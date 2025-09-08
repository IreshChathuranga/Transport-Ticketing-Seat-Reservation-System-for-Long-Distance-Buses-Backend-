package org.example.longdistancebusbackend.service;

import org.example.longdistancebusbackend.dto.BusDTO;
import org.example.longdistancebusbackend.dto.TripDTO;

import java.util.List;

public interface TripService {
    void saveTrip(TripDTO tripDTO);
    public void updateTrip(TripDTO tripDTO);
    List<TripDTO> getAll();
    void deleteTrip(Integer id);
}
