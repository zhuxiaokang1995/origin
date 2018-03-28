package com.mj.holley.ims.repository;

import com.mj.holley.ims.domain.ProcessControl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the ProcessControl entity.
 */
@SuppressWarnings("unused")
public interface ProcessControlRepository extends JpaRepository<ProcessControl,Long> {

    Optional<ProcessControl> findOneBySerialNumberAndStationID(String serialNumber, String stationI);
}
