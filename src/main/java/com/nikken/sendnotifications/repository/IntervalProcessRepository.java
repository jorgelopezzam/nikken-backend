package com.nikken.sendnotifications.repository;

import com.nikken.sendnotifications.model.IntervalProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface IntervalProcessRepository extends JpaRepository<IntervalProcess,Integer> {
    @Query("from IntervalProcess where adminProcess.id=?1")
    IntervalProcess findIntervalProcessByAdminProcessId(Integer id);
}
