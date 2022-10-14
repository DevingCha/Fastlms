package com.zerobase.fastlms.main.controller;


import com.zerobase.fastlms.admin.dto.ListBannerDto;
import com.zerobase.fastlms.admin.service.BannerService;
import com.zerobase.fastlms.components.MailComponents;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final BannerService bannerService;
    
    @RequestMapping("/")
    public String index(Model model) {

        List<ListBannerDto> bannerList = bannerService.getAllBannerVisible();
        model.addAttribute("bannerList", bannerList);
        
        return "index";
    }
    
    
    
    @RequestMapping("/error/denied")
    public String errorDenied() {
        
        return "error/denied";
    }
    
    
    
}
