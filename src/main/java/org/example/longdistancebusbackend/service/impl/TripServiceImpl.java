package org.example.longdistancebusbackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.longdistancebusbackend.dto.BusDTO;
import org.example.longdistancebusbackend.dto.TripDTO;
import org.example.longdistancebusbackend.entity.Bus;
import org.example.longdistancebusbackend.entity.Trip;
import org.example.longdistancebusbackend.exception.ResourseAllredyFound;
import org.example.longdistancebusbackend.exception.ResourseNotFound;
import org.example.longdistancebusbackend.repository.BusRepository;
import org.example.longdistancebusbackend.repository.TripRepository;
import org.example.longdistancebusbackend.service.BusService;
import org.example.longdistancebusbackend.service.TripService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {
    private final TripRepository tripRepository;
    private final ModelMapper modelMapper;


    @Override
    public void saveTrip(TripDTO tripDTO) {
        try {
            tripRepository.save(modelMapper.map(tripDTO, Trip.class));
        } catch (DataIntegrityViolationException ex) {
            throw new ResourseAllredyFound("Duplicate value found");
        }
    }

    @Override
    public void updateTrip(TripDTO tripDTO) {
        Trip existingTrip = tripRepository.findById(tripDTO.getTripId())
                .orElseThrow(() -> new ResourseNotFound("Trip not found with id: " + tripDTO.getTripId()));

        try {
            Trip updatedTrip = modelMapper.map(tripDTO, Trip.class);
            tripRepository.save(updatedTrip);

        } catch (DataIntegrityViolationException ex) {
            throw new ResourseAllredyFound("Duplicate value found");
        }
    }

    @Override
    public List<TripDTO> getAll() {
        List<Trip> allTrips = tripRepository.findAll();
        if(allTrips.isEmpty()) {
            throw new ResourseNotFound("No trips found");
        }
        return modelMapper.map(allTrips, new TypeToken<List<TripDTO>>(){}.getType());
    }

    @Override
    public void deleteTrip(Integer id) {
        try {
            Trip existingTrip = tripRepository.findById(id)
                    .orElseThrow(() -> new ResourseNotFound("Trip not found with id: " + id));

            tripRepository.delete(existingTrip);

        } catch (ResourseNotFound ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to delete trip: " + ex.getMessage());
        }
    }
}
