package com.nikken.sendnotifications.repository;

import com.nikken.sendnotifications.model.SchedulerProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface SchedulerProcessRepository extends JpaRepository<SchedulerProcess,Integer> {

    @Query("select horario from SchedulerProcess where adminProcess.idAdmin=?1")
    List<Date> findSchedulerProcessByAdminProcessId(Integer adminProcessID);
}
