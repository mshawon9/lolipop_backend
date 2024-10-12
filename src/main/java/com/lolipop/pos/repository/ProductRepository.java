package com.lolipop.pos.repository;

import com.lolipop.pos.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findByName(String name);
    Optional<ProductEntity> findBySkuAndDeletedAtIsNull(String sku);

    @EntityGraph(value = "product-graph", type = EntityGraph.EntityGraphType.LOAD)
    Optional<ProductEntity> findByIdAndDeletedAtIsNull(Long id);

    @EntityGraph(value = "product-graph", type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT P FROM ProductEntity P WHERE P.deletedAt is null AND (:fromDate is null or P.createdAt between :fromDate and :toDate) " +
            " and (:name is null or P.sku like %:name% or P.name like %:name% or P.barcode like %:name%)")
    Page<ProductEntity> findAllBySearch(LocalDateTime fromDate, LocalDateTime toDate, String name, Pageable pageable);

    Optional<ProductEntity> findByBarcodeAndDeletedAtIsNull(String barcode);
}
