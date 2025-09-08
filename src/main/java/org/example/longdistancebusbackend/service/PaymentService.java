package org.example.longdistancebusbackend.service;

import org.example.longdistancebusbackend.dto.BookingDTO;
import org.example.longdistancebusbackend.dto.PaymentDTO;

import java.util.List;

public interface PaymentService {
    void savePayment(PaymentDTO paymentDTO);
    public void updatePayment(PaymentDTO paymentDTO);
    List<PaymentDTO> getAll();
    void deletePayment(Integer id);
}
