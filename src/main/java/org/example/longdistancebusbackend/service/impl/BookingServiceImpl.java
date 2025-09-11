package org.example.longdistancebusbackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.longdistancebusbackend.dto.BookingDTO;
import org.example.longdistancebusbackend.dto.BusDTO;
import org.example.longdistancebusbackend.entity.Booking;
import org.example.longdistancebusbackend.entity.Bus;
import org.example.longdistancebusbackend.entity.Trip;
import org.example.longdistancebusbackend.entity.User;
import org.example.longdistancebusbackend.exception.ResourseAllredyFound;
import org.example.longdistancebusbackend.exception.ResourseNotFound;
import org.example.longdistancebusbackend.repository.BookingRepository;
import org.example.longdistancebusbackend.repository.BusRepository;
import org.example.longdistancebusbackend.service.BookingService;
import org.example.longdistancebusbackend.service.BusService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository bookingRepository;
    private final ModelMapper modelMapper;

    @Override
    public void saveBooking(BookingDTO bookingDTO) {
        try {
            Booking booking = new Booking();

            booking.setBookingRef(bookingDTO.getBookingRef());
            booking.setSeatNumber(bookingDTO.getSeatNumber());
            booking.setStatus(bookingDTO.getStatus());
            booking.setTotalAmount(bookingDTO.getTotalAmount());
            booking.setCurrency(bookingDTO.getCurrency());

            // Manually map User and Trip
            User user = new User();
            user.setUserId(bookingDTO.getUserId());
            booking.setUser(user);

            Trip trip = new Trip();
            trip.setTripId(bookingDTO.getTripId());
            booking.setTrip(trip);

            bookingRepository.save(booking);

        } catch (DataIntegrityViolationException ex) {
            throw new ResourseAllredyFound("Duplicate value found (qrCode already exists)");
        }
    }

    @Override
    public void updateBooking(BookingDTO bookingDTO) {
        Booking existingBooking = bookingRepository.findById(bookingDTO.getBookingId())
                .orElseThrow(() -> new ResourseNotFound("Booking not found with id: " + bookingDTO.getBookingId()));

        try {
            Booking updatedBooking = modelMapper.map(bookingDTO, Booking.class);
            bookingRepository.save(updatedBooking);

        } catch (DataIntegrityViolationException ex) {
            throw new ResourseAllredyFound("Duplicate value found (qrCode already exists)");
        }
    }

    @Override
    public List<BookingDTO> getAll() {
        List<Booking> allBookings = bookingRepository.findAll();
        if(allBookings.isEmpty()) {
            throw new ResourseNotFound("No bookings found");
        }
        return modelMapper.map(allBookings, new TypeToken<List<BookingDTO>>(){}.getType());
    }

    @Override
    public void deleteBooking(Integer id) {
        try {
            Booking existingBooking = bookingRepository.findById(id)
                    .orElseThrow(() -> new ResourseNotFound("Booking not found with id: " + id));

            bookingRepository.delete(existingBooking);

        } catch (ResourseNotFound ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to delete booking: " + ex.getMessage());
        }
    }
}
