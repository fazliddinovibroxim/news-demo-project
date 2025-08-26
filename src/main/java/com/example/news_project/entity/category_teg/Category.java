package com.example.news_project.entity.category_teg;

import com.example.news_project.entity.AuthUser;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "category", schema = "category_scheme")
public class Category {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private String name;

    /** columnga nom bermasa ham variablning nomini oladi,
     * lekin aniqlashtirish uchun name berilgan **/

    @Column(name = "parent_id", nullable = false)
    private UUID parentId;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @CreatedBy
    @JoinColumn(name = "created_by_id", referencedColumnName = "id")
    private AuthUser createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @LastModifiedBy
    @JoinColumn(name = "updated_by_id", referencedColumnName = "id")
    private AuthUser updatedBy;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Timestamp updatedAt;
}
