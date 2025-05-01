package com.abnamro.assignment.ship;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.abnamro.assignment.utils.OrderStatus;

@Repository
public interface ShippingOrderRepository extends JpaRepository<ShippingOrder, Long> {

	@Query("SELECT s FROM ShippingOrder s WHERE s.senderID.employeeID = :senderID AND s.orderStatus = :orderStatus")
	List<ShippingOrder> findBySenderIDAndOrderStatus(@Param("senderID") String senderID,
			@Param("orderStatus") OrderStatus orderStatus);

}