package org.example.longdistancebusbackend.config;

import org.example.longdistancebusbackend.dto.UserDTO;
import org.example.longdistancebusbackend.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        // Skip 'name' during automatic mapping
        modelMapper.addMappings(new PropertyMap<User, UserDTO>() {
            @Override
            protected void configure() {
                skip(destination.getName());
            }
        });

        return modelMapper;
    }
}
