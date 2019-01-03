package com.nikken.sendnotifications.repository;

import com.nikken.sendnotifications.model.NikkenGuideArchive;
import com.nikken.sendnotifications.model.NikkenGuideSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NikkenGuideSummaryRepository extends JpaRepository<NikkenGuideSummary,Integer> {

    @Query(value = "select * from nikken_data_summary where sendProcess is null",nativeQuery = true)
    List<NikkenGuideSummary> findPendingRecords();

    Optional<NikkenGuideSummary> findByCardCodeAndUEstafeta(String cardCode, String uEstafeta);

}
