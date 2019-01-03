package com.nikken.sendnotifications.repository;

import com.nikken.sendnotifications.model.NikkenGuideArchive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NikkenGuideSummaryArchiveRepository extends JpaRepository<NikkenGuideArchive,Integer> {

    Optional<NikkenGuideArchive> findByCardCodeAndUEstafeta(String cardCode,String uEstafeta);
}
