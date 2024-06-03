package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Order;
import com.example.demo.model.Store;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByStore(Store store);

    @Query("SELECT o FROM Order o JOIN FETCH o.admin WHERE o.id = :id")
    Optional<Order> findByIdWithAdmin(@Param("id") Long id);
}
