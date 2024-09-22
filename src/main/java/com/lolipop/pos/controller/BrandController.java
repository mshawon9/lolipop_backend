package com.lolipop.pos.controller;

import com.lolipop.pos.entity.BrandEntity;
import com.lolipop.pos.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/brand")
public class BrandController {
    private final BrandService brandService;

    @GetMapping
    public List<BrandEntity> getAll() {
        return brandService.getAll();
    }

    @PostMapping("/add")
    public BrandEntity create(@RequestBody BrandEntity entity) {
        return brandService.save(entity);
    }

    @PutMapping("/{id}")
    public BrandEntity update(@PathVariable Long id, @RequestBody BrandEntity entity) {
        entity.setId(id);
        return brandService.save(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        brandService.delete(id);
    }
}
