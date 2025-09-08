package org.example.longdistancebusbackend.service;

import org.example.longdistancebusbackend.dto.BookingItemDTO;
import org.example.longdistancebusbackend.dto.TicketDTO;

import java.util.List;

public interface TicketService {
    void saveTicket(TicketDTO ticketDTO);
    public void updateTicket(TicketDTO ticketDTO);
    List<TicketDTO> getAll();
    void deleteTicket(Integer id);
}
