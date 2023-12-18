package org.shopping.server.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressBody {
    @NotNull
    @NotBlank
    @Size(max = 512)
    private String addressLine1;

    @Size(max = 512)
    private String addressLine2;

    @NotNull
    @NotBlank
    @Size(max = 75)
    private String city;

    @NotNull
    @NotBlank
    @Size(max = 75)
    private String country;

    @NotNull
    @NotBlank
    @Size(max = 10)
    private String zipCode;
}
