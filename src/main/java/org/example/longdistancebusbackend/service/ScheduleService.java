package org.example.longdistancebusbackend.service;

import org.example.longdistancebusbackend.dto.ScheduleSearchDTO;
import org.example.longdistancebusbackend.dto.ScheduleDTO;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    void saveSchedule(ScheduleDTO scheduleDTO);
    public void updateSchedule(ScheduleDTO scheduleDTO);
    List<ScheduleDTO> getAll();
    void deleteSchedule(Integer id);
    List<ScheduleSearchDTO> searchSchedules(String from, String to, LocalDate date);}
