package org.example.longdistancebusbackend.service;

import org.example.longdistancebusbackend.dto.BusDTO;
import org.example.longdistancebusbackend.dto.OperatorDTO;

import java.util.List;

public interface BusService {
    void saveBus(BusDTO busDTO);
    public void updateBus(BusDTO busDTO);
    List<BusDTO> getAll();
    void deleteBus(Integer id);
}
