package org.shopping.server.api.controller.addresses;

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
import java.util.Optional;

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

    @GetMapping("/{id}")
    public ResponseEntity findAddressById(
            @AuthenticationPrincipal LocalUser user,
            @RequestParam long id
    ) {
        Optional<Address> address = addressService.getAddressById(id);

        if (address.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok().build();
    }
}
