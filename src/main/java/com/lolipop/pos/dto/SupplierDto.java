package com.lolipop.pos.dto;

import com.lolipop.pos.entity.SupplierEntity;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SupplierDto {
    private Long id;
    @Min(value = 10, message = "need 10 chars")
    private String name;
    private String code;
    private String company;
    private Address address;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public SupplierDto(SupplierEntity supplierEntity) {
        this.id = supplierEntity.getId();
        this.name = supplierEntity.getName();
        this.code = supplierEntity.getCode();
        this.company = supplierEntity.getCompany();
        this.address = supplierEntity.getAddress();
    }

}