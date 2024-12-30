package mk.iwec.mini_payments.service.impl;

import lombok.extern.slf4j.Slf4j;
import mk.iwec.mini_payments.mapper.LocationMapper;
import mk.iwec.mini_payments.model.Location;
import mk.iwec.mini_payments.model.dto.LocationDto;
import mk.iwec.mini_payments.repository.LocationRepository;
import mk.iwec.mini_payments.service.LocationService;
import java.util.NoSuchElementException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    public LocationServiceImpl(LocationRepository locationRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    @Override
    public LocationDto createLocation(LocationDto locationDTO) {
        log.info("Creating new location: {}", locationDTO);
        Location location = locationMapper.dtoToEntity(locationDTO);
        location = locationRepository.save(location);
        log.info("Location created with UUID: {}", location.getUuid());
        return locationMapper.entityToDto(location);
    }

    @Override
    public LocationDto getLocationById(UUID uuid) {
        log.info("Fetching location with UUID: {}", uuid);
        Optional<Location> location = locationRepository.findById(uuid);
        return location.map(loc -> {
            log.info("Location found: {}", loc);
            return locationMapper.entityToDto(loc);
        }).orElseThrow(() -> {
            log.warn("Location with UUID {} not found", uuid);
            return new NoSuchElementException("Location with UUID " + uuid + " not found");
        });
    }

    @Override
    public Page<LocationDto> getAllLocations(Pageable pageable) {
        log.info("Fetching all locations with pagination: {}", pageable);
        Page<Location> locationsPage = locationRepository.findAll(pageable);
        log.info("Fetched {} locations", locationsPage.getTotalElements());
        return locationsPage.map(locationMapper::entityToDto);
    }

    @Override
    public LocationDto updateLocation(UUID uuid, LocationDto locationDTO) {
        log.info("Updating location with UUID: {}", uuid);
        Optional<Location> location = locationRepository.findById(uuid);
        if (location.isPresent()) {
            Location existingLocation = location.get();
            existingLocation.setName(locationDTO.name);
            existingLocation = locationRepository.save(existingLocation);
            log.info("Location updated with UUID: {}", existingLocation.getUuid());
            return locationMapper.entityToDto(existingLocation);
        }
        log.warn("Location with UUID {} not found for update", uuid);
        throw new NoSuchElementException("Location with UUID " + uuid + " not found for update");
    }

    @Override
    public void deleteLocation(UUID uuid) {
        log.info("Deleting location with UUID: {}", uuid);
        if (!locationRepository.existsById(uuid)) {
            log.warn("Location with UUID {} not found for deletion", uuid);
            throw new NoSuchElementException("Location with UUID " + uuid + " not found for deletion");
        }
        locationRepository.deleteById(uuid);
        log.info("Location with UUID {} deleted", uuid);
    }
}
