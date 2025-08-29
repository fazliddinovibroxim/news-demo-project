package com.example.news_project.repository;

import com.example.news_project.entity.ads.AdsAssignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface AdsRepository extends JpaRepository<AdsAssignment, UUID> {
}
