package mk.iwec.mini_payments.repository;

import mk.iwec.mini_payments.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
