package org.example.longdistancebusbackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.longdistancebusbackend.dto.BookingDTO;
import org.example.longdistancebusbackend.dto.BookingItemDTO;
import org.example.longdistancebusbackend.dto.TicketDTO;
import org.example.longdistancebusbackend.entity.Booking;
import org.example.longdistancebusbackend.entity.BookingItem;
import org.example.longdistancebusbackend.entity.Ticket;
import org.example.longdistancebusbackend.exception.ResourseAllredyFound;
import org.example.longdistancebusbackend.exception.ResourseNotFound;
import org.example.longdistancebusbackend.repository.BookingItemRepository;
import org.example.longdistancebusbackend.repository.TicketRepository;
import org.example.longdistancebusbackend.service.BookingItemService;
import org.example.longdistancebusbackend.service.TicketService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {
    private final TicketRepository ticketRepository;
    private final ModelMapper modelMapper;

    @Override
    public void saveTicket(TicketDTO ticketDTO) {
        try {
            ticketRepository.save(modelMapper.map(ticketDTO, Ticket.class));
        } catch (DataIntegrityViolationException ex) {
            throw new ResourseAllredyFound("Duplicate value found");
        }
    }

    @Override
    public void updateTicket(TicketDTO ticketDTO) {
        Ticket existingTicket = ticketRepository.findById(ticketDTO.getTicketId())
                .orElseThrow(() -> new ResourseNotFound("Ticket not found with id: " + ticketDTO.getTicketId()));

        try {
            Ticket updatedTicket = modelMapper.map(ticketDTO, Ticket.class);
            ticketRepository.save(updatedTicket);

        } catch (DataIntegrityViolationException ex) {
            throw new ResourseAllredyFound("Duplicate value found");
        }
    }

    @Override
    public List<TicketDTO> getAll() {
        List<Ticket> allTickets = ticketRepository.findAll();
        if(allTickets.isEmpty()) {
            throw new ResourseNotFound("No tickets found");
        }
        return modelMapper.map(allTickets, new TypeToken<List<TicketDTO>>(){}.getType());
    }

    @Override
    public void deleteTicket(Integer id) {
        try {
            Ticket existingTicket = ticketRepository.findById(id)
                    .orElseThrow(() -> new ResourseNotFound("Ticket not found with id: " + id));

            ticketRepository.delete(existingTicket);

        } catch (ResourseNotFound ex) {
            throw ex;
        } catch (Exception ex) {
            throw new RuntimeException("Failed to delete ticket: " + ex.getMessage());
        }
    }
}
