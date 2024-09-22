package com.lolipop.pos.repository;

import com.lolipop.pos.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    ProductEntity findByName(String name);
    ProductEntity findBySku(String sku);

    @Query("SELECT P FROM ProductEntity P WHERE (:fromDate is null or P.createdAt between :fromDate and :toDate) " +
            " and (:name is null or P.sku like %:name% or P.name like %:name% or P.barcode like %:name%)")
    Page<ProductEntity> findAllBySearch(LocalDateTime fromDate, LocalDateTime toDate, String name, Pageable pageable);
}
