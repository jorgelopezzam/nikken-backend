package com.nikken.sendnotifications.repository;

import com.nikken.sendnotifications.model.NikkenGuide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface NikkenGuideRepository extends JpaRepository<NikkenGuide,Integer> {

    @Override
    List<NikkenGuide> findAll();

    List<NikkenGuide> findByDocNum(String docNum);

    @Query(value = "select DISTINCT(U_Estafeta) as U_Estafeta from nikken_data where DocNum = ?1",
            nativeQuery = true)
    List<String> findGuideNumber(String docNum);

    Optional<NikkenGuide> findByCardCode(String cardCode);
}
