package com.smartbilling.repository;
import com.smartbilling.domain.BusinessSettings;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
public interface BusinessSettingsRepository extends JpaRepository<BusinessSettings, UUID> {}
