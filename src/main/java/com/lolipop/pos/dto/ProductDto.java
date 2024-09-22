package com.lolipop.pos.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.lolipop.pos.entity.BrandEntity;
import com.lolipop.pos.entity.ProductEntity;
import com.lolipop.pos.entity.SupplierEntity;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Valid
@JsonTypeName("product")
public class ProductDto {
    private Long id;
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;
    private String description;
    @Min(5)
    private String shortName;
    @NotNull(message = "Required")
    private String sku;
    @NotEmpty(message = "Required")
    private String barcode;
    private String manufacturer;
    private BrandEntity brand;
    @JsonProperty("country_of_origin")
    private String countryOfOrigin;
    @NotNull(message = "Price cannot be null")
    @Min(value = 0, message = "Price must be a positive value")
    private Double price;
    @JsonProperty("manufacture_part_number")
    private String manufacturePartNumber;
    @JsonProperty("oem_part_number")
    private String oemPartNumber;
    private Double length;
    private Double height;
    private Double width;
    private Double weight;
    @JsonProperty("on_hand")
    private Integer onHand;
    private Integer allocated;
    private Integer available;
    @JsonProperty("unit_measurement_in_height")
    private String unitMeasurementInHeight;
    @JsonProperty("unit_measurement_in_weight")
    private String unitMeasurementInWeight;
    private SupplierEntity supplier;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;

    public ProductDto(ProductEntity productEntity) {
        this.id = productEntity.getId();
        this.name = productEntity.getName();
        this.description = productEntity.getDescription();
        this.shortName = productEntity.getShortName();
        this.sku = productEntity.getSku();
        this.barcode = productEntity.getBarcode();
        this.price = productEntity.getPrice();
        this.available = productEntity.getAvailable();
        this.allocated = productEntity.getAllocated();
        this.onHand = productEntity.getOnHand();
        this.manufacturer = productEntity.getManufacturer();
        this.manufacturePartNumber = productEntity.getManufacturePartNumber();
        this.oemPartNumber = productEntity.getOemPartNumber();
        this.length = productEntity.getLength();
        this.height = productEntity.getHeight();
        this.width = productEntity.getWidth();
        this.weight = productEntity.getWeight();
        this.countryOfOrigin = productEntity.getCountryOfOrigin();
        this.unitMeasurementInHeight = productEntity.getUnitMeasurementInHeight();
        this.unitMeasurementInWeight = productEntity.getUnitMeasurementInWeight();
        this.supplier = productEntity.getSupplier();
        this.brand = productEntity.getBrand();
    }
}
