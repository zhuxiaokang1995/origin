package com.mj.holley.ims.repository;

import com.mj.holley.ims.domain.OrderInfo;
import com.mj.holley.ims.domain.RepeatProcess;

import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the RepeatProcess entity.
 */
@SuppressWarnings("unused")
@Transactional
public interface RepeatProcessRepository extends JpaRepository<RepeatProcess,Long> {

    @Transactional
    int deleteByOrderInfo(OrderInfo orderInfo);

    Optional<RepeatProcess> findByOrderInfo(OrderInfo orderInfo);
}
