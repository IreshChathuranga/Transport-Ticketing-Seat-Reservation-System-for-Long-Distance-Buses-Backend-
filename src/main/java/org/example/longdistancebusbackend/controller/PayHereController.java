package org.example.longdistancebusbackend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.longdistancebusbackend.Util.PayHereUtil;
import org.example.longdistancebusbackend.entity.Booking;
import org.example.longdistancebusbackend.entity.Payment;
import org.example.longdistancebusbackend.repository.BookingRepository;
import org.example.longdistancebusbackend.repository.PaymentRepository;
import org.example.longdistancebusbackend.service.BookingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

@RestController
@RequestMapping("/api/v1/payhere")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Slf4j
public class PayHereController {
    private final BookingRepository bookingRepository;
    private final PaymentRepository paymentRepository;

    private final BookingService bookingService;

    @Value("${payhere.merchant.id}")
    private String merchantId;

    @Value("${app.base-url}")
    private String appBaseUrl;

    @Value("${payhere.secret.key}")
    private String payhereSecret;

    // frontend calls this with bookingRef (or create booking beforehand)
    @PostMapping(value = "/initiate",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.TEXT_HTML_VALUE)
    public String initiatePayment(@RequestHeader("Authorization") String authHeader,@RequestBody Map<String, Object> payload) {
        String jwtToken = authHeader.replace("Bearer ", ""); // 🔑 Extract token from header
        String bookingRef = (String) payload.get("bookingRef");
        String amount = String.format("%.2f", new BigDecimal(payload.get("amount").toString()));
        String items = (String) payload.getOrDefault("items", "Bus Ticket");
        String firstName = (String) payload.getOrDefault("firstName", "");
        String lastName = (String) payload.getOrDefault("lastName", "");
        String email = (String) payload.getOrDefault("email", "");
        String phone = (String) payload.getOrDefault("phone", "");
        // new required params
        String address = (String) payload.getOrDefault("address", "");
        String city = (String) payload.getOrDefault("city", "");
        String country = (String) payload.getOrDefault("country", "Sri Lanka");  // default

        String returnUrl = "http://localhost:63342/Long%20Distance%20Bus%20Fronted/html/payment-success.html?order_id=" + bookingRef;
        String cancelUrl = appBaseUrl + "/payment-cancel.html";
        String notifyUrl = appBaseUrl + "/api/v1/payhere/notify";

        String payhereUrl = "https://sandbox.payhere.lk/pay/checkout";

        String hash = PayHereUtil.generateHash(merchantId, bookingRef, amount, "LKR", payhereSecret);

        return "<html><body onload='document.forms[\"payhereForm\"].submit()'>"
                + "<form name='payhereForm' method='post' action='" + payhereUrl + "'>"
                + "<input type='hidden' name='merchant_id' value='" + merchantId + "'/>"
                + "<input type='hidden' name='return_url' value='" + returnUrl + "'/>"
                + "<input type='hidden' name='cancel_url' value='" + cancelUrl + "'/>"
                + "<input type='hidden' name='notify_url' value='" + notifyUrl + "'/>"
                + "<input type='hidden' name='order_id' value='" + bookingRef + "'/>"
                + "<input type='hidden' name='items' value='" + URLEncoder.encode(items, UTF_8) + "'/>"
                + "<input type='hidden' name='amount' value='" + amount + "'/>"
                + "<input type='hidden' name='currency' value='LKR'/>"
                + "<input type='hidden' name='first_name' value='" + URLEncoder.encode(firstName, UTF_8) + "'/>"
                + "<input type='hidden' name='last_name' value='" + URLEncoder.encode(lastName, UTF_8) + "'/>"
                + "<input type='hidden' name='email' value='" + URLEncoder.encode(email, UTF_8) + "'/>"
                + "<input type='hidden' name='phone' value='" + URLEncoder.encode(phone, UTF_8) + "'/>"
                // new ones:
                + "<input type='hidden' name='address' value='" + URLEncoder.encode(address, UTF_8) + "'/>"
                + "<input type='hidden' name='city' value='" + URLEncoder.encode(city, UTF_8) + "'/>"
                + "<input type='hidden' name='country' value='" + URLEncoder.encode(country, UTF_8) + "'/>"
                + "<input type='hidden' name='hash' value='" + hash + "'/>"
                + "</form><p>Redirecting to PayHere Sandbox...</p></body></html>";
    }

    private String escape(String s) {
        if (s == null) return "";
        return URLEncoder.encode(s, UTF_8);
    }
    @PostMapping("/notify")
    public String handleNotify(@RequestParam Map<String,String> payload) {
        String merchantIdReceived = payload.get("merchant_id");
        String orderId = payload.get("order_id"); // bookingRef
        String paymentId = payload.get("payment_id");
        String statusCode = payload.get("status_code");
        String md5sig = payload.get("md5sig");
        String amount = payload.get("amount");
        String currency = payload.get("currency");

        if (!merchantId.equals(merchantIdReceived)) {
            return "INVALID_MERCHANT";
        }

        boolean ok = PayHereUtil.verifyMd5(merchantIdReceived, orderId, amount, currency, statusCode, payhereSecret, md5sig);

        Booking booking = bookingRepository.findByBookingRef(orderId).orElse(null);
        if (booking == null) return "BOOKING_NOT_FOUND";

        Payment payment = new Payment();
        payment.setBookingRef(booking.getBookingRef());
        payment.setAmount(new BigDecimal(amount));
        payment.setCurrency(currency);
        payment.setMethod("CARD");
        payment.setProvider("PayHere");
        payment.setProviderTxnId(paymentId);
        payment.setStatus(ok && "2".equals(statusCode) ? "SUCCESS" : "FAILED");
        if (ok && "2".equals(statusCode)) payment.setPaidAt(LocalDateTime.now());
        // save via paymentRepository
        paymentRepository.save(payment);

        if (ok && "2".equals(statusCode)) {
            bookingService.confirmBooking(booking.getBookingId());
        } else {
            booking.setStatus("PAYMENT_FAILED");
            bookingRepository.save(booking);
        }

        return "OK";
    }
}
