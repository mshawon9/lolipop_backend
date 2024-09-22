package com.lolipop.pos.service.impl;

import com.lolipop.pos.entity.BrandEntity;
import com.lolipop.pos.repository.BrandRepository;
import com.lolipop.pos.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository repository;

    @Override
    public List<BrandEntity> getAll() {
        return repository.findAll();
    }

    @Override
    public BrandEntity save(BrandEntity entity) {
        return repository.save(entity);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}