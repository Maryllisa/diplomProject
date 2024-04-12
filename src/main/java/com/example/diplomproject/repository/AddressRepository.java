package com.example.diplomproject.repository;

import com.example.diplomproject.model.entity.declaration.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
