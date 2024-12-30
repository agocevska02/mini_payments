package mk.iwec.mini_payments.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.iwec.mini_payments.model.enumeration.PaymentType;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "payments")
public class Payment extends BaseEntity {

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type", nullable = false)
    private PaymentType paymentType;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_uuid", nullable = false)
    private Customer customer;

    public Payment(Double amount, PaymentType type, Customer customer) {
        this.amount = amount;
        this.paymentType = type;
        this.customer = customer;
        this.createdAt = LocalDateTime.now();
    }
}
