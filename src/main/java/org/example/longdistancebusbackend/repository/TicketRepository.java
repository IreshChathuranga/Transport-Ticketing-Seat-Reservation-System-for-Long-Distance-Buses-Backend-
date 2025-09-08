package org.example.longdistancebusbackend.repository;

import org.example.longdistancebusbackend.entity.BookingItem;
import org.example.longdistancebusbackend.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
