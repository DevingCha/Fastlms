package com.zerobase.fastlms.admin.controller;

import com.zerobase.fastlms.admin.dto.*;
import com.zerobase.fastlms.admin.service.BannerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AdminBannerController {
    private final BannerService bannerService;

    @GetMapping("/admin/banner/list.do")
    public String getAllBanner(HttpServletRequest req, Model model) {
        String pageVal = req.getParameter("page");
        int page = 0;

        try {
            if (pageVal != null) {
                page = Integer.parseInt(pageVal);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        int previous = page-1;
        int next = page+1;

        model.addAttribute("previous", previous);
        model.addAttribute("current", page);
        model.addAttribute("next", next);

        int maxCount = bannerService.getMaxPage();
        model.addAttribute("maxPage", maxCount / 5);
        model.addAttribute("totalCount", maxCount);

        List<ListBannerDto> bannerList = bannerService.getAllBannerByPage(page);
        model.addAttribute("bannerList", bannerList);

        return "admin/banner/list";
    }

    @GetMapping("/admin/banner/edit.do")
    public String updateBannerPage(HttpServletRequest req, Model model) {
        String id = req.getParameter("id");
        DetailBannerDto detail = bannerService.getBannerById(id);

        model.addAttribute("detail", detail);
        return "admin/banner/banner-detail";
    }

    @PostMapping("/admin/banner/edit.do")
    public String updateBanner(Model model, MultipartFile imgFile,
                                   UpdateBannerDto updateBannerDto) {

        Long id = updateBannerDto.getId();
        bannerService.updateBanner(updateBannerDto, imgFile);
        return "redirect:/admin/banner/edit.do?id=" + id;
    }

    @GetMapping("/admin/banner/add.do")
    public String addBannerPage(Model model) {
        AddBannerDto addBannerDto = new AddBannerDto();
        model.addAttribute("detail", addBannerDto);
        return "admin/banner/banner-detail";
    }

    @PostMapping("/admin/banner/add.do")
    public String addBanner(AddBannerDto addBannerDto, MultipartFile imgFile, Model model) {
        log.warn(imgFile.getName());
        boolean result = bannerService.addBanner(addBannerDto, imgFile);
        model.addAttribute("result", result);
        return "redirect:/admin/banner/list.do";
    }

    @PostMapping("/admin/banner/delete.do")
    public String del(Model model, HttpServletRequest request
            , DeleteBannerDto deleteBannerDto) {

        System.out.println(deleteBannerDto.getDeleteBannerIds());
        boolean result = bannerService.del(deleteBannerDto.getDeleteBannerIds());

        return "redirect:/admin/banner/list.do";
    }
}
