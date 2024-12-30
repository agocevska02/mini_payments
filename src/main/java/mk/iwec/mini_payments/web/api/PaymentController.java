package mk.iwec.mini_payments.web.api;

import mk.iwec.mini_payments.model.dto.PaymentDto;
import mk.iwec.mini_payments.service.PaymentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<PaymentDto> createPayment(@RequestBody PaymentDto paymentDto) {
        PaymentDto createdPayment = paymentService.createPayment(paymentDto);
        return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<PaymentDto> getPaymentById(@PathVariable UUID uuid) {
        PaymentDto paymentDto = paymentService.getPaymentById(uuid);
        return paymentDto != null ?
                new ResponseEntity<>(paymentDto, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity<Page<PaymentDto>> getAllPayments(Pageable pageable) {
        Page<PaymentDto> payments = paymentService.getAllPayments(pageable);
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<PaymentDto> updatePayment(@PathVariable UUID uuid, @RequestBody PaymentDto paymentDto) {
        PaymentDto updatedPayment = paymentService.updatePayment(uuid, paymentDto);
        return updatedPayment != null ?
                new ResponseEntity<>(updatedPayment, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deletePayment(@PathVariable UUID uuid) {
        paymentService.deletePayment(uuid);
        return ResponseEntity.noContent().build();
    }
}
