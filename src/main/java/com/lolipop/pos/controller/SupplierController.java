package com.lolipop.pos.controller;

import com.lolipop.pos.entity.SupplierEntity;
import com.lolipop.pos.service.SupplierService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/supplier")
public class SupplierController {
    private final SupplierService service;

    @GetMapping
    public List<SupplierEntity> getAll() {
        return service.getAll();
    }

    @PostMapping("/add")
    public SupplierEntity create(@Valid @RequestBody SupplierEntity entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public SupplierEntity update(@PathVariable Long id, @RequestBody SupplierEntity entity) {
        entity.setId(id);
        return service.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}