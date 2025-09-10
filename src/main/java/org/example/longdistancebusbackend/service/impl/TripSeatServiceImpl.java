package org.example.longdistancebusbackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.longdistancebusbackend.dto.BusDTO;
import org.example.longdistancebusbackend.dto.TripSeatDTO;
import org.example.longdistancebusbackend.entity.Bus;
import org.example.longdistancebusbackend.entity.TripSeat;
import org.example.longdistancebusbackend.exception.ResourseAllredyFound;
import org.example.longdistancebusbackend.exception.ResourseNotFound;
import org.example.longdistancebusbackend.repository.BusRepository;
import org.example.longdistancebusbackend.repository.TripSeatRepository;
import org.example.longdistancebusbackend.service.BusService;
import org.example.longdistancebusbackend.service.TripSeatService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TripSeatServiceImpl implements TripSeatService {
    private final TripSeatRepository tripSeatRepository;
    private final ModelMapper modelMapper;


    @Override
    public void saveTripSeat(TripSeatDTO tripSeatDTO) {
        try {
            tripSeatRepository.save(modelMapper.map(tripSeatDTO, TripSeat.class));
        } catch (DataIntegrityViolationException ex) {
            throw new ResourseAllredyFound("Duplicate value found");
        }
    }

    @Override
    public void updateTripSeat(TripSeatDTO tripSeatDTO) {
        TripSeat existingTripSeat = tripSeatRepository.findById(tripSeatDTO.getTripSeatId())
                .orElseThrow(() -> new ResourseNotFound("TripSeat not found with id: " + tripSeatDTO.getTripSeatId()));

        try {
            TripSeat updatedTripSeat = modelMapper.map(tripSeatDTO, TripSeat.class);
            tripSeatRepository.save(updatedTripSeat);

        } catch (DataIntegrityViolationException ex) {
            throw new ResourseAllredyFound("Duplicate value found");
        }
    }

    @Override
    public List<TripSeatDTO> getAll() {
        List<TripSeat> allTripSeats = tripSeatRepository.findAll();
        if(allTripSeats.isEmpty()) {
            throw new ResourseNotFound("No tripSeats found");
        }

        return allTripSeats.stream().map(tripSeat -> {
            TripSeatDTO dto = new TripSeatDTO();
            dto.setTripSeatId(tripSeat.getTripSeatId());
            dto.setTripId(tripSeat.getTrip().getTripId());
            dto.setSeatId(tripSeat.getSeat().getSeatId());
            dto.setStatus(tripSeat.getStatus());
            dto.setHoldExpiresAt(tripSeat.getHoldExpiresAt());
            return dto;
        }).toList();
    }

    @Override
    public void deleteTripSeat(Integer id) {
        try {
            TripSeat existingTripSeat = tripSeatRepository.findById(id)
                    .orElseThrow(() -> new ResourseNotFound("TripSeat not found with id: " + id));

            tripSeatRepository.delete(existingTripSeat);

        } catch (ResourseNotFound ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to delete tripSeat: " + ex.getMessage());
        }
    }
}
