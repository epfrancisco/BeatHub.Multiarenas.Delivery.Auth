package com.beathub.multiarenas.delivery.auth.repository.log;

import com.beathub.multiarenas.delivery.auth.entity.log.ExceptionLogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExceptionLogRepository extends JpaRepository<ExceptionLogEntity, Long> {
}
