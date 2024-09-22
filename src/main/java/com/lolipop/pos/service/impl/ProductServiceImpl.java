package com.lolipop.pos.service.impl;

import com.lolipop.pos.configuration.EntityAlreadyExistException;
import com.lolipop.pos.configuration.ResourceNotFoundException;
import com.lolipop.pos.dto.ProductDto;
import com.lolipop.pos.entity.ProductEntity;
import com.lolipop.pos.repository.ProductRepository;
import com.lolipop.pos.service.CommonService;
import com.lolipop.pos.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CommonService commonService;

    @Override
    @Transactional
    public ProductDto addProduct(ProductDto product) {
        ProductEntity productEntity = productRepository.findByName(product.getName());

        if (productEntity != null) {
            throw new EntityAlreadyExistException("Product", product.getName());
            //throw new Exception("Product already present on - " + product.getName());
        }

        productEntity = new ProductEntity(product);
        productEntity = productRepository.save(productEntity);
        BeanUtils.copyProperties(productEntity, product);
        return product;
    }

    @Override
    @Transactional
    public Optional<ProductEntity> updateProduct(Long id, ProductDto updatedProductData) {
        return productRepository.findById(id).map(existingProduct -> {

            // Update fields if they are not null
            if (updatedProductData.getName() != null) {
                existingProduct.setName(updatedProductData.getName());
            }
            if (updatedProductData.getDescription() != null) {
                existingProduct.setDescription(updatedProductData.getDescription());
            }
            if (updatedProductData.getShortName() != null) {
                existingProduct.setShortName(updatedProductData.getShortName());
            }
            if (updatedProductData.getSku() != null) {
                existingProduct.setSku(updatedProductData.getSku());
            }
            if (updatedProductData.getBarcode() != null) {
                existingProduct.setBarcode(updatedProductData.getBarcode());
            }
            if (updatedProductData.getBrand() != null) {
                existingProduct.setBrand(updatedProductData.getBrand());
            }
            if (updatedProductData.getPrice() != null) {
                existingProduct.setPrice(updatedProductData.getPrice());
            }
            if (updatedProductData.getAvailable() != null) {
                existingProduct.setAvailable(updatedProductData.getAvailable());
            }
            if (updatedProductData.getAllocated() != null) {
                existingProduct.setAllocated(updatedProductData.getAllocated());
            }
            if (updatedProductData.getOnHand() != null) {
                existingProduct.setOnHand(updatedProductData.getOnHand());
            }
            if (updatedProductData.getManufacturer() != null) {
                existingProduct.setManufacturer(updatedProductData.getManufacturer());
            }
            if (updatedProductData.getManufacturePartNumber() != null) {
                existingProduct.setManufacturePartNumber(updatedProductData.getManufacturePartNumber());
            }
            if (updatedProductData.getOemPartNumber() != null) {
                existingProduct.setOemPartNumber(updatedProductData.getOemPartNumber());
            }
            if (updatedProductData.getLength() != null) {
                existingProduct.setLength(updatedProductData.getLength());
            }
            if (updatedProductData.getHeight() != null) {
                existingProduct.setHeight(updatedProductData.getHeight());
            }
            if (updatedProductData.getWidth() != null) {
                existingProduct.setWidth(updatedProductData.getWidth());
            }
            if (updatedProductData.getWeight() != null) {
                existingProduct.setWeight(updatedProductData.getWeight());
            }
            if (updatedProductData.getCountryOfOrigin() != null) {
                existingProduct.setCountryOfOrigin(updatedProductData.getCountryOfOrigin());
            }
            if (updatedProductData.getUnitMeasurementInHeight() != null) {
                existingProduct.setUnitMeasurementInHeight(updatedProductData.getUnitMeasurementInHeight());
            }
            if (updatedProductData.getUnitMeasurementInWeight() != null) {
                existingProduct.setUnitMeasurementInWeight(updatedProductData.getUnitMeasurementInWeight());
            }
            if (updatedProductData.getSupplier() != null) {
                existingProduct.setSupplier(updatedProductData.getSupplier());
            }

            return productRepository.save(existingProduct);
        });
    }


    @Override
    public ProductDto getProductById(Long productId) {
        return productRepository.findById(productId).map(ProductDto::new)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
    }

    @Override
    public ProductDto getProductByName(String productName) throws Exception {
        ProductEntity productEntity = productRepository.findByName(productName);

        if (productEntity == null) {
            throw new Exception(commonService.generateMessage("Product", productName));
        }
        return new ProductDto(productEntity);
    }


    @Override
    public ProductDto getProductBySku(String sku) throws Exception {
        ProductEntity productEntity = productRepository.findBySku(sku);

        if (productEntity == null) {
            throw new Exception(commonService.generateMessage("Product", sku));
        }
        return new ProductDto(productEntity);
    }

    @Override
    public Page<ProductEntity> getProductListPageable(String fromDate, String toDate, String name, int pageNo, int pageSize, String sortField, String sortDir) throws Exception {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        LocalDateTime syncTo = null;
        if (!StringUtils.isEmpty(toDate)) {
            LocalDateTime formatTo = commonService.formateDate(toDate);
            syncTo = formatTo.plusDays(1);
        }

        return productRepository.findAllBySearch(
                StringUtils.isEmpty(fromDate) ? null : commonService.formateDate(fromDate),
                syncTo,
                StringUtils.isEmpty(name) ? null : name,
                pageable);
    }
}

