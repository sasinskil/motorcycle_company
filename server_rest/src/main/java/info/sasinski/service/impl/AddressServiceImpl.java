package info.sasinski.service.impl;

import info.sasinski.entity.Address;
import info.sasinski.repository.AddressRepository;
import info.sasinski.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public List<Address> findAll() {
        Iterable<Address> iterableAddress = addressRepository.findAll();

        List<Address> getAll = new ArrayList<>();

        for (Address a : iterableAddress) {
            getAll.add(a);
        }

        return getAll;
    }

    @Override
    public Address findAddressById(long id) {
        return addressRepository.findAddressById(id);
    }


    @Override
    public void saveAddress(Address address) {

        addressRepository.save(address);

    }
}
