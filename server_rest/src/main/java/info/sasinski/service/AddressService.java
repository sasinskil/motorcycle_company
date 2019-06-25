package info.sasinski.service;

import info.sasinski.entity.Address;

import java.util.List;

public interface AddressService {

    List<Address> findAll();

    Address findAddressById(long id);

    void saveAddress(Address address);
}
