package org.example.longdistancebusbackend.service;

import org.example.longdistancebusbackend.dto.SeatDTO;
import org.example.longdistancebusbackend.dto.SeatMapDTO;

import java.util.List;

public interface SeatMapService {
    void saveSeatMap(SeatMapDTO seatMapDTO);
    public void updateSeatMap(SeatMapDTO seatMapDTO);
    List<SeatMapDTO> getAll();
    void deleteSeatMap(Integer id);
}
