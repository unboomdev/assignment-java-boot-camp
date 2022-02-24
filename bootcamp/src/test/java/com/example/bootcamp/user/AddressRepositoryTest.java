package com.example.bootcamp.user;

import com.example.bootcamp.user.entities.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    void findByUserIdAndIsDefault() {
        Optional<Address> address = addressRepository.findByUserIdAndIsDefault(20001, true);

        assertTrue(address.isPresent());
    }
}