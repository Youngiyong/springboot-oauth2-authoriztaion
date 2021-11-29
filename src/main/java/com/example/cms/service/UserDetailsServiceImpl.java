package com.example.cms.service;


import com.example.cms.constants.CustomExceptionCode;
import com.example.cms.dto.BaseResponse;
import com.example.cms.dto.request.SignupRequest;
import com.example.cms.entity.RoleEntity;
import com.example.cms.entity.UserEntity;
import com.example.cms.exception.CustomException;
import com.example.cms.model.ERole;
import com.example.cms.repository.UserRepository;
import com.example.cms.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository roleRepository;

    @Transactional
    @Override
    public UserDetailsImpl loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " can not be found");
        }
        return UserDetailsImpl.build(user);
    }

    @Transactional
    public BaseResponse save(SignupRequest payload) throws UsernameNotFoundException {
        if (userRepository.existsByEmail(payload.getEmail()))
            throw new CustomException(CustomExceptionCode.EMAIL_IS_EXIST);

        if (userRepository.existsByUsername(payload.getUsername()))
            throw new CustomException(CustomExceptionCode.USERNAME_IS_EXIST);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        UserEntity user = UserEntity.builder()
                .email(payload.getEmail())
                .username(payload.getUsername())
                .password(encoder.encode(payload.getPassword()))
                .build();

        userRepository.save(user);

        Set<String> strRoles = payload.getRoles();
        Set<RoleEntity> roles = new HashSet<>();
        if (strRoles == null){
            RoleEntity userRole = roleRepository.findByName(ERole.ROLE_ADMIN).get();

            if(userRole==null)
                throw new CustomException(CustomExceptionCode.REQUEST_ROLE_NOT_FOUND);

            roles.add(userRole);

        } else {
            for (String role: strRoles){
                switch (role) {
                    case "ADMIN":
                        RoleEntity adminRole = roleRepository.findByName(ERole.ROLE_ADMIN).get();

                        if(adminRole==null){
                            throw new CustomException(CustomExceptionCode.REQUEST_ROLE_NOT_FOUND);
                        }
                        roles.add(adminRole);

                        break;
                    case "MANAGER":
                        RoleEntity managerRole = roleRepository.findByName(ERole.ROLE_MANAGER).get();

                        if(managerRole==null){
                            throw new CustomException(CustomExceptionCode.REQUEST_ROLE_NOT_FOUND);
                        }
                        roles.add(managerRole);

                        break;
                    default:
                        RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER).get();

                        if(userRole==null){
                            throw new CustomException(CustomExceptionCode.REQUEST_ROLE_NOT_FOUND);
                        }

                        roles.add(userRole);

                }
            }
        }

        user.setRoles(roles);
        userRepository.save(user);

        return new BaseResponse(true, "reg ok");

    }
}
