package com.example.bootcamp.user;

import com.example.bootcamp.common.ExceptionResponse;
import com.example.bootcamp.user.entities.Address;
import com.example.bootcamp.user.exception.AddressNotFoundException;
import com.example.bootcamp.user.model.AddressResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Test
    void getAddress_Success() {
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

        UserService userService = new UserService();
        userService.setAddressRepository(addressRepository);
        AddressResponse result = userService.getAddress(20001);

        assertEquals(1, result.getId());
    }

    @Test
    void getAddress_Not_Found_And_Throw_AddressNotFoundException() {
        when(addressRepository.findByUserIdAndIsDefault(20001, true)).thenReturn(Optional.empty());

        UserService userService = new UserService();
        userService.setAddressRepository(addressRepository);

        assertThrows(AddressNotFoundException.class, () -> userService.getAddress(20001));
    }
}