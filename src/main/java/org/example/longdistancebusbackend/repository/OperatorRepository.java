package org.example.longdistancebusbackend.repository;

import org.example.longdistancebusbackend.entity.Operator;
import org.example.longdistancebusbackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperatorRepository extends JpaRepository<Operator, Integer> {
}
