package org.example.longdistancebusbackend.service;

import org.example.longdistancebusbackend.dto.BusDTO;
import org.example.longdistancebusbackend.dto.SeatDTO;

import java.util.List;

public interface SeatService {
    void saveSeat(SeatDTO seatDTO);
    public void updateSeat(SeatDTO seatDTO);
    List<SeatDTO> getAll();
    void deleteSeat(Integer id);
}
