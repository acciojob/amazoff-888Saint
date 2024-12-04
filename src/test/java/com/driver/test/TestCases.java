package com.driver.test;

import com.driver.*;
import com.driver.Order;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = Application.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCases {

    @Autowired
    private OrderController orderController;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private OrderService mockOrderService;

    @BeforeEach
    public void setup() {
        mockOrderService = mock(OrderService.class);
        orderController = new OrderController(mockOrderService);
    }

    @Test
    public void testOrderController() {
        // Create an instance of OrderService (it could be a mock)
        OrderService orderService = new OrderService();

        // Now pass the OrderService instance to the OrderController constructor
        OrderController orderController = new OrderController(orderService);

        // Verifying that OrderController is properly initialized
        assertNotNull(orderController, "OrderController should be initialized");
    }


    @Test
    public void testAddOrder() {
        Order order = new Order("order123", "12:30");
        ResponseEntity<String> expectedOutput = new ResponseEntity<>("New order added successfully", HttpStatus.CREATED);

        ResponseEntity<String> actualOutput = orderController.addOrder(order);

        assertEquals(expectedOutput.getStatusCode(), actualOutput.getStatusCode());
        assertEquals(expectedOutput.getBody(), actualOutput.getBody());
    }

    @Test
    public void testAddOrderPartnerPair() {
        String orderId = "order123";
        String partnerId = "partner456";
        ResponseEntity<String> expectedOutput = new ResponseEntity<>("New order-partner pair added successfully", HttpStatus.CREATED);

        ResponseEntity<String> actualOutput = orderController.addOrderPartnerPair(orderId, partnerId);

        assertEquals(expectedOutput.getStatusCode(), actualOutput.getStatusCode());
        assertEquals(expectedOutput.getBody(), actualOutput.getBody());
    }

    @Test
    public void testAddPartner() {
        String partnerId = "partner123";
        ResponseEntity<String> expectedOutput = new ResponseEntity<>("New delivery partner added successfully", HttpStatus.CREATED);

        ResponseEntity<String> actualOutput = orderController.addPartner(partnerId);

        assertEquals(expectedOutput.getStatusCode(), actualOutput.getStatusCode());
        assertEquals(expectedOutput.getBody(), actualOutput.getBody());
    }

    @Test
    public void testDeletePartnerById() {
        String partnerId = "partner123";
        String expectedMessage = partnerId + " removed successfully";
        ResponseEntity<String> expectedOutput = new ResponseEntity<>(expectedMessage, HttpStatus.CREATED);

        ResponseEntity<String> actualOutput = orderController.deletePartnerById(partnerId);

        assertEquals(expectedOutput.getStatusCode(), actualOutput.getStatusCode());
        assertEquals(expectedOutput.getBody(), actualOutput.getBody());
    }

    @Test
    public void testGetAllOrders() {
        List<String> orders = List.of("order1", "order2", "order3");
        when(mockOrderService.getAllOrders()).thenReturn(orders);

        ResponseEntity<List<String>> expectedOutput = new ResponseEntity<>(orders, HttpStatus.CREATED);

        ResponseEntity<List<String>> actualOutput = orderController.getAllOrders();

        assertEquals(expectedOutput.getStatusCode(), actualOutput.getStatusCode());
        assertEquals(expectedOutput.getBody(), actualOutput.getBody());
    }

    @Test
    public void testGetCountOfOrdersLeftAfterGivenTime() {
        String time = "12:30";
        String partnerId = "partner1";
        Integer countOfOrders = 3;
        when(mockOrderService.getOrdersLeftAfterGivenTimeByPartnerId(time, partnerId)).thenReturn(countOfOrders);

        ResponseEntity<Integer> expectedOutput = new ResponseEntity<>(countOfOrders, HttpStatus.CREATED);

        ResponseEntity<Integer> actualOutput = orderController.getOrdersLeftAfterGivenTimeByPartnerId(time, partnerId);

        assertEquals(expectedOutput.getStatusCode(), actualOutput.getStatusCode());
        assertEquals(expectedOutput.getBody(), actualOutput.getBody());
    }

    @Test
    public void testGetCountOfUnassignedOrders() {
        Integer countOfUnassignedOrders = 5;
        when(mockOrderService.getCountOfUnassignedOrders()).thenReturn(countOfUnassignedOrders);

        ResponseEntity<Integer> expectedOutput = new ResponseEntity<>(countOfUnassignedOrders, HttpStatus.CREATED);

        ResponseEntity<Integer> actualOutput = orderController.getCountOfUnassignedOrders();

        assertEquals(expectedOutput.getStatusCode(), actualOutput.getStatusCode());
        assertEquals(expectedOutput.getBody(), actualOutput.getBody());
    }

    @Test
    public void testGetLastDeliveryTime() {
        String partnerId = "partner1";
        String expectedLastDeliveryTime = "18:30";
        when(mockOrderService.getLastDeliveryTimeByPartnerId(partnerId)).thenReturn(expectedLastDeliveryTime);

        ResponseEntity<String> expectedOutput = new ResponseEntity<>(expectedLastDeliveryTime, HttpStatus.CREATED);

        ResponseEntity<String> actualOutput = orderController.getLastDeliveryTimeByPartnerId(partnerId);

        assertEquals(expectedOutput.getStatusCode(), actualOutput.getStatusCode());
        assertEquals(expectedOutput.getBody(), actualOutput.getBody());
    }

    @Test
    public void testGetOrderById() {
        String orderId = "order1";
        Order expectedOrder = new Order(orderId, "12:30");
        when(mockOrderService.getOrderById(orderId)).thenReturn(expectedOrder);

        ResponseEntity<Order> expectedOutput = new ResponseEntity<>(expectedOrder, HttpStatus.CREATED);

        ResponseEntity<Order> actualOutput = orderController.getOrderById(orderId);

        assertEquals(expectedOutput.getStatusCode(), actualOutput.getStatusCode());
        assertEquals(expectedOutput.getBody(), actualOutput.getBody());
    }

    @Test
    public void testGetOrderCountByPartnerId() {
        String partnerId = "partner1";
        int expectedOrderCount = 5;
        when(mockOrderService.getOrderCountByPartnerId(partnerId)).thenReturn(expectedOrderCount);

        ResponseEntity<Integer> expectedOutput = new ResponseEntity<>(expectedOrderCount, HttpStatus.CREATED);

        ResponseEntity<Integer> actualOutput = orderController.getOrderCountByPartnerId(partnerId);

        assertEquals(expectedOutput.getStatusCode(), actualOutput.getStatusCode());
        assertEquals(expectedOutput.getBody(), actualOutput.getBody());
    }

    @Test
    public void testGetOrdersByPartnerId() {
        String partnerId = "partner1";
        List<String> expectedOrders = List.of("order1", "order2", "order3");
        when(mockOrderService.getOrdersByPartnerId(partnerId)).thenReturn(expectedOrders);

        ResponseEntity<List<String>> expectedOutput = new ResponseEntity<>(expectedOrders, HttpStatus.CREATED);

        ResponseEntity<List<String>> actualOutput = orderController.getOrdersByPartnerId(partnerId);

        assertEquals(expectedOutput.getStatusCode(), actualOutput.getStatusCode());
        assertEquals(expectedOutput.getBody(), actualOutput.getBody());
    }

    @Test
    public void testGetPartnerById() {
        String partnerId = "partner1";
        DeliveryPartner expectedPartner = new DeliveryPartner(partnerId);
        when(mockOrderService.getPartnerById(partnerId)).thenReturn(expectedPartner);

        ResponseEntity<DeliveryPartner> expectedOutput = new ResponseEntity<>(expectedPartner, HttpStatus.CREATED);

        ResponseEntity<DeliveryPartner> actualOutput = orderController.getPartnerById(partnerId);

        assertEquals(expectedOutput.getStatusCode(), actualOutput.getStatusCode());
        assertEquals(expectedOutput.getBody(), actualOutput.getBody());
    }
}
