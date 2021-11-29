package com.example.cms.repository;

import com.example.cms.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByName(Enum name);
}
