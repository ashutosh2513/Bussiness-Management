package com.smartbilling.repository;
import com.smartbilling.domain.InventoryMovement;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
public interface InventoryMovementRepository extends JpaRepository<InventoryMovement, UUID> {}
