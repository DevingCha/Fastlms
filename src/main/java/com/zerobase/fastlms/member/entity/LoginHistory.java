package com.zerobase.fastlms.member.entity;

import com.zerobase.fastlms.member.dto.LoginHistoryDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class LoginHistory {
    @Id
    @GeneratedValue
    public long id;

    public String userId;
    public LocalDateTime loginDt;
    public String userAgent;
    public String accessIp;

    public static LoginHistory of(LoginHistoryDto loginHistoryDto) {
        return LoginHistory.builder()
                .userId(loginHistoryDto.getUserId())
                .loginDt(loginHistoryDto.getLoginDt())
                .userAgent(loginHistoryDto.getUserAgent())
                .accessIp(loginHistoryDto.getAccessIp())
                .build();
    }
}
