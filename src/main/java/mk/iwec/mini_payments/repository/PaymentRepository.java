package mk.iwec.mini_payments.repository;

import mk.iwec.mini_payments.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
