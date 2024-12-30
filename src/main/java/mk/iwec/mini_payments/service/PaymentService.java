package mk.iwec.mini_payments.service;

import mk.iwec.mini_payments.model.dto.PaymentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface PaymentService {

    PaymentDto createPayment(PaymentDto paymentDto);

    PaymentDto getPaymentById(UUID uuid);

    Page<PaymentDto> getAllPayments(Pageable pageable);

    PaymentDto updatePayment(UUID uuid, PaymentDto paymentDto);

    void deletePayment(UUID uuid);
}
