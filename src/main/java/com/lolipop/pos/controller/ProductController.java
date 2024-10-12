package com.lolipop.pos.controller;

import com.lolipop.pos.configuration.ResourceNotFoundException;
import com.lolipop.pos.dto.GenericConverter;
import com.lolipop.pos.dto.ProductDto;
import com.lolipop.pos.entity.ProductEntity;
import com.lolipop.pos.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Validated
public class ProductController {
    private final ProductService productService;

    @PostMapping("/add")
    public ProductEntity add(@Valid @RequestBody ProductDto product) {

        return productService.addProduct(product);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductEntity> updateProduct(@PathVariable Long id, @Validated @RequestBody ProductDto updatedProduct) {

        ProductEntity updatedEntity = productService.updateProduct(id, updatedProduct)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));

        return ResponseEntity.ok(updatedEntity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {

        ProductDto product = productService.getProductById(id);

        return ResponseEntity.ok(product);
    }

    @PostMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable Long id) {

        String response = productService.deleteProductById(id);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public Page<ProductDto> getProductList(@RequestParam(required = false) String from,
                                           @RequestParam(required = false) String to,
                                           @RequestParam(required = false) String name,
                                           @RequestParam(required = false, defaultValue = "0") int pageNo,
                                           @RequestParam(required = false, defaultValue = "25") int pageSize,
                                           @RequestParam(required = false, defaultValue = "createdAt") String sortField,
                                           @RequestParam(required = false, defaultValue = "desc") String sortDir) throws Exception {

        Page<ProductEntity> productPage = productService.getProductListPageable(from, to, name, pageNo, pageSize, sortField, sortDir);

        return GenericConverter.convertToDto(productPage, ProductDto::new);

    }
}