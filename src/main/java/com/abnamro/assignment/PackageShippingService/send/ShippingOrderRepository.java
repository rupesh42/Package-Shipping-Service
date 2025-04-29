package com.abnamro.assignment.PackageShippingService.send;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingOrderRepository extends JpaRepository<ShippingOrder, Long> {
}
