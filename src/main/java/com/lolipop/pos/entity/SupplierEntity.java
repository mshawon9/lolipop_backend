package com.lolipop.pos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lolipop.pos.dto.Address;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "supplier")
@Data
public class SupplierEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Min(value = 10, message = "need 10 chars")
    private String name;
    private String code;
    private String company;
    @Embedded
    private Address address;

    @CreationTimestamp
    private LocalDateTime createdAt;
    @JsonIgnore
    private String createdBy;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    @JsonIgnore
    private String updatedBy;
    @JsonIgnore
    private LocalDateTime deletedAt;
}