package org.binaracademy.challenge_4.repository;

import org.binaracademy.challenge_4.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUser_IdAndIsCompleted(Long id, Boolean isCompleted, Pageable pageable);
}
