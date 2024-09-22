package com.lolipop.pos.service.impl;

import com.lolipop.pos.entity.SupplierEntity;
import com.lolipop.pos.repository.SupplierRepository;
import com.lolipop.pos.service.SupplierService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class SupplierServiceImpl implements SupplierService {
    private final SupplierRepository repository;

    @Override
    public List<SupplierEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public SupplierEntity save(SupplierEntity entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}