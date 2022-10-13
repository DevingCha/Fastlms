package com.zerobase.fastlms.admin.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Banner {
    @Id
    @GeneratedValue
    Long id;

    String name;

    String imgUrl;

    String linkUrl;

    @Enumerated(EnumType.STRING)
    BannerOpenType bannerOpenType;

    int orderBy;

    boolean release;

    @CreatedDate
    LocalDateTime createdAt;

    @LastModifiedDate
    LocalDateTime updatedAt;
}
