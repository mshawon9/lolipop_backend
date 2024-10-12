package com.lolipop.pos.dto;

import com.lolipop.pos.entity.BrandEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BrandDto {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public BrandDto(BrandEntity brandEntity) {
        this.id = brandEntity.getId();
        this.name = brandEntity.getName();
        this.description = brandEntity.getDescription();
        this.createdAt = brandEntity.getCreatedAt();
        this.updatedAt = brandEntity.getUpdatedAt();
    }
}