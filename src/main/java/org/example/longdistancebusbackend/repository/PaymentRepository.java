package org.example.longdistancebusbackend.repository;

import org.example.longdistancebusbackend.entity.BookingItem;
import org.example.longdistancebusbackend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
