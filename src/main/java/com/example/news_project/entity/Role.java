package com.example.news_project.entity;

import com.example.news_project.enums.AppPermissions;
import com.example.news_project.enums.AppRoleName;
import jakarta.persistence.*;
import lombok.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.sql.Timestamp;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "role", schema = "auth")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private AppRoleName appRoleName;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(
            name = "role_app_permissions",
            joinColumns = @JoinColumn(name = "role_id")
    )

    @Column(name = "permission")
    private Set<AppPermissions> appPermissions;

    @ManyToOne(fetch = FetchType.LAZY)
    @CreatedBy
    @JoinColumn(name = "created_by_id", referencedColumnName = "id")
    private AuthUser createdBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @LastModifiedBy
    private AuthUser updatedBy;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private Timestamp updatedAt;

    public Set<AppPermissions> getAppPermissions() {
        return appPermissions;
    }
}
