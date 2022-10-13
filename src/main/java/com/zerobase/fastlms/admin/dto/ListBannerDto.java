package com.zerobase.fastlms.admin.dto;


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
    LocalDateTime createdAt;
}
