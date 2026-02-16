package com.example.Ecomerce.users.infraestructure.api;

import com.example.Ecomerce.common.application.mediator.Mediator;
import com.example.Ecomerce.users.application.command.login.LoginUserRequest;
import com.example.Ecomerce.users.application.command.login.LoginUserResponse;
import com.example.Ecomerce.users.application.command.register.RegisterUserRequest;
import com.example.Ecomerce.users.application.command.register.RegisterUserResponse;
import com.example.Ecomerce.users.infraestructure.api.dto.LoginRequestDto;
import com.example.Ecomerce.users.infraestructure.api.dto.RegisterRequestDto;
import com.example.Ecomerce.users.infraestructure.api.dto.TokenResponseDto;
import com.example.Ecomerce.users.infraestructure.api.mapper.MapperUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class userController {
    private final Mediator mediator;
    private final MapperUser usermapper;

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {


        LoginUserRequest request = usermapper.mapToLoginUserRequest(loginRequestDto);

        LoginUserResponse response = mediator.dispatch(request);

        TokenResponseDto tokenResponseDto = usermapper.mapToTokenResponse(response);
        return ResponseEntity.ok(tokenResponseDto);

    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponseDto> regsiter(@RequestBody RegisterRequestDto registerDTO) {


        RegisterUserRequest request = usermapper.mapToRegsiterUserRequest(registerDTO);

        RegisterUserResponse response = mediator.dispatch(request);

        TokenResponseDto tokenResponseDto = usermapper.mapToTokenResponse(response);
        return ResponseEntity.ok(tokenResponseDto);
    }
}
