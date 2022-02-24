package com.example.bootcamp.user;

import com.example.bootcamp.common.ExceptionResponse;
import com.example.bootcamp.user.entities.Address;
import com.example.bootcamp.user.model.AddressResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @MockBean
    private AddressRepository addressRepository;

    @Test
    void getAddress() {
        Address address = new Address();
        address.setId(1);
        address.setEmail("john345@test.com");
        address.setAddress("123/4 ซอยจุ๊");
        address.setZipcode("10234");
        address.setDistrict("พระราม 10");
        address.setProvince("กรุงเทพ");
        address.setMobileNumber("0987654321");
        address.setDefault(true);
        address.setUserId(20001);
        when(addressRepository.findByUserIdAndIsDefault(20001, true)).thenReturn(Optional.of(address));

        AddressResponse result = testRestTemplate.getForObject("/address/20001", AddressResponse.class);

        assertEquals(1, result.getId());
    }

    @Test
    void getAddressNotFound() {
        when(addressRepository.findByUserIdAndIsDefault(20001, true)).thenReturn(Optional.empty());

        ExceptionResponse result = testRestTemplate.getForObject("/address/20001", ExceptionResponse.class);

        assertEquals("Address not found", result.getMessage());
    }

    @Test
    void getBadRequest() {
        ResponseEntity<Object> result = testRestTemplate.getForEntity("/address/bkk", Object.class);

        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }
}