package com.mazhar.chat_application.controller;

import com.mazhar.chat_application.dto.request.UserSummaryDto;
import com.mazhar.chat_application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserSummaryDto>> getAllContacts(@AuthenticationPrincipal UserDetails userDetails) {
        List<UserSummaryDto> users = userService.getAllUsersExceptCurrent(userDetails.getUsername());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/me")
    public ResponseEntity<UserSummaryDto> getMyProfile(@AuthenticationPrincipal UserDetails userDetails) {
        UserSummaryDto profile = userService.getCurrentUserProfile(userDetails.getUsername());
        return ResponseEntity.ok(profile);
    }
}