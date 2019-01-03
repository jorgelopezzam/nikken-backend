package com.nikken.sendnotifications.repository;

import com.nikken.sendnotifications.model.SocialNetwork;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialNetworkRepository extends JpaRepository<SocialNetwork,Integer> {
    SocialNetwork findByPaisPublic(String paisPublic);
}
