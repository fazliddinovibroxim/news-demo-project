package com.example.news_project.repository;

import com.example.news_project.entity.ads.AdsAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdsRepository extends JpaRepository<AdsAssignment, UUID> {

}
