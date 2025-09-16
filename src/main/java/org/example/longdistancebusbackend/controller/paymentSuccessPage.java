package org.example.longdistancebusbackend.controller;

import jakarta.annotation.security.PermitAll;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class paymentSuccessPage {
    @GetMapping("/payment-success")
    @PermitAll
    public String paymentSuccessPage(@RequestParam String order_id, Model model) {
        model.addAttribute("orderId", order_id);
        return "payment-success"; // this resolves to payment-success.html
    }
}
