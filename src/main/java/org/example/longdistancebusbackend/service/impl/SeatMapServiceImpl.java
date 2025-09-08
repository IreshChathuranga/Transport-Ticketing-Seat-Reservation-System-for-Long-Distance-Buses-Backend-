package org.example.longdistancebusbackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.longdistancebusbackend.dto.SeatDTO;
import org.example.longdistancebusbackend.dto.SeatMapDTO;
import org.example.longdistancebusbackend.entity.Seat;
import org.example.longdistancebusbackend.entity.SeatMap;
import org.example.longdistancebusbackend.exception.ResourseAllredyFound;
import org.example.longdistancebusbackend.exception.ResourseNotFound;
import org.example.longdistancebusbackend.repository.SeatMapRepository;
import org.example.longdistancebusbackend.repository.SeatRepository;
import org.example.longdistancebusbackend.service.SeatMapService;
import org.example.longdistancebusbackend.service.SeatService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SeatMapServiceImpl implements SeatMapService {
    private final SeatMapRepository seatMapRepository;
    private final ModelMapper modelMapper;


    @Override
    public void saveSeatMap(SeatMapDTO seatMapDTO) {
        try {
            seatMapRepository.save(modelMapper.map(seatMapDTO, SeatMap.class));
        } catch (DataIntegrityViolationException ex) {
            throw new ResourseAllredyFound("Duplicate value found (name already exists)");
        }
    }

    @Override
    public void updateSeatMap(SeatMapDTO seatMapDTO) {
        SeatMap existingSeatMap = seatMapRepository.findById(seatMapDTO.getSeatMapId())
                .orElseThrow(() -> new ResourseNotFound("SeatMap not found with id: " + seatMapDTO.getSeatMapId()));

        try {
            SeatMap updatedSeatMap = modelMapper.map(seatMapDTO, SeatMap.class);
            seatMapRepository.save(updatedSeatMap);

        } catch (DataIntegrityViolationException ex) {
            throw new ResourseAllredyFound("Duplicate value found (name already exists)");
        }
    }

    @Override
    public List<SeatMapDTO> getAll() {
        List<SeatMap> allSeatMaps = seatMapRepository.findAll();
        if(allSeatMaps.isEmpty()) {
            throw new ResourseNotFound("No seatmaps found");
        }
        return modelMapper.map(allSeatMaps, new TypeToken<List<SeatMapDTO>>(){}.getType());
    }

    @Override
    public void deleteSeatMap(Integer id) {
        try {
            SeatMap existingSeatMap = seatMapRepository.findById(id)
                    .orElseThrow(() -> new ResourseNotFound("SeatMap not found with id: " + id));

            seatMapRepository.delete(existingSeatMap);

        } catch (ResourseNotFound ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to delete seatmap: " + ex.getMessage());
        }
    }
}
