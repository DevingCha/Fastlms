package com.zerobase.fastlms.admin.service;

import com.zerobase.fastlms.admin.banner.exception.BannerNotFoundException;
import com.zerobase.fastlms.admin.dto.AddBannerDto;
import com.zerobase.fastlms.admin.dto.DetailBannerDto;
import com.zerobase.fastlms.admin.dto.ListBannerDto;
import com.zerobase.fastlms.admin.dto.UpdateBannerDto;
import com.zerobase.fastlms.admin.entity.Banner;
import com.zerobase.fastlms.admin.entity.BannerOpenType;
import com.zerobase.fastlms.admin.repository.BannerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@RequiredArgsConstructor
@Service
public class BannerServiceImpl implements BannerService {
    private final BannerRepository bannerRepository;

    @Override
    public boolean addBanner(AddBannerDto addBannerDto, MultipartFile imgFile) {
        String imgUrl = saveImg(imgFile);
        Banner banner = Banner.builder()
                .name(addBannerDto.getName())
                .imgUrl(imgUrl)
                .linkUrl(addBannerDto.getLinkUrl())
                .bannerOpenType(BannerOpenType.valueOf(addBannerDto.getBannerOpenType()))
                .orderVal(addBannerDto.getOrderVal())
                .visible(addBannerDto.isVisible()).build();

        bannerRepository.save(banner);
        return true;
    }

    public String saveImg(MultipartFile imgFile) {
        String extension = "";
        int index = imgFile.getOriginalFilename().lastIndexOf(".");
        if (index > -1) {
            extension = imgFile.getOriginalFilename().substring(index+1);
        }
        String filename = UUID.randomUUID().toString() + "." + extension;
        String url = "./images/banner/" + filename;

        try {
            File dir = new File("./images/banner/");
            if (!dir.isDirectory()) {
                dir.mkdirs();
            }

            File file = new File(url.toString());
            FileCopyUtils.copy(imgFile.getInputStream(), new FileOutputStream(file));
        } catch (Exception e) {
            log.error("File Save Error");
            log.error(e.getMessage());
        }

        return filename;
    }

    @Override
    public List<ListBannerDto> getAllBannerByPage(int page) {
        List<ListBannerDto> bannerList = new ArrayList<>();

        AtomicInteger sequence = new AtomicInteger(5*page + 1);
        Pageable sortAndLimit = PageRequest.of(page, 5, Sort.by("OrderVal"));
        bannerRepository.findAll(sortAndLimit).forEach(e -> {
            bannerList.add(
                    ListBannerDto.builder()
                            .id(Long.toString(e.getId()))
                            .name(e.getName())
                            .imgUrl(e.getImgUrl())
                            .seq(sequence.getAndIncrement())
                            .createdAt(e.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                            .linkUrl(e.getLinkUrl())
                            .bannerOpenType(e.getBannerOpenType().equals(BannerOpenType.BLANK) ? "_blank" : "_self")
                            .build()
            );
        });
        return bannerList;
    }

    @Override
    public int getMaxPage() {
        return bannerRepository.findAll().size();
    }

    @Override
    public List<ListBannerDto> getAllBannerVisible() {
        List<ListBannerDto> bannerList = new ArrayList<>();

        AtomicInteger sequence = new AtomicInteger(1);

        bannerRepository.findAll(Sort.by(Sort.Direction.ASC, "orderVal")).forEach(e -> {
            if (e.isVisible()) {
                bannerList.add(
                        ListBannerDto.builder()
                                .id(Long.toString(e.getId()))
                                .name(e.getName())
                                .imgUrl(e.getImgUrl())
                                .seq(sequence.getAndIncrement())
                                .createdAt(e.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                                .linkUrl(e.getLinkUrl())
                                .bannerOpenType(e.getBannerOpenType().equals(BannerOpenType.BLANK) ? "_blank" : "_self")
                                .build()
                );
            }
        });

        return bannerList;
    }

    @Override
    public boolean updateBanner(UpdateBannerDto updateBannerDto, MultipartFile imgFile) {
        Optional<Banner> findBanner = bannerRepository.findById(updateBannerDto.getId());

        if (!findBanner.isPresent()) {
            throw new BannerNotFoundException("해당 배너가 존재하지 않습니다.");
        }

        Banner banner = findBanner.get();
        banner.update(updateBannerDto);
        if (!imgFile.isEmpty()) {
            String imgUrl = saveImg(imgFile);
            try {
                String url = "./images/banner/" + banner.getImgUrl();
                Files.delete(Paths.get(url));
            } catch (IOException e) {
                log.error("File Delete Fail");
            }
            banner.setImgUrl(imgUrl);
        }

        bannerRepository.save(banner);
        return true;
    }

    @Override
    public DetailBannerDto getBannerById(String id) {
        if (id == null) {
            throw new BannerNotFoundException("다시 시도해주세요.");
        }
        Optional<Banner> findBanner = bannerRepository.findById(Long.parseLong(id));

        if (!findBanner.isPresent()) {
            throw new BannerNotFoundException("해당 배너가 존재하지 않습니다.");
        }

        Banner banner = findBanner.get();

        return DetailBannerDto.builder()
                .name(banner.getName())
                .bannerOpenType(banner.getBannerOpenType().toString())
                .visible(banner.isVisible())
                .orderVal(banner.getOrderVal())
                .imgUrl(banner.getImgUrl())
                .linkUrl(banner.getLinkUrl())
                .editMode(true)
                .build();
    }

    @Override
    public boolean del(String deleteBannerIds) {
        String[] idList = deleteBannerIds.split(",");

        for (String id : idList) {
            try {
                Optional<Banner> findBanner = bannerRepository.findById(Long.parseLong(id));
                if (!findBanner.isPresent()) {
                    throw new BannerNotFoundException("해당 배너가 존재하지 않습니다.");
                }
                Banner banner = findBanner.get();
                String url = "./images/banner/" + banner.getImgUrl();
                bannerRepository.delete(banner);
                Files.delete(Paths.get(url));
            } catch (Exception e) {
                log.warn("File Delete Failed: " + id);
                continue;
            }
        }

        return true;
    }


}
