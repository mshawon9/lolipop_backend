package com.lolipop.pos.entity;

import com.lolipop.pos.dto.ProductDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "product", indexes = {
        @Index(name = "idx_sku", columnList = "sku"),
        @Index(name = "idx_name", columnList = "name"),
        @Index(name = "idx_barcode", columnList = "barcode")
})
@Data
@NoArgsConstructor
@NamedEntityGraph(
        name = "product-graph",
        attributeNodes = {
                @NamedAttributeNode("brand"),
                @NamedAttributeNode("supplier")
        }
)
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private String shortName;

    @Column(unique = true, nullable = false)
    private String sku;

    private String barcode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private BrandEntity brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id")
    private SupplierEntity supplier;

    private Double price;

    private Integer available;

    private Integer allocated;

    private Integer onHand;

    private String manufacturer;

    private String manufacturePartNumber;

    private String oemPartNumber;

    private Double length;

    private Double height;

    private Double width;

    private Double weight;

    private String countryOfOrigin;

    @Column(name = "unit_measurement_in_height")
    private String unitMeasurementInHeight;

    @Column(name = "unit_measurement_in_weight")
    private String unitMeasurementInWeight;

    @CreationTimestamp
    private LocalDateTime createdAt;

    private String createdBy;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private String updatedBy;

    private LocalDateTime deletedAt;

    public ProductEntity(ProductDto productDto) {
        this.id = productDto.getId();
        this.name = productDto.getName();
        this.description = productDto.getDescription();
        this.shortName = productDto.getShortName();
        this.sku = productDto.getSku();
        this.barcode = productDto.getBarcode();
        this.price = productDto.getPrice();
        this.available = productDto.getAvailable();
        this.allocated = productDto.getAllocated();
        this.onHand = productDto.getOnHand();
        this.manufacturer = productDto.getManufacturer();
        this.manufacturePartNumber = productDto.getManufacturePartNumber();
        this.oemPartNumber = productDto.getOemPartNumber();
        this.length = productDto.getLength();
        this.height = productDto.getHeight();
        this.width = productDto.getWidth();
        this.weight = productDto.getWeight();
        this.countryOfOrigin = productDto.getCountryOfOrigin();
        this.unitMeasurementInHeight = productDto.getUnitMeasurementInHeight();
        this.unitMeasurementInWeight = productDto.getUnitMeasurementInWeight();
    }
}
