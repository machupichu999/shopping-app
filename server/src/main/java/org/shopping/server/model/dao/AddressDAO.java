package org.shopping.server.model.dao;

import org.shopping.server.model.Address;
import org.shopping.server.model.LocalUser;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;
import java.util.Optional;

public interface AddressDAO extends ListCrudRepository<Address, Long> {

    void deleteByUser(LocalUser user);

    List<Address> findAllByUser(LocalUser user);
}
