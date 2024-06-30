package com.mostafa.cashing_app.controller;

import com.mostafa.cashing_app.entity.Order;
import com.mostafa.cashing_app.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Test
    void testSaveOrder() throws Exception {
        Order order = new Order();
        order.setId(1L);
        order.setDetails("Sample order");
        order.setQuantity(10);
        order.setPrice(100L);

        when(orderService.saveOrder(any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/order/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"details\": \"Sample order\", \"quantity\": 10, \"price\": 100}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.details", is("Sample order")))
                .andExpect(jsonPath("$.quantity", is(10)))
                .andExpect(jsonPath("$.price", is(100)));
    }

    @Test
    void testGetAllOrders() throws Exception {
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

        when(orderService.getAllOrders()).thenReturn(Arrays.asList(order1, order2));

        mockMvc.perform(get("/order/getAll"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].details", is("Sample order 1")))
                .andExpect(jsonPath("$[0].quantity", is(10)))
                .andExpect(jsonPath("$[0].price", is(100)))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].details", is("Sample order 2")))
                .andExpect(jsonPath("$[1].quantity", is(20)))
                .andExpect(jsonPath("$[1].price", is(200)));
    }

    @Test
    void testGetOrderById() throws Exception {
        Order order = new Order();
        order.setId(1L);
        order.setDetails("Sample order");
        order.setQuantity(10);
        order.setPrice(100L);

        when(orderService.getOrderById(1L)).thenReturn(order);

        mockMvc.perform(get("/order/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.details", is("Sample order")))
                .andExpect(jsonPath("$.quantity", is(10)))
                .andExpect(jsonPath("$.price", is(100)));
    }

    @Test
    void testDeleteOrderById() throws Exception {
        when(orderService.deleteOrderById(1L)).thenReturn("Order deleted successfully!");

        mockMvc.perform(delete("/order/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Order deleted successfully!"));
    }
}
