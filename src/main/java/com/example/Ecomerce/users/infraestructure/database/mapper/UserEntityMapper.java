package com.example.Ecomerce.users.infraestructure.database.mapper;

import com.example.Ecomerce.users.dominio.User;
import com.example.Ecomerce.users.infraestructure.database.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserEntityMapper {
    @Mapping(target = "authorities", ignore = true)
    UserEntity mapToEntity(User user);

    User mapToUser(UserEntity userEntity);}
