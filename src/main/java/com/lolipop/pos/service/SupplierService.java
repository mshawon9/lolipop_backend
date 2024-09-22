package com.lolipop.pos.service;

import com.lolipop.pos.entity.SupplierEntity;

import java.util.List;

public interface SupplierService {
    List<SupplierEntity> getAll();

    SupplierEntity save(SupplierEntity entity);

    void delete(Long id);
}