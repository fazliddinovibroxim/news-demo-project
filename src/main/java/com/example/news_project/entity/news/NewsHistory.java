package com.example.news_project.entity.news;

import com.example.news_project.entity.AuthUser;
import com.example.news_project.enums.NewsStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.UUID;


@Entity
@Table(name = "news_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "news_id", nullable = false)
    private News news;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "changed_by", nullable = false)
    private AuthUser changedBy;

    @Column(name = "from_status", nullable = false, length = 50)
    private NewsStatus fromStatus;

    @Column(name = "to_status", nullable = false, length = 50)
    private NewsStatus toStatus;

    @Column(name = "diff_json", columnDefinition = "jsonb")
    private String diffJson;

    @Column(name = "changed_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Timestamp changedAt;
}
