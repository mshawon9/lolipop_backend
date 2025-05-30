package com.lolipop.pos.service;

import com.lolipop.pos.dto.ProductDto;
import com.lolipop.pos.entity.ProductEntity;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ProductService {
    ProductEntity addProduct(ProductDto product);
    Optional<ProductEntity> updateProduct(Long id, ProductDto product);

    ProductDto getProductById(Long productId);

    ProductDto getProductByName(String productName) throws Exception;

    ProductDto getProductBySku(String sku) throws Exception;

    ProductDto getProductByBarcode(String barcode) throws Exception;

    Page<ProductEntity> getProductListPageable(String fromDate, String toDate, String name,
                                               int pageNo, int pageSize, String sortField, String sortDir) throws Exception;

    String deleteProductById(Long id);
}
