package org.binaracademy.challenge_4.repository;

import org.binaracademy.challenge_4.entity.Merchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    Page<Merchant> findAllByIsOpen(boolean open, Pageable pageable);
    @Modifying
    @Query("DELETE FROM Merchant a WHERE a.id = :id")
    void deleteById(@Param("id") Long id);
}
