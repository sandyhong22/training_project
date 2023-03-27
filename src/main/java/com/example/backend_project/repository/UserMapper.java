package com.example.backend_project.repository;

import com.example.backend_project.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    
    @Select("SELECT * FROM user u WHERE u.username=#{username}")
    User findByUsername(@Param("username") String username);
}
