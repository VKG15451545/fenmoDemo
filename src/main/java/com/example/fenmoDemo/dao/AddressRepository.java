package com.example.fenmoDemo.dao;

import com.example.fenmoDemo.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
