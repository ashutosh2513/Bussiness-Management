package com.smartbilling.web;

import com.smartbilling.dto.ProductDtos;
import com.smartbilling.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping("/add")
    public ProductDtos.ProductResponse createProduct(@Valid @RequestBody ProductDtos.CreateProductRequest request) {
        return productService.createProduct(request);
    }

    @PostMapping("/get")
    public List<ProductDtos.ProductListItem> getProducts(
            @RequestBody(required = false) ProductDtos.GetProductsRequest request) {
        return productService.getProducts(request);
    }
}
