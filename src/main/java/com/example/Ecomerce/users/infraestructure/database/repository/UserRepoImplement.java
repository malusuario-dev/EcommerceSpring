package com.example.Ecomerce.users.infraestructure.database.repository;

import com.example.Ecomerce.users.dominio.User;
import com.example.Ecomerce.users.dominio.port.UserRepository;
import com.example.Ecomerce.users.infraestructure.database.entity.QueryUserRepo;
import com.example.Ecomerce.users.infraestructure.database.entity.UserEntity;
import com.example.Ecomerce.users.infraestructure.database.mapper.UserEntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
@RequiredArgsConstructor
public class UserRepoImplement implements UserRepository {
    private  final QueryUserRepo queryUserRepo;
    private  final UserEntityMapper userEntityMapper;


    @Override
    public Optional<User> findByEmail(String email) {
        return queryUserRepo.findByemail(email).map(userEntityMapper::mapToUser);
    }

    @Override
    public boolean existByEmail(String email) {
        return queryUserRepo.findByemail(email).isPresent();
    }

    @Override
    public User upsert(User user) {
        UserEntity userEntity = userEntityMapper.mapToEntity(user);
        UserEntity save = queryUserRepo.save(userEntity);
        return userEntityMapper.mapToUser(save);
    }
}
