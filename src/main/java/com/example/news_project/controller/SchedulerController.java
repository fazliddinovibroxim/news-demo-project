package com.example.news_project.controller;

import com.example.news_project.service.SchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/scheduler")
@RequiredArgsConstructor
public class SchedulerController {

    private final SchedulerService schedulerService;

    @PostMapping("/check-news-status")
    public ResponseEntity<String> checkNewsStatus() {
        schedulerService.checkPublishUnpublish();
        return ResponseEntity.ok("News status check executed successfully.");
    }
}
