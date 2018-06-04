package com.mj.holley.ims.repository;

import com.mj.holley.ims.domain.AbnormalInformation;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the AbnormalInformation entity.
 */
@SuppressWarnings("unused")
public interface AbnormalInformationRepository extends JpaRepository<AbnormalInformation,Long> {

}
