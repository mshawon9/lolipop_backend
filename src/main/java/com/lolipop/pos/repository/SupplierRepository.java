package com.lolipop.pos.repository;
import com.lolipop.pos.entity.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
public interface SupplierRepository extends JpaRepository<SupplierEntity, Long> {}