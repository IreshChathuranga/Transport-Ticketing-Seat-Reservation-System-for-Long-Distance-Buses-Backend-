package org.example.longdistancebusbackend.service;

import org.example.longdistancebusbackend.dto.LoginRequestDTO;
import org.example.longdistancebusbackend.dto.LoginResponseDTO;
import org.example.longdistancebusbackend.dto.UserDTO;
import org.example.longdistancebusbackend.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDTO userDTO);
    public void updateUser(UserDTO userDTO);
    List<UserDTO> getAll();
    void deleteUser(Integer id);
    LoginResponseDTO login(LoginRequestDTO loginRequestDTO);

    UserDTO authenticate(String email, String password);
    UserDTO register(User user);
    UserDTO getUserByNic(String nic);
}
