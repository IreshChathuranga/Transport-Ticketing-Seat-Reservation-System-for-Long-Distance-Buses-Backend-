package org.example.longdistancebusbackend.repository;

import org.example.longdistancebusbackend.entity.Booking;
import org.example.longdistancebusbackend.entity.BookingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingItemRepository extends JpaRepository<BookingItem, Integer> {
}
