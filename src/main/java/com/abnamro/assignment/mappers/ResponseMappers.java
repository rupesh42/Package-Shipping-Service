package com.abnamro.assignment.mappers;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.abnamro.assignment.ship.ShippingOrder;
import com.abnamro.assignment.ship.ShippingOrderProjection;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ResponseMappers {

	ResponseMappers INSTANCE = Mappers.getMapper(ResponseMappers.class);

	@Mapping(target = "deliveryDate", source = "actualDeliveryDate")
	ShippingOrderProjection getOrdersToDto(ShippingOrder shippingOrder);

	@Mapping(target = "deliveryDate", source = "actualDeliveryDate")
	List<ShippingOrderProjection> getOrdersListToDto(List<ShippingOrder> shippingOrder);

}
