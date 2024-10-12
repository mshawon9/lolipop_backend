package com.lolipop.pos.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "brand")
@Data
public class BrandEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;

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