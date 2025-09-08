package org.example.longdistancebusbackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.longdistancebusbackend.dto.BusDTO;
import org.example.longdistancebusbackend.dto.ScheduleDTO;
import org.example.longdistancebusbackend.entity.Bus;
import org.example.longdistancebusbackend.entity.Schedule;
import org.example.longdistancebusbackend.exception.ResourseAllredyFound;
import org.example.longdistancebusbackend.exception.ResourseNotFound;
import org.example.longdistancebusbackend.repository.BusRepository;
import org.example.longdistancebusbackend.repository.ScheduleRepository;
import org.example.longdistancebusbackend.service.BusService;
import org.example.longdistancebusbackend.service.ScheduleService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ModelMapper modelMapper;


    @Override
    public void saveSchedule(ScheduleDTO scheduleDTO) {
        try {
            scheduleRepository.save(modelMapper.map(scheduleDTO, Schedule.class));
        } catch (DataIntegrityViolationException ex) {
            throw new ResourseAllredyFound("Duplicate value found");
        }
    }

    @Override
    public void updateSchedule(ScheduleDTO scheduleDTO) {
        Schedule existingSchedule = scheduleRepository.findById(scheduleDTO.getScheduleId())
                .orElseThrow(() -> new ResourseNotFound("Schedule not found with id: " + scheduleDTO.getScheduleId()));

        try {
            Schedule updatedSchedule = modelMapper.map(scheduleDTO, Schedule.class);
            scheduleRepository.save(updatedSchedule);

        } catch (DataIntegrityViolationException ex) {
            throw new ResourseAllredyFound("Duplicate value found");
        }
    }

    @Override
    public List<ScheduleDTO> getAll() {
        List<Schedule> allSchedules = scheduleRepository.findAll();
        if(allSchedules.isEmpty()) {
            throw new ResourseNotFound("No schedules found");
        }
        return modelMapper.map(allSchedules, new TypeToken<List<ScheduleDTO>>(){}.getType());
    }

    @Override
    public void deleteSchedule(Integer id) {
        try {
            Schedule existingSchedule = scheduleRepository.findById(id)
                    .orElseThrow(() -> new ResourseNotFound("Schedule not found with id: " + id));

            scheduleRepository.delete(existingSchedule);

        } catch (ResourseNotFound ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to delete schedule: " + ex.getMessage());
        }
    }
}
