package org.example.longdistancebusbackend.service;

import org.example.longdistancebusbackend.dto.BusDTO;
import org.example.longdistancebusbackend.dto.FareDTO;

import java.util.List;

public interface FareService {
    void saveFare(FareDTO fareDTO);
    public void updateFare(FareDTO fareDTO);
    List<FareDTO> getAll();
    void deleteFare(Integer id);
}
