package org.shopping.server.api.controller.address;

import jakarta.validation.Valid;
import org.shopping.server.api.model.AddressBody;
import org.shopping.server.model.Address;
import org.shopping.server.model.LocalUser;
import org.shopping.server.service.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity addAddress(
            @Valid @RequestBody AddressBody addressBody,
            @AuthenticationPrincipal LocalUser user
    ) {
        addressService.addAddress(addressBody, user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity deleteAddress(@AuthenticationPrincipal LocalUser user) {
        addressService.deleteAddress(user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<Address> findAllByUser(@AuthenticationPrincipal LocalUser user) {
        return addressService.getAddresses(user);
    }
}
