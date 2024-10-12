package com.lolipop.pos.service.impl;

import com.lolipop.pos.configuration.EntityAlreadyExistException;
import com.lolipop.pos.configuration.ResourceNotFoundException;
import com.lolipop.pos.dto.ProductDto;
import com.lolipop.pos.entity.BrandEntity;
import com.lolipop.pos.entity.ProductEntity;
import com.lolipop.pos.entity.SupplierEntity;
import com.lolipop.pos.repository.BrandRepository;
import com.lolipop.pos.repository.ProductRepository;
import com.lolipop.pos.repository.SupplierRepository;
import com.lolipop.pos.service.CommonService;
import com.lolipop.pos.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
    private final BrandRepository brandRepository;
    private final SupplierRepository supplierRepository;

    @Override
    @Transactional
    public ProductEntity addProduct(ProductDto product) {
        Optional<ProductEntity> productEntityOptional = productRepository.findByName(product.getName());

        if (productEntityOptional.isEmpty()) {
            throw new EntityAlreadyExistException("Product", product.getName());
        }

        ProductEntity productEntity = new ProductEntity(product);

        if (product.getBrandId() == null) {
            throw new ResourceNotFoundException("Brand not found on product");
        }

        if (product.getSupplierId() == null) {
            throw new ResourceNotFoundException("Supplier not found on product");
        }

        Optional<BrandEntity> brandEntityOptional = brandRepository.findById(product.getBrandId());

        if (brandEntityOptional.isEmpty()) {
            throw new ResourceNotFoundException("Brand not found on product");
        }

        Optional<SupplierEntity> supplierEntityOptional = supplierRepository.findById(product.getSupplierId());

        if (supplierEntityOptional.isEmpty()) {
            throw new ResourceNotFoundException("Supplier not found on product");
        }

        productEntity.setBrand(brandEntityOptional.get());
        productEntity.setSupplier(supplierEntityOptional.get());

        return productRepository.save(productEntity);
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
            if (updatedProductData.getBrandId() != null) {
                Long brandId = updatedProductData.getBrandId();
                Optional<BrandEntity> brandEntityOptional = brandRepository.findById(brandId);

                if (brandEntityOptional.isEmpty()) {
                    throw new ResourceNotFoundException("Brand not found with id: " + brandId);
                }

                existingProduct.setBrand(brandEntityOptional.get());
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
            if (updatedProductData.getSupplierId() != null) {
                Optional<SupplierEntity> supplierEntityOptional = supplierRepository.findById(updatedProductData.getSupplierId());

                if (supplierEntityOptional.isEmpty()) {
                    throw new ResourceNotFoundException("Supplier not found on with Id: " + updatedProductData.getSupplierId());
                }
                existingProduct.setSupplier(supplierEntityOptional.get());
            }

            return productRepository.save(existingProduct);
        });
    }


    @Override
    public ProductDto getProductById(Long productId) {
        return productRepository.findById(productId).map(ProductDto::new).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
    }

    @Override
    public ProductDto getProductByName(String productName) throws Exception {

        return productRepository.findByName(productName).map(ProductDto::new)
                .orElseThrow(() -> new  Exception(commonService.generateMessage("Product", productName)));
    }


    @Override
    public ProductDto getProductBySku(String sku) throws Exception {
        return productRepository.findBySku(sku).map(ProductDto::new)
                .orElseThrow(() -> new  Exception(commonService.generateMessage("Product", sku)));
    }

    @Override
    public ProductDto getProductByBarcode(String barcode) throws Exception{
        return productRepository.findByBarcode(barcode).map(ProductDto::new)
                .orElseThrow(() -> new  Exception(commonService.generateMessage("Product", barcode)));
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

    @Override
    public String deleteProductById(Long id) {
        ProductEntity productEntity = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
        productEntity.setDeletedAt(LocalDateTime.now());
        productRepository.save(productEntity);

        return "Successfully deleted product with id: " + id;
    }
}

