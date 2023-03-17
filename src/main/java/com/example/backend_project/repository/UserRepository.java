package com.example.backend_project.repository;


import com.example.backend_project.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    
    Boolean existsByUsername(String username);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE User u SET u.password = :password WHERE u.username = :username")
    void updatePassword(@Param("password") String password, @Param("username") String username);

    
    
}
