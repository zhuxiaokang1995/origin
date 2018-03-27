package com.mj.holley.ims.repository;

import com.mj.holley.ims.domain.ProcessControl;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the ProcessControl entity.
 */
@SuppressWarnings("unused")
public interface ProcessControlRepository extends JpaRepository<ProcessControl,Long> {

    Optional<ProcessControl> findOneBySerialNumberAndStationId(String serialNumber,String stationId);

}
