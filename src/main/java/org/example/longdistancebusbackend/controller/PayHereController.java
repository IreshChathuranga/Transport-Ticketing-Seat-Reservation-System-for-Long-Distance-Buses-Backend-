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
        String notifyUrl = "https://aa9085fe2741.ngrok-free.app/api/v1/payhere/notify";

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
        log.info("PAYHERE NOTIFY called, payload: {}", payload);
        System.out.println("method call");
        try {
            String merchantIdReceived = payload.get("merchant_id");
            String orderId = payload.get("order_id");
            String paymentId = payload.get("payment_id");
            String statusCode = payload.get("status_code");
            String md5sig = payload.get("md5sig");
            String amount = payload.get("payhere_amount");
            String currency = payload.get("payhere_currency");

            if (amount == null || currency == null || statusCode == null || md5sig == null) {
                log.error("Missing required fields in PayHere payload: {}", payload);
                return "INVALID_PAYLOAD";
            }

            boolean ok = PayHereUtil.verifyMd5(merchantIdReceived, orderId, amount, currency, statusCode, payhereSecret, md5sig);
            if (!ok) {
                log.error("MD5 signature failed for order {}", orderId);
                return "INVALID_MD5";
            }

            Booking booking = bookingRepository.findByBookingRef(orderId)
                    .orElse(null);
            if (booking == null) {
                log.error("Booking not found for ref {}", orderId);
                return "BOOKING_NOT_FOUND";
            }

            // Check if payment already exists for this bookingRef to avoid unique constraint violation
            boolean paymentExists = paymentRepository.existsByBookingRef(orderId);
            if (paymentExists) {
                log.warn("Payment already exists for bookingRef {}. Skipping duplicate save.", orderId);
                // possibly update status or ignore
                return "ALREADY_PAID";
            }

            Payment payment = new Payment();
            payment.setBookingRef(orderId);
            payment.setAmount(new BigDecimal(amount));
            payment.setCurrency(currency);
            payment.setMethod("CARD");
            payment.setProvider("PayHere");
            payment.setProviderTxnId(paymentId);
            if ("2".equals(statusCode)) {
                payment.setStatus("SUCCESS");
                payment.setPaidAt(LocalDateTime.now());
            } else {
                payment.setStatus("FAILED");
            }

            paymentRepository.save(payment);
            log.info("Payment saved for bookingRef {}", orderId);

            if ("2".equals(statusCode)) {
                bookingService.confirmBooking(booking.getBookingId());
                log.info("Booking confirmed for bookingRef {}", orderId);
            } else {
                booking.setStatus("PAYMENT_FAILED");
                bookingRepository.save(booking);
                log.info("Booking status updated to PAYMENT_FAILED for bookingRef {}", orderId);
            }

            return "OK";
        } catch (Exception e) {
            log.error("Error in PayHere notify processing", e);
            return "ERROR";
        }
    }
}
