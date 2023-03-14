package com.example.backend_project.repository;


import com.example.backend_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    Boolean existsByUsername(String username);
}
