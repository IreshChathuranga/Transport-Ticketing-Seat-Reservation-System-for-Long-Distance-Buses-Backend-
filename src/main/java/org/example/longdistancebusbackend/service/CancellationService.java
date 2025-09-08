package org.example.longdistancebusbackend.service;

import org.example.longdistancebusbackend.dto.BookingItemDTO;
import org.example.longdistancebusbackend.dto.CancellationDTO;

import java.util.List;

public interface CancellationService {
    void saveCancellation(CancellationDTO cancellationDTO);
    public void updateCancellation(CancellationDTO cancellationDTO);
    List<CancellationDTO> getAll();
    void deleteCancellation(Integer id);
}
