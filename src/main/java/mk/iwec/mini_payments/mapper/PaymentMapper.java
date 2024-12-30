package mk.iwec.mini_payments.mapper;

import mk.iwec.mini_payments.model.Payment;
import mk.iwec.mini_payments.model.dto.PaymentDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring", uses = CustomerMapper.class)
public interface PaymentMapper extends GeneralMapper<PaymentDto, Payment> {

    @Override
    @Mapping(target = "customerDto", source = "customer")
    PaymentDto entityToDto(Payment payment);

    @Override
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "customer", source = "customerDto")
    Payment dtoToEntity(PaymentDto paymentDTO);

}