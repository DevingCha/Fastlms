package com.zerobase.fastlms.member.dto;

import com.zerobase.fastlms.util.RequestUtils;
import lombok.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class LoginHistoryDto {
    public String userId;
    public LocalDateTime loginDt;
    public String userAgent;
    public String accessIp;
}
