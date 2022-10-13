package com.zerobase.fastlms.admin.controller;

import com.zerobase.fastlms.admin.dto.AddBannerDto;
import com.zerobase.fastlms.admin.dto.ListBannerDto;
import com.zerobase.fastlms.admin.entity.Banner;
import com.zerobase.fastlms.admin.entity.BannerOpenType;
import com.zerobase.fastlms.admin.service.BannerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminBannerController {
    private final BannerService bannerService;

    @GetMapping("/admin/banner/list.do")
    public String getAllBanner(Model model) {
        List<ListBannerDto> bannerList = bannerService.getAllBanner();
        model.addAttribute("bannerList", bannerList);

        return "admin/banner/list";
    }

    @GetMapping("/admin/banner/add.do")
    public String addBannerPage(Model model) {
        AddBannerDto addBannerDto = new AddBannerDto();
        model.addAttribute("detail", addBannerDto);
        return "admin/banner/add";
    }

    @PostMapping("/admin/banner/add.do")
    public String addBanner(AddBannerDto addBannerDto, MultipartFile imgFile, Model model) {
        log.warn(imgFile.getName());
        boolean result = bannerService.saveBanner(addBannerDto, imgFile);
        model.addAttribute("result", result);
        return "redirect:/admin/banner/list.do";
    }




}
