package com.example.fenmoDemo.dao;

import com.example.fenmoDemo.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.yaml.snakeyaml.events.Event;

import java.util.Optional;

public interface SchoolRepository extends JpaRepository<School,Long>
{
    Optional<School> findByRegistrationNumber(String registrationNumber);
}
