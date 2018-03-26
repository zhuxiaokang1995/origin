package com.mj.holley.ims.repository;

import com.mj.holley.ims.domain.Sn;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Sn entity.
 */
@SuppressWarnings("unused")
public interface SnRepository extends JpaRepository<Sn,Long> {

}
