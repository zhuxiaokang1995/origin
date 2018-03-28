package com.mj.holley.ims.repository;

import com.mj.holley.ims.domain.Sn;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Spring Data JPA repository for the Sn entity.
 */
@SuppressWarnings("unused")
public interface SnRepository extends JpaRepository<Sn,Long> {

    Optional<Sn> findFirstByHutIDAndIsBindingTrueOrderByIdDesc(String hutID);
}
