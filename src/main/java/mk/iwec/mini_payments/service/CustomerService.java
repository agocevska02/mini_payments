package mk.iwec.mini_payments.service;

import mk.iwec.mini_payments.model.dto.CustomerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface CustomerService {

    Page<CustomerDto> getAllCustomers(Pageable pageable);

    CustomerDto getCustomerByUUID(UUID uuid);

    CustomerDto createCustomer(CustomerDto customerDto);

    CustomerDto updateCustomer(UUID uuid, CustomerDto customerDto);

    void deleteCustomer(UUID uuid);
}
