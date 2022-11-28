package com.depromeet.ahmatda.user;

import com.depromeet.ahmatda.common.response.RestResponse;
import com.depromeet.ahmatda.user.dto.SignUpRequestDto;
import com.depromeet.ahmatda.user.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<RestResponse<Object>> createUser(@RequestBody @Validated SignUpRequestDto request) {
        userService.createUser(request);
        return ResponseEntity.ok().body(RestResponse.ok());
    }
}
