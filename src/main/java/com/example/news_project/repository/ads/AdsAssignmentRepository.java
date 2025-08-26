package com.example.news_project.repository.ads;

import com.example.news_project.entity.ads.AdsAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdsAssignmentRepository extends JpaRepository<AdsAssignment, UUID> {
}
