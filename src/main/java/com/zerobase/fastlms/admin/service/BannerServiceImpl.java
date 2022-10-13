package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.dto.AddBannerDto;
import com.zerobase.fastlms.admin.dto.ListBannerDto;
import com.zerobase.fastlms.admin.entity.Banner;
import com.zerobase.fastlms.admin.entity.BannerOpenType;
import com.zerobase.fastlms.admin.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class BannerServiceImpl implements BannerService {
    private final BannerRepository bannerRepository;

    @Override
    public boolean saveBanner(AddBannerDto addBannerDto, MultipartFile imgFile) {
        String imgUrl = saveImg(imgFile);
        System.out.println(addBannerDto.isRelease());
        Banner banner = Banner.builder()
                .name(addBannerDto.getName())
                .imgUrl(imgUrl)
                .linkUrl(addBannerDto.getLinkUrl())
                .bannerOpenType(BannerOpenType.valueOf(addBannerDto.getBannerOpenType()))
                .orderBy(addBannerDto.getOrderBy())
                .release(addBannerDto.isRelease()).build();

        bannerRepository.save(banner);
        log.info("Save Complete --- Good");

        return true;
    }

    public String saveImg(MultipartFile imgFile) {
        String extension = "";
        int index = imgFile.getOriginalFilename().lastIndexOf(".");
        if (index > -1) {
            extension = imgFile.getOriginalFilename().substring(index+1);
        }
        String filename = UUID.randomUUID().toString() + "." + extension;
        String url = "/Users/cdj97/Desktop/vscode/Fastlms/src/main/java/com/zerobase/fastlms/images/banner/" + filename;

        try {
            File file = new File(url.toString());
            FileCopyUtils.copy(imgFile.getInputStream(), new FileOutputStream(file));
        } catch (Exception e) {
            log.error("File Save Error");
            log.error(e.getMessage());
        }

        return url;
    }

    @Override
    public List<ListBannerDto> getAllBanner() {
        List<ListBannerDto> bannerList = new ArrayList<>();

        bannerRepository.findAll().forEach(e -> {
            bannerList.add(
                    ListBannerDto.builder()
                            .id(Long.toString(e.getId()))
                            .name(e.getName())
                            .imgUrl(e.getImgUrl())
                            .createdAt(e.getCreatedAt())
                            .build()
            );
        });

        return bannerList;
    }


}
