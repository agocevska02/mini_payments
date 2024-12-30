package mk.iwec.mini_payments.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import mk.iwec.mini_payments.model.enumeration.PaymentType;

@Getter
@Setter
public class PaymentDto {
        Double amount;
        PaymentType paymentType;
        CustomerDto customerDto;
}
