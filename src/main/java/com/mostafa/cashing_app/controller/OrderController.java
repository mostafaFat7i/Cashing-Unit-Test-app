package com.mostafa.cashing_app.controller;

import com.mostafa.cashing_app.entity.Order;
import com.mostafa.cashing_app.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;


   @PostMapping("/save")
    public Order saveOrder(@RequestBody Order order) {
        return orderService.saveOrder(order);
    }

    @GetMapping("/getAll")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
    @GetMapping("/get/{id}")
    public Order getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteOrderById(@PathVariable Long id) {
        return orderService.deleteOrderById(id);
    }

}
