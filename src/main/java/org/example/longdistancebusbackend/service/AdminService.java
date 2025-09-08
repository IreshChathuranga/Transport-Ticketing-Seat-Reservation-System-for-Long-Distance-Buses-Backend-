package org.example.longdistancebusbackend.service;

import org.example.longdistancebusbackend.dto.AdminDTO;

import java.util.List;

public interface AdminService {
    void saveAdmin(AdminDTO adminDTO);
    public void updateAdmin(AdminDTO adminDTO);
    List<AdminDTO> getAll();
    void deleteAdmin(Integer id);
}
