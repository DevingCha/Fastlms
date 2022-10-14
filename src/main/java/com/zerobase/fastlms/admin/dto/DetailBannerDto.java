package com.zerobase.fastlms.admin.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetailBannerDto {
    String name;
    String imgUrl;
    String linkUrl;
    String bannerOpenType;
    int orderVal;
    boolean visible;
    boolean editMode;
}
