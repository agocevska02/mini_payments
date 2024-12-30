package mk.iwec.mini_payments.service;

import mk.iwec.mini_payments.model.dto.LocationDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.UUID;

public interface LocationService {
    LocationDto createLocation(LocationDto locationDto);
    LocationDto getLocationById(UUID uuid);
    LocationDto updateLocation(UUID uuid, LocationDto locationDto);
    void deleteLocation(UUID uuid);
    Page<LocationDto> getAllLocations(Pageable pageable);
}
