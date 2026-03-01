package com.smartbilling.service;

import com.smartbilling.dto.ProductDtos;

public interface ProductService {
    ProductDtos.ProductResponse createProduct(ProductDtos.CreateProductRequest request);
}
