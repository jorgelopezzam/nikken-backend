package com.nikken.sendnotifications.repository;

import com.nikken.sendnotifications.model.AdminProcess;
import io.swagger.models.auth.In;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

public interface AdminProcessRepository extends JpaRepository<AdminProcess,Integer> {

    @Query("from AdminProcess where idAdmin=:id")
    Optional<AdminProcess> findAdminProcessById(@Param("id") Integer id);
}
