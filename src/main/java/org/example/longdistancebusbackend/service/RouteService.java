package org.example.longdistancebusbackend.service;

import org.example.longdistancebusbackend.dto.RouteDTO;
import org.example.longdistancebusbackend.dto.StopDTO;

import java.util.List;

public interface RouteService {
    void saveRoute(RouteDTO routeDTO);
    public void updateRoute(RouteDTO routeDTO);
    List<RouteDTO> getAll();
    void deleteRoute(Integer id);
}
