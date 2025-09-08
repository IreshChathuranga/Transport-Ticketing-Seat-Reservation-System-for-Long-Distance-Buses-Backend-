package org.example.longdistancebusbackend.service;

import org.example.longdistancebusbackend.dto.OperatorDTO;
import org.example.longdistancebusbackend.dto.UserDTO;

import java.util.List;

public interface OperatorService {
    void saveOperator(OperatorDTO operatorDTO);
    public void updateOperator(OperatorDTO operatorDTO);
    List<OperatorDTO> getAll();
    void deleteOperator(Integer id);
}
