package mk.iwec.mini_payments.web.api;

import jakarta.validation.Valid;
import mk.iwec.mini_payments.model.Constants;
import mk.iwec.mini_payments.model.dto.LocationDto;
import mk.iwec.mini_payments.service.impl.LocationServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    private final LocationServiceImpl locationService;

    public LocationController(LocationServiceImpl locationService) {
        this.locationService = locationService;
    }
    @GetMapping
    public ResponseEntity<Page<LocationDto>> getAllLocations(Pageable pageable) {
        Page<LocationDto> locationDTOs = locationService.getAllLocations(pageable);
        return new ResponseEntity<>(locationDTOs, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<LocationDto> createLocation(@Valid @RequestBody LocationDto locationDTO) {
        LocationDto createdLocation = locationService.createLocation(locationDTO);
        return createdLocation != null ?
                new ResponseEntity<>(createdLocation, HttpStatus.CREATED) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/{uuid}")
    public ResponseEntity<LocationDto> getLocationByUuid(@PathVariable UUID uuid) {
        LocationDto customerDTO = locationService.getLocationById(uuid);
        return customerDTO != null ?
                new ResponseEntity<>(customerDTO, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("/{uuid}")
    public ResponseEntity<LocationDto> updateLocation(@PathVariable UUID uuid, @Valid @RequestBody LocationDto locationDTO) {
        LocationDto updatedLocation = locationService.updateLocation(uuid, locationDTO);
        return updatedLocation != null ?
                new ResponseEntity<>(updatedLocation, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> deleteLocation(@PathVariable UUID uuid) {
        locationService.deleteLocation(uuid);
        return ResponseEntity.noContent().build();
    }
}
