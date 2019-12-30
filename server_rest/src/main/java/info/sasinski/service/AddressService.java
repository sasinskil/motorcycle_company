package info.sasinski.service;

import info.sasinski.entity.Address;
import info.sasinski.repository.AddressRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor

@Service
public class AddressService {

    final AddressRepository _addressRepository;

    public List<Address> findAll() {
        Iterable<Address> iterableAddress = _addressRepository.findAll();

        List<Address> getAll = new ArrayList<>();

        for (Address a : iterableAddress) {
            getAll.add(a);
        }

        return getAll;
    }

    public Address findAddressById(long id) {
        return _addressRepository.findById(id)
                .orElse(null);
    }

    public void saveAddress(Address address) {
        _addressRepository.save(address);
    }
}
