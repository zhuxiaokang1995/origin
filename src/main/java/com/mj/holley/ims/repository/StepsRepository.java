package com.mj.holley.ims.repository;

import com.mj.holley.ims.domain.OrderInfo;
import com.mj.holley.ims.domain.Steps;

import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Spring Data JPA repository for the Steps entity.
 */
@SuppressWarnings("unused")
public interface StepsRepository extends JpaRepository<Steps,Long> {

    @Transactional
    int deleteByOrderInfo(OrderInfo orderInfo);

    List<Steps> findByOrderInfo(OrderInfo orderInfo);
}
