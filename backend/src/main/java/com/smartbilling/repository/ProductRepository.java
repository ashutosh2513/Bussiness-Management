package com.smartbilling.repository;
import com.smartbilling.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
public interface ProductRepository extends JpaRepository<Product, UUID> {}
