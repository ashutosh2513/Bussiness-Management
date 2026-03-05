package com.smartbilling.service;

import com.smartbilling.dto.ProductDtos;

import java.util.List;

public interface ProductService {
    ProductDtos.ProductResponse createProduct(ProductDtos.CreateProductRequest request);
    List<ProductDtos.ProductListItem> getProducts(ProductDtos.GetProductsRequest request);
}
