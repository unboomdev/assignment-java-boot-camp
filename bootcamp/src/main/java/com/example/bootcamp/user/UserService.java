package com.example.bootcamp.user;

import com.example.bootcamp.user.entities.Address;
import com.example.bootcamp.user.exception.AddressNotFoundException;
import com.example.bootcamp.user.model.AddressResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private AddressRepository addressRepository;

    public void setAddressRepository(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public AddressResponse getAddress(int userId) {
        Optional<Address> result = addressRepository.findByUserIdAndIsDefault(userId, true);
        if (result.isPresent()) {
            AddressResponse response = new AddressResponse();
            response.setId(result.get().getId());
            response.setEmail(result.get().getEmail());
            response.setAddress(result.get().getAddress());
            response.setZipcode(result.get().getZipcode());
            response.setDistrict(result.get().getDistrict());
            response.setProvince(result.get().getProvince());
            response.setMobileNumber(result.get().getMobileNumber());
            response.setDefault(result.get().getDefault());
            return response;
        }
        throw new AddressNotFoundException("Address not found");
    }
}
