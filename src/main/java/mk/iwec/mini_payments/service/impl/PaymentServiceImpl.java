package mk.iwec.mini_payments.service.impl;

import lombok.extern.slf4j.Slf4j;
import mk.iwec.mini_payments.mapper.CustomerMapper;
import mk.iwec.mini_payments.mapper.PaymentMapper;
import mk.iwec.mini_payments.model.Customer;
import mk.iwec.mini_payments.model.Payment;
import mk.iwec.mini_payments.model.dto.PaymentDto;
import mk.iwec.mini_payments.repository.PaymentRepository;
import mk.iwec.mini_payments.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final CustomerMapper customerMapper;
    private final CustomerServiceImpl customerServiceImpl;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper, CustomerMapper customerMapper, CustomerServiceImpl customerServiceImpl) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.customerMapper = customerMapper;
        this.customerServiceImpl = customerServiceImpl;
    }

    @Override
    public PaymentDto createPayment(PaymentDto paymentDto) {
        log.info("Creating payment with amount: {}", paymentDto.getAmount());

        if (paymentDto.getCustomerDto() == null) {
            log.error("Customer information is required");
            throw new IllegalArgumentException("Customer information is required");
        }

        if (paymentDto.getPaymentType() == null) {
            log.error("Payment type is required");
            throw new IllegalArgumentException("Payment type is required");
        }

        Payment payment = paymentMapper.dtoToEntity(paymentDto);
        payment.setCreatedAt(LocalDateTime.now());
        payment.setCustomer(customerMapper.dtoToEntity(paymentDto.getCustomerDto()));

        Payment savedPayment = paymentRepository.save(payment);

        return paymentMapper.entityToDto(savedPayment);
    }

    @Override
    public PaymentDto getPaymentById(UUID uuid) {
        log.info("Fetching payment by ID: {}", uuid);

        Optional<Payment> paymentOptional = paymentRepository.findById(uuid);
        if (paymentOptional.isPresent()) {
            log.info("Payment found with ID: {}", uuid);
            return paymentMapper.entityToDto(paymentOptional.get());
        } else {
            log.warn("Payment not found with ID: {}", uuid);
            throw new NoSuchElementException("Payment not found with ID: " + uuid);
        }
    }

    @Override
    public Page<PaymentDto> getAllPayments(Pageable pageable) {
        log.info("Fetching all payments with pageable: {}", pageable);

        Page<PaymentDto> payments = paymentRepository.findAll(pageable).map(paymentMapper::entityToDto);
        log.info("Fetched {} payments", payments.getTotalElements());

        return payments;
    }

    @Override
    public PaymentDto updatePayment(UUID uuid, PaymentDto paymentDto) {
        log.info("Updating payment with ID: {}", uuid);

        Optional<Payment> paymentOptional = paymentRepository.findById(uuid);
        if (paymentOptional.isPresent()) {
            Payment payment = paymentOptional.get();
            payment.setAmount(paymentDto.getAmount());
            payment.setPaymentType(paymentDto.getPaymentType());
            payment.setCustomer(customerMapper.dtoToEntity(paymentDto.getCustomerDto()));

            payment = paymentRepository.save(payment);

            return paymentMapper.entityToDto(payment);
        } else {
            log.warn("Payment not found for update with ID: {}", uuid);
            throw new NoSuchElementException("Payment not found for update with ID: " + uuid);
        }
    }

    @Override
    public void deletePayment(UUID uuid) {
        log.info("Deleting payment with ID: {}", uuid);

        Optional<Payment> paymentOptional = paymentRepository.findById(uuid);
        if (paymentOptional.isPresent()) {
            paymentRepository.deleteById(uuid);
            log.info("Payment with ID: {} deleted", uuid);
        } else {
            log.warn("Payment not found with ID: {}", uuid);
            throw new NoSuchElementException("Payment not found with ID: " + uuid);
        }
    }
}
