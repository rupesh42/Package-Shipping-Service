package com.abnamro.assignment.ship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.abnamro.assignment.employees.Employee;
import com.abnamro.assignment.employees.EmployeeRepository;
import com.abnamro.assignment.exceptions.InvalidStatusCode;
import com.abnamro.assignment.exceptions.OrderNotFoundException;
import com.abnamro.assignment.mappers.ResponseMappers;
import com.abnamro.assignment.utils.OrderStatus;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class ShippingOrderServiceImplTest {

	@InjectMocks
	private ShippingOrderServiceImpl serviceImpl;

	@Mock
	private EmployeeRepository empRepo;

	@Mock
	private ResponseMappers mappers;

	@Mock
	private ShippingOrderRepository shippingOrderRepository;

	private static List<ShippingOrder> shippingOrderList;
	private static List<ShippingOrderProjection> projectionList;

	private static Employee employee;

	@BeforeAll
	static void setUp() {

		employee = new Employee();
		employee.setEmployeeID("C100");
		employee.setHouseNumber(123);
		employee.setStreetName("street");
		employee.setPostCode("test123");

		ShippingOrder order1 = new ShippingOrder();
		order1.setOrderId(1L);
		order1.setOrderStatus(OrderStatus.DELIVERED);
		order1.setOrderDate(LocalDate.of(2023, 1, 1));
		order1.setActualDeliveryDate(LocalDate.of(2023, 1, 5));

		ShippingOrder order2 = new ShippingOrder();
		order2.setOrderId(2L);
		order2.setOrderStatus(OrderStatus.IN_PROGRESS);
		order2.setOrderDate(LocalDate.of(2023, 2, 1));
		order2.setActualDeliveryDate(LocalDate.of(2023, 2, 5));

		ShippingOrder order3 = new ShippingOrder();
		order3.setOrderId(3L);
		order3.setOrderStatus(OrderStatus.SENT);
		order3.setOrderDate(LocalDate.of(2023, 3, 1));
		order3.setActualDeliveryDate(LocalDate.of(2023, 3, 5));

		shippingOrderList = Arrays.asList(order1, order2, order3);

		projectionList = Arrays.asList(
				new ShippingOrderProjection("DELIVERED", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 5)),
				new ShippingOrderProjection("IN_PROGRESS", LocalDate.of(2023, 2, 1), LocalDate.of(2023, 2, 5)),
				new ShippingOrderProjection("Pending", LocalDate.of(2023, 3, 1), LocalDate.of(2023, 3, 5)));
	}

	@Test
	void testCreateShippingOrder() {

		ShippingOrder order = new ShippingOrder();
		order.setReceiverID(employee);
		order.setOrderId(1L);
		order.setPackageWeight(200D);
		order.setPackageName("Test");
		order.setSenderID(employee);

		when(empRepo.findByEmployeeID(any())).thenReturn(Optional.of(employee));
		when(shippingOrderRepository.save(any())).thenReturn(mock(ShippingOrder.class));
		assertNotNull(serviceImpl.createShippingOrder(order));

	}

	@Test
	void testGetShippingOrders() {
		Pageable pageable = PageRequest.of(0, 2);

		// Ensure the test data is correctly set up
		when(shippingOrderRepository.findAll()).thenReturn(shippingOrderList);
		when(mappers.getOrdersListToDto(shippingOrderList)).thenReturn(projectionList);

		Page<ShippingOrderProjection> result = serviceImpl.getShippingOrders(pageable);

		assertNotNull(result);
	}

	@Test
	void testGetShippingOrderById() {
		Long orderId = 1L;
		ShippingOrder shippingOrder = shippingOrderList.get(0);
		ShippingOrderProjection shippingOrderProjection = projectionList.get(0);

		when(shippingOrderRepository.findById(orderId)).thenReturn(Optional.of(shippingOrder));
		when(mappers.getOrdersToDto(shippingOrder)).thenReturn(shippingOrderProjection);

		ShippingOrderProjection result = serviceImpl.getShippingOrderById(orderId);

		assertEquals(shippingOrderProjection, result);
	}

	@Test
	void testGetShippingOrderById_OrderNotFound() {
		Long orderId = 4L; // Use an ID that does not exist in the list
		when(shippingOrderRepository.findById(orderId)).thenReturn(Optional.empty());
		assertThrows(OrderNotFoundException.class, () -> serviceImpl.getShippingOrderById(orderId));
	}

	@Test
	void testGetShippingOrdersBySenderIDAndStatus_InvalidStatus() {
		String senderID = "C100";
		String orderStatus = "INVALID_STATUS";
		assertThrows(InvalidStatusCode.class,
				() -> serviceImpl.getShippingOrdersBySenderIDAndStatus(senderID, orderStatus));
	}

	@Test
	void testGetShippingOrdersBySenderIDAndStatus_OrderNotFound() {
		String senderID = "C100";
		String orderStatus = "IN_PROGRESS";
		when(shippingOrderRepository.findBySenderIDAndOrderStatus(senderID, OrderStatus.valueOf(orderStatus)))
				.thenReturn(Arrays.asList());
		assertThrows(OrderNotFoundException.class,
				() -> serviceImpl.getShippingOrdersBySenderIDAndStatus(senderID, orderStatus));
	}

	@Test
	void testUpdateShippingOrderStatus() throws Exception {
		Long orderId = 1L;
		String orderStatus = OrderStatus.DELIVERED.name();
		ShippingOrder shippingOrder = shippingOrderList.get(0);
		when(shippingOrderRepository.findById(orderId)).thenReturn(Optional.of(shippingOrder));
		String result = serviceImpl.updateShippingOrderStatus(orderId, orderStatus);
		assertEquals("Order status has been updated to " + orderStatus, result);
		assertEquals(OrderStatus.DELIVERED.name(), shippingOrder.getOrderStatus().name());
		assertEquals(LocalDate.now(), shippingOrder.getActualDeliveryDate());
		verify(shippingOrderRepository).save(shippingOrder);
	}

	@Test
	void testUpdateShippingOrderStatus_OrderNotFound() {
		Long orderId = 4L; // Use an ID that does not exist in the list
		String orderStatus = "DELIVERED";

		when(shippingOrderRepository.findById(orderId)).thenReturn(Optional.empty());

		assertThrows(OrderNotFoundException.class, () -> serviceImpl.updateShippingOrderStatus(orderId, orderStatus));
	}

}
