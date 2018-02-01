package com.mj.holley.ims.repository;

import com.mj.holley.ims.domain.ScanningRegistration;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ScanningRegistration entity.
 */
@SuppressWarnings("unused")
public interface ScanningRegistrationRepository extends JpaRepository<ScanningRegistration,Long> {

}
