package com.smartbilling.repository;
import com.smartbilling.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
public interface PaymentRepository extends JpaRepository<Payment, UUID> {}
