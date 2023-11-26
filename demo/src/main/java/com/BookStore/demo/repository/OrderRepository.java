package com.BookStore.demo.repository;

import com.BookStore.demo.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	List<Order> findAllByUserId(Long userId);

	Order findOrderDetailsById(Long orderId);
}