package com.zerobase.fastlms.member.repository;

import com.zerobase.fastlms.member.entity.LoginHistory;
import com.zerobase.fastlms.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, String> {
    Optional<List<LoginHistory>> findTop5ByUserIdOrderByLoginDtDesc(String userId);
}
