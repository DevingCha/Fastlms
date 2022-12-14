package com.zerobase.fastlms.admin.dto;


import com.zerobase.fastlms.admin.entity.BannerOpenType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListBannerDto {
    String id;
    String name;
    String imgUrl;
    String linkUrl;
    String bannerOpenType;
    int seq;
    String createdAt;
}
