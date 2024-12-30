package mk.iwec.mini_payments.web.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mk.iwec.mini_payments.model.Constants;
import mk.iwec.mini_payments.model.dto.CustomerDto;
import mk.iwec.mini_payments.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<Page<CustomerDto>> getAllCustomers(Pageable pageable) {
        Page<CustomerDto> customers = customerService.getAllCustomers(pageable);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable UUID uuid) {
        CustomerDto customer = customerService.getCustomerByUUID(uuid);
        return customer != null ?
                new ResponseEntity<>(customer, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody CustomerDto customerDto) {
        CustomerDto createdCustomer = customerService.createCustomer(customerDto);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable UUID uuid, @Valid @RequestBody CustomerDto customerDto) {
        CustomerDto updatedCustomer = customerService.updateCustomer(uuid, customerDto);
        return updatedCustomer != null ?
                new ResponseEntity<>(updatedCustomer, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID uuid) {
        customerService.deleteCustomer(uuid);
        return ResponseEntity.noContent().build();
    }
}
