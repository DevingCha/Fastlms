package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.AddBannerDto;
import com.zerobase.fastlms.admin.dto.ListBannerDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BannerService {
    boolean saveBanner(AddBannerDto addBannerDto, MultipartFile imgFile);

    List<ListBannerDto> getAllBanner();
}
