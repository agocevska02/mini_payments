package mk.iwec.mini_payments.model.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class CustomerDto {
    UUID uuid;
    String firstName;
    String lastName;
    LocationDto location;

    public CustomerDto(String firstName, String lastName, LocationDto location) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
    }
}
