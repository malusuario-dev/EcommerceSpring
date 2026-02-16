package com.example.Ecomerce.users.infraestructure.api.mapper;

import com.example.Ecomerce.users.application.command.login.LoginUserRequest;
import com.example.Ecomerce.users.application.command.login.LoginUserResponse;
import com.example.Ecomerce.users.application.command.register.RegisterUserRequest;
import com.example.Ecomerce.users.application.command.register.RegisterUserResponse;
import com.example.Ecomerce.users.infraestructure.api.dto.LoginRequestDto;
import com.example.Ecomerce.users.infraestructure.api.dto.RegisterRequestDto;
import com.example.Ecomerce.users.infraestructure.api.dto.TokenResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)

public interface MapperUser {
    LoginUserRequest mapToLoginUserRequest(LoginRequestDto loginRequestDto);

    TokenResponseDto mapToTokenResponse(LoginUserResponse loginUserResponse);

    TokenResponseDto mapToTokenResponse(RegisterUserResponse registerUserResponse);

    RegisterUserRequest mapToRegsiterUserRequest(RegisterRequestDto registerRequestDto) ;
}
