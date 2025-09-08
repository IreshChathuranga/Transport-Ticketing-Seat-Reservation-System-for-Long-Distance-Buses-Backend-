package org.example.longdistancebusbackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.longdistancebusbackend.dto.BusDTO;
import org.example.longdistancebusbackend.dto.OperatorDTO;
import org.example.longdistancebusbackend.dto.UserDTO;
import org.example.longdistancebusbackend.entity.Bus;
import org.example.longdistancebusbackend.entity.Operator;
import org.example.longdistancebusbackend.entity.User;
import org.example.longdistancebusbackend.exception.ResourseAllredyFound;
import org.example.longdistancebusbackend.exception.ResourseNotFound;
import org.example.longdistancebusbackend.repository.BusRepository;
import org.example.longdistancebusbackend.repository.UserRepository;
import org.example.longdistancebusbackend.service.BusService;
import org.example.longdistancebusbackend.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BusServiceImpl implements BusService {
    private final BusRepository busRepository;
    private final ModelMapper modelMapper;


    @Override
    public void saveBus(BusDTO busDTO) {
        try {
            busRepository.save(modelMapper.map(busDTO, Bus.class));
        } catch (DataIntegrityViolationException ex) {
            throw new ResourseAllredyFound("Duplicate value found (plate_no already exists)");
        }
    }

    @Override
    public void updateBus(BusDTO busDTO) {
        Bus existingBus = busRepository.findById(busDTO.getBusId())
                .orElseThrow(() -> new ResourseNotFound("Bus not found with id: " + busDTO.getBusId()));

        try {
            Bus updatedBus = modelMapper.map(busDTO, Bus.class);
            busRepository.save(updatedBus);

        } catch (DataIntegrityViolationException ex) {
            throw new ResourseAllredyFound("Duplicate value found (plate_no already exists)");
        }
    }

    @Override
    public List<BusDTO> getAll() {
        List<Bus> allBuses = busRepository.findAll();
        if(allBuses.isEmpty()) {
            throw new ResourseNotFound("No buses found");
        }
        return modelMapper.map(allBuses, new TypeToken<List<BusDTO>>(){}.getType());
    }

    @Override
    public void deleteBus(Integer id) {
        try {
            Bus existingBus = busRepository.findById(id)
                    .orElseThrow(() -> new ResourseNotFound("Bus not found with id: " + id));

            busRepository.delete(existingBus);

        } catch (ResourseNotFound ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to delete bus: " + ex.getMessage());
        }
    }
}
