package com.mostafa.cashing_app.service;

import com.mostafa.cashing_app.entity.Order;
import com.mostafa.cashing_app.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;


    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    @Cacheable(value = "order" , key = "#id")
    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Cacheable(value = "order")
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Caching(evict = {
            @CacheEvict(value = "order" , allEntries = true),
            @CacheEvict(value = "order" , key = "#id")
    })
    public String deleteOrderById(Long id) {
        orderRepository.deleteById(id);
        return "Order deleted successfully!";

    }

}
