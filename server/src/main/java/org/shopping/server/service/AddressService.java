package org.shopping.server.service;

import org.shopping.server.api.model.AddressBody;
import org.shopping.server.model.Address;
import org.shopping.server.model.LocalUser;
import org.shopping.server.model.dao.AddressDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private final AddressDAO addressDAO;

    public AddressService(AddressDAO addressDAO) {
        this.addressDAO = addressDAO;
    }

    public Address addAddress(AddressBody addressBody, LocalUser user) {
        Address address = new Address();
        address.setAddressLine1(addressBody.getAddressLine1());
        address.setAddressLine2(addressBody.getAddressLine2());
        address.setCity(addressBody.getCity());
        address.setCountry(addressBody.getCountry());
        address.setZipCode(addressBody.getZipCode());
        address.setUser(user);

        return addressDAO.save(address);
    }

    public void deleteAddress(LocalUser user) {
        addressDAO.deleteByUser(user);
    }

    public List<Address> getAddresses(LocalUser user) {
        return addressDAO.findAllByUser(user);
    }
}
