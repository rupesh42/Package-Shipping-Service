package com.abnamro.assignment.ship;

import java.time.LocalDate;

public record ShippingOrderProjection(String orderStatus, LocalDate orderDate, LocalDate deliveryDate) {

}
