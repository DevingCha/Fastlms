package com.zerobase.fastlms.admin.entity;

import com.zerobase.fastlms.admin.dto.UpdateBannerDto;
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

    int orderVal;

    boolean visible;

    @CreatedDate
    LocalDateTime createdAt;

    @LastModifiedDate
    LocalDateTime updatedAt;

    public void update(UpdateBannerDto updateBannerDto) {
        name = updateBannerDto.getName();
        linkUrl = updateBannerDto.getLinkUrl();
        bannerOpenType = BannerOpenType.valueOf(updateBannerDto.getBannerOpenType());
        orderVal = updateBannerDto.getOrderVal();
        visible = updateBannerDto.isVisible();
    }
}
