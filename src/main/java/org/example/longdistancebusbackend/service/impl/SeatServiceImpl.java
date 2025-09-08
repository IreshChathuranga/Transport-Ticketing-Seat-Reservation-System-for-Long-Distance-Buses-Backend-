package org.example.longdistancebusbackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.longdistancebusbackend.dto.BusDTO;
import org.example.longdistancebusbackend.dto.SeatDTO;
import org.example.longdistancebusbackend.entity.Bus;
import org.example.longdistancebusbackend.entity.Seat;
import org.example.longdistancebusbackend.entity.SeatMap;
import org.example.longdistancebusbackend.exception.ResourseAllredyFound;
import org.example.longdistancebusbackend.exception.ResourseNotFound;
import org.example.longdistancebusbackend.repository.BusRepository;
import org.example.longdistancebusbackend.repository.SeatMapRepository;
import org.example.longdistancebusbackend.repository.SeatRepository;
import org.example.longdistancebusbackend.service.BusService;
import org.example.longdistancebusbackend.service.SeatService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService {
    private final SeatRepository seatRepository;
    private final SeatMapRepository seatMapRepository;
    private final ModelMapper modelMapper;


    @Override
    public void saveSeat(SeatDTO seatDTO) {
        SeatMap seatMap = seatMapRepository.findById(seatDTO.getSeatMap())
                .orElseThrow(() -> new ResourseNotFound("SeatMap not found with id: " + seatDTO.getSeatMap()));

        Seat seat = new Seat();
        seat.setSeatNumber(seatDTO.getSeatNumber());
        seat.setRowNo(seatDTO.getRowNo());
        seat.setColNo(seatDTO.getColNo());
        seat.setSeatType(seatDTO.getSeatType());
        seat.setSeatMap(seatMap); // now properly linked

        try {
            seatRepository.save(seat);
        } catch (DataIntegrityViolationException ex) {
            throw new ResourseAllredyFound("Duplicate value found (seat_number,row_no and col_no already exists)");
        }
    }

    @Override
    public void updateSeat(SeatDTO seatDTO) {
        Seat existingSeat = seatRepository.findById(seatDTO.getSeatId())
                .orElseThrow(() -> new ResourseNotFound("Seat not found with id: " + seatDTO.getSeatId()));

        existingSeat.setSeatNumber(seatDTO.getSeatNumber());
        existingSeat.setRowNo(seatDTO.getRowNo());
        existingSeat.setColNo(seatDTO.getColNo());
        existingSeat.setSeatType(seatDTO.getSeatType());

        SeatMap seatMap = seatMapRepository.findById(seatDTO.getSeatMap())
                .orElseThrow(() -> new ResourseNotFound("SeatMap not found with id: " + seatDTO.getSeatMap()));
        existingSeat.setSeatMap(seatMap);

        try {
            seatRepository.save(existingSeat);
        } catch (DataIntegrityViolationException ex) {
            throw new ResourseAllredyFound("Duplicate value found (seat_number,row_no and col_no already exists)");
        }
    }

    @Override
    public List<SeatDTO> getAll() {
        List<Seat> allSeats = seatRepository.findAll();
        if(allSeats.isEmpty()) {
            throw new ResourseNotFound("No seats found");
        }
        return modelMapper.map(allSeats, new TypeToken<List<SeatDTO>>(){}.getType());
    }

    @Override
    public void deleteSeat(Integer id) {
        try {
            Seat existingSeat = seatRepository.findById(id)
                    .orElseThrow(() -> new ResourseNotFound("Seat not found with id: " + id));

            seatRepository.delete(existingSeat);

        } catch (ResourseNotFound ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to delete seat: " + ex.getMessage());
        }
    }
}
