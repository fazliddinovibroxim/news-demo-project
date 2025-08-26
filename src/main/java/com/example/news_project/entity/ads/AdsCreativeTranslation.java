package com.example.news_project.entity.ads;

import com.example.news_project.entity.AuthUser;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(
        schema = "ads",
    name = "ads_creative_translation",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"creative_id", "lang"})
    }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdsCreativeTranslation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /** columnga nom bermasa ham variablning nomini oladi,
     * lekin aniqlashtirish uchun name berilgan **/

     @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_entity_id")
    private AdsCreative parentEntity;


    @Column(name = "lang", nullable = false, length = 10)
    private String lang;

    @Column(name = "title", length = 255)
    private String title;

    @Column(name = "alt_text", length = 255)
    private String altText;

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
