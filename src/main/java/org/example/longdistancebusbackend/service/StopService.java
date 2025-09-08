package org.example.longdistancebusbackend.service;

import org.example.longdistancebusbackend.dto.BusDTO;
import org.example.longdistancebusbackend.dto.StopDTO;

import java.util.List;

public interface StopService {
    void saveStop(StopDTO stopDTO);
    public void updateStop(StopDTO stopDTO);
    List<StopDTO> getAll();
    void deleteStop(Integer id);
}
