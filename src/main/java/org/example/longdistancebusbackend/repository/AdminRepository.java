package org.example.longdistancebusbackend.repository;

import org.example.longdistancebusbackend.entity.Admin;
import org.example.longdistancebusbackend.entity.BookingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {
}
