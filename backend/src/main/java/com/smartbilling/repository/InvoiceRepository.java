package com.smartbilling.repository;
import com.smartbilling.domain.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;import java.util.UUID;
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> { Optional<Invoice> findTopByOrderByCreatedAtDesc(); }
