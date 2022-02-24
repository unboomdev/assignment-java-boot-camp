package com.example.bootcamp.user;

import com.example.bootcamp.user.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Integer> {
    Optional<Address> findByUserIdAndIsDefault(int userId, boolean isDefault);
}
