package org.example.longdistancebusbackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.longdistancebusbackend.dto.RouteDTO;
import org.example.longdistancebusbackend.dto.SeatDTO;
import org.example.longdistancebusbackend.dto.StopDTO;
import org.example.longdistancebusbackend.entity.Route;
import org.example.longdistancebusbackend.entity.Seat;
import org.example.longdistancebusbackend.entity.Stop;
import org.example.longdistancebusbackend.exception.ResourseAllredyFound;
import org.example.longdistancebusbackend.exception.ResourseNotFound;
import org.example.longdistancebusbackend.repository.RouteRepository;
import org.example.longdistancebusbackend.repository.StopRepository;
import org.example.longdistancebusbackend.service.RouteService;
import org.example.longdistancebusbackend.service.StopService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl implements RouteService{
    private final RouteRepository routeRepository;
    private final ModelMapper modelMapper;


    @Override
    public void saveRoute(RouteDTO routeDTO) {
        try {
            routeRepository.save(modelMapper.map(routeDTO, Route.class));
        } catch (DataIntegrityViolationException ex) {
            throw new ResourseAllredyFound("Duplicate value found (code already exists)");
        }
    }

    @Override
    public void updateRoute(RouteDTO routeDTO) {
        Route existingRoute = routeRepository.findById(routeDTO.getRouteId())
                .orElseThrow(() -> new ResourseNotFound("Route not found with id: " + routeDTO.getRouteId()));

        try {
            Route updatedRoute = modelMapper.map(routeDTO, Route.class);
            routeRepository.save(updatedRoute);

        } catch (DataIntegrityViolationException ex) {
            throw new ResourseAllredyFound("Duplicate value found (code already exists)");
        }
    }
    @Override
    public List<RouteDTO> getAll() {
        List<Route> allRoutes = routeRepository.findAll();
        if(allRoutes.isEmpty()) {
            throw new ResourseNotFound("No routes found");
        }
        return modelMapper.map(allRoutes, new TypeToken<List<RouteDTO>>(){}.getType());
    }

    @Override
    public void deleteRoute(Integer id) {
        try {
            Route existingRoute = routeRepository.findById(id)
                    .orElseThrow(() -> new ResourseNotFound("Route not found with id: " + id));

            routeRepository.delete(existingRoute);

        } catch (ResourseNotFound ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to delete route: " + ex.getMessage());
        }
    }
}
