package com.smartbilling.service;

import com.smartbilling.common.AuditableAction;
import com.smartbilling.domain.Category;
import com.smartbilling.domain.Product;
import com.smartbilling.domain.Unit;
import com.smartbilling.domain.User;
import com.smartbilling.dto.ProductDtos;
import com.smartbilling.repository.CategoryRepository;
import com.smartbilling.repository.ProductRepository;
import com.smartbilling.repository.UnitRepository;
import com.smartbilling.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UnitRepository unitRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    @AuditableAction("PRODUCT_CREATE")
    public ProductDtos.ProductResponse createProduct(ProductDtos.CreateProductRequest request) {
        if (request.sellingPrice() < request.purchasePrice()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Selling price must be greater than or equal to purchase price");
        }

        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid categoryId"));
        Unit unit = unitRepository.findById(request.unitId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid unitId"));
        User retailer = userRepository.findById(request.retailerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid retailerId"));

        String skuCode = request.skuCode().trim();
        if (productRepository.existsBySkuIgnoreCase(skuCode)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "SKU code already exists");
        }

        Product product = new Product();
        product.setName(request.name().trim());
        product.setCategory(category);
        product.setUnit(unit);
        product.setPurchasePrice(request.purchasePrice());
        product.setSellingPrice(request.sellingPrice());
        product.setGstPercent(request.gstPercent());
        product.setSku(skuCode);
        // Backward-compatible default until barcode is explicitly added in create API.
        product.setBarcode(skuCode);
        product.setRetailer(retailer);

        // Keep existing invoice calculations working.
        product.setPrice(request.sellingPrice());
        product.setTaxRate(request.gstPercent());

        Product saved = productRepository.save(product);
        return new ProductDtos.ProductResponse(
                saved.getId(),
                saved.getName(),
                saved.getCategory().getId(),
                saved.getUnit().getId(),
                saved.getPurchasePrice(),
                saved.getSellingPrice(),
                saved.getGstPercent(),
                saved.getSku(),
                saved.getRetailer().getId(),
                saved.getCreatedAt().atZone(ZoneOffset.UTC).toLocalDate()
        );
    }
}
