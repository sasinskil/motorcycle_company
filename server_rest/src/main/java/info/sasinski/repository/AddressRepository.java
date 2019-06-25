package info.sasinski.repository;

import info.sasinski.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
    Address findAddressById(long id);
}
