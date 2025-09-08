package org.example.longdistancebusbackend.service;

import org.example.longdistancebusbackend.dto.BookingDTO;
import org.example.longdistancebusbackend.dto.BookingItemDTO;

import java.util.List;

public interface BookingItemService {
    void saveBookingItem(BookingItemDTO bookingItemDTO);
    public void updateBookingItem(BookingItemDTO bookingItemDTO);
    List<BookingItemDTO> getAll();
    void deleteBookingItem(Integer id);
}
