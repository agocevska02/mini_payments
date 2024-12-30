package mk.iwec.mini_payments.mapper;

import mk.iwec.mini_payments.model.Customer;
import mk.iwec.mini_payments.model.dto.CustomerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {PaymentMapper.class, LocationMapper.class})
public interface CustomerMapper extends GeneralMapper<CustomerDto, Customer> {

    @Override
    @Mapping(target = "location", source = "location")
    CustomerDto entityToDto(Customer customer);

    @Override
    @Mapping(target = "uuid", ignore = true)
    @Mapping(target = "location", source = "location")
    Customer dtoToEntity(CustomerDto dto);
}