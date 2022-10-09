package com.zerobase.fastlms.configuration;

import com.zerobase.fastlms.member.dto.LoginHistoryDto;
import com.zerobase.fastlms.member.repository.MemberRepository;
import com.zerobase.fastlms.member.service.MemberService;
import com.zerobase.fastlms.util.RequestUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;


@Configuration
@RequiredArgsConstructor
public class UserAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication authentication) throws IOException, ServletException {
        String userAgent = RequestUtils.getUserAgent(req);
        String accessIp = RequestUtils.getClientIP(req);
        String userId = authentication.getName();

        memberRepository.findById(userId).ifPresent(e -> {
            e.setLastLoginDt(LocalDateTime.now());
            memberRepository.save(e);
        });

        LoginHistoryDto loginHistoryDto = LoginHistoryDto
                .builder()
                .userId(userId)
                .loginDt(LocalDateTime.now())
                .userAgent(userAgent)
                .accessIp(accessIp)
                .build();

        memberService.loginHistory(loginHistoryDto);
        res.sendRedirect("/");
    }
}
