package org.example.longdistancebusbackend.service;

import org.example.longdistancebusbackend.dto.BusDTO;
import org.example.longdistancebusbackend.dto.TripSeatDTO;
import org.example.longdistancebusbackend.entity.TripSeat;

import java.util.List;

public interface TripSeatService {
    void saveTripSeat(TripSeatDTO tripSeatDTO);
    public void updateTripSeat(TripSeatDTO tripSeatDTO);
    List<TripSeatDTO> getAll();
    void deleteTripSeat(Integer id);
    int getAvailableSeatsByTripId(Integer tripId);

    List<TripSeat> getSeatsForTrip(Integer tripId);
}
