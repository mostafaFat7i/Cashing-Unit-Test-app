package com.mostafa.cashing_app.service;


import com.mostafa.cashing_app.entity.Order;
import com.mostafa.cashing_app.repo.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    void testSaveOrder() {
        Order order = new Order();
        order.setId(1L);
        order.setDetails("Sample order");
        order.setQuantity(10);
        order.setPrice(100L);

        when(orderRepository.save(order)).thenReturn(order);

        Order savedOrder = orderService.saveOrder(order);

        assertEquals(order, savedOrder);
    }

    @Test
    void testGetAllOrders() {
        Order order1 = new Order();
        order1.setId(1L);
        order1.setDetails("Sample order 1");
        order1.setQuantity(10);
        order1.setPrice(100L);

        Order order2 = new Order();
        order2.setId(2L);
        order2.setDetails("Sample order 2");
        order2.setQuantity(20);
        order2.setPrice(200L);

        when(orderRepository.findAll()).thenReturn(Arrays.asList(order1, order2));

        List<Order> orders = orderService.getAllOrders();

        assertEquals(2, orders.size());
        assertEquals(order1, orders.get(0));
        assertEquals(order2, orders.get(1));
    }

    @Test
    void testGetOrderById() {
        Order order = new Order();
        order.setId(1L);
        order.setDetails("Sample order");
        order.setQuantity(10);
        order.setPrice(100L);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Order foundOrder = orderService.getOrderById(1L);

        assertEquals(order, foundOrder);
    }

    @Test
    void testGetOrderByIdNotFound() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            orderService.getOrderById(1L);
        });

        assertEquals("Order not found", exception.getMessage());
    }

    @Test
    void testDeleteOrderById() {
        doNothing().when(orderRepository).deleteById(1L);

        String result = orderService.deleteOrderById(1L);

        verify(orderRepository, times(1)).deleteById(1L);
        assertEquals("Order deleted successfully!", result);
    }
}
