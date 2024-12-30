package mk.iwec.mini_payments.service.impl;

import java.util.NoSuchElementException;
import lombok.extern.slf4j.Slf4j;
import mk.iwec.mini_payments.mapper.CustomerMapper;
import mk.iwec.mini_payments.model.Customer;
import mk.iwec.mini_payments.model.dto.CustomerDto;
import mk.iwec.mini_payments.repository.CustomerRepository;
import mk.iwec.mini_payments.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public Page<CustomerDto> getAllCustomers(Pageable pageable) {
        log.info("Fetching all customers with pagination: {}", pageable);
        Page<CustomerDto> customers = customerRepository.findAll(pageable)
                .map(customerMapper::entityToDto);
        log.info("Fetched {} customers", customers.getTotalElements());
        return customers;
    }

    @Override
    public CustomerDto getCustomerByUUID(UUID uuid) {
        log.info("Fetching customer with UUID: {}", uuid);
        return customerRepository.findById(uuid)
                .map(customer -> {
                    log.info("Customer found: {}", customer);
                    return customerMapper.entityToDto(customer);
                })
                .orElseThrow(() -> {
                    log.warn("Customer with UUID {} not found", uuid);
                    return new NoSuchElementException("Customer with UUID " + uuid + " not found");
                });
    }

    @Override
    public CustomerDto createCustomer(CustomerDto customerDto) {
        log.info("Creating new customer: {}", customerDto);
        Customer customer = customerMapper.dtoToEntity(customerDto);
        Customer savedCustomer = customerRepository.save(customer);
        log.info("Customer created with UUID: {}", savedCustomer.getUuid());
        return customerMapper.entityToDto(savedCustomer);
    }

    @Override
    public CustomerDto updateCustomer(UUID uuid, CustomerDto customerDto) {
        log.info("Updating customer with UUID: {}", uuid);
        return customerRepository.findById(uuid)
                .map(existingCustomer -> {
                    log.info("Customer found for update: {}", existingCustomer);
                    Customer updatedCustomer = customerMapper.dtoToEntity(customerDto);
                    updatedCustomer.setUuid(existingCustomer.getUuid());
                    Customer savedCustomer = customerRepository.save(updatedCustomer);
                    log.info("Customer updated with UUID: {}", savedCustomer.getUuid());
                    return customerMapper.entityToDto(savedCustomer);
                })
                .orElseThrow(() -> {
                    log.warn("Customer with UUID {} not found for update", uuid);
                    return new NoSuchElementException("Customer with UUID " + uuid + " not found for update");
                });
    }

    @Override
    public void deleteCustomer(UUID uuid) {
        log.info("Deleting customer with UUID: {}", uuid);
        if (!customerRepository.existsById(uuid)) {
            log.warn("Customer with UUID {} not found for deletion", uuid);
            throw new NoSuchElementException("Customer with UUID " + uuid + " not found for deletion");
        }
        customerRepository.deleteById(uuid);
        log.info("Customer with UUID {} deleted", uuid);
    }
}
