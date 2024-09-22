package com.lolipop.pos.service;

import com.lolipop.pos.entity.BrandEntity;

import java.util.List;

public interface BrandService {
    List<BrandEntity> getAll();

    BrandEntity save(BrandEntity entity);

    void delete(Long id);
}