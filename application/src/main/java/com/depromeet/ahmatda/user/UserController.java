package com.depromeet.ahmatda.user;

import com.depromeet.ahmatda.HttpHeader;
import com.depromeet.ahmatda.common.response.RestResponse;
import com.depromeet.ahmatda.user.dto.SignUpRequest;
import com.depromeet.ahmatda.user.service.UserService;
import com.depromeet.ahmatda.user.token.FcmToken;
import com.depromeet.ahmatda.user.token.UserToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<RestResponse<UserRegisterCode>> checkUser(@RequestParam UserToken userToken) {
        return ResponseEntity.ok().body(
                RestResponse.ok(userService.checkUser(userToken))
        );
    }

    @PostMapping
    public ResponseEntity<RestResponse<UserToken>> signUp(@Valid @RequestBody SignUpRequest request) {
        UserToken token = userService.createUser(request);

        return ResponseEntity.ok().body(
                RestResponse.ok(token)
        );
    }

    @PostMapping("/token")
    public ResponseEntity<RestResponse<Object>> updateFcmToken(@Valid @RequestBody FcmToken fcmToken, HttpServletRequest request) {
        final String userToken = request.getHeader(HttpHeader.USER_TOKEN);
        userService.updateFcmToken(userToken, fcmToken);
        return ResponseEntity.ok().body(RestResponse.ok());
    }
}
