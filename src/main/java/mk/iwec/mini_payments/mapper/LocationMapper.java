package mk.iwec.mini_payments.mapper;

import mk.iwec.mini_payments.model.Location;
import mk.iwec.mini_payments.model.dto.LocationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocationMapper extends GeneralMapper<LocationDto, Location> {

    @Override
    LocationDto entityToDto(Location location);

    @Override
    @Mapping(target = "uuid", ignore = true)
    Location dtoToEntity(LocationDto locationDTO);
}