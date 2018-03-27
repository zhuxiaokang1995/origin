package com.mj.holley.ims.repository;

import com.mj.holley.ims.domain.OrderInfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the OrderInfo entity.
 */
@SuppressWarnings("unused")
public interface OrderInfoRepository extends JpaRepository<OrderInfo,Long> {

    List<OrderInfo> findAllByOrderID(String OrderID);

    Optional<OrderInfo> findOneByOrderID(String OrderID);

}
