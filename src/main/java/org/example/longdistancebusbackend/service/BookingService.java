package org.example.longdistancebusbackend.service;

import org.example.longdistancebusbackend.dto.BookingDTO;
import org.example.longdistancebusbackend.dto.BusDTO;

import java.util.List;

public interface BookingService {
    void saveBooking(BookingDTO bookingDTO);
    public void updateBooking(BookingDTO bookingDTO);
    List<BookingDTO> getAll();
    void deleteBooking(Integer id);
    void confirmBooking(Integer bookingId);
}
