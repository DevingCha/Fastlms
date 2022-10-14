package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.AddBannerDto;
import com.zerobase.fastlms.admin.dto.DetailBannerDto;
import com.zerobase.fastlms.admin.dto.ListBannerDto;
import com.zerobase.fastlms.admin.dto.UpdateBannerDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BannerService {
    boolean addBanner(AddBannerDto addBannerDto, MultipartFile imgFile);

    List<ListBannerDto> getAllBannerByPage(int page);

    int getMaxPage();

    List<ListBannerDto> getAllBannerVisible();

    boolean updateBanner(UpdateBannerDto updateBannerDto, MultipartFile imgFile);

    DetailBannerDto getBannerById(String id);

    boolean del(String deleteBannerIds);
}
