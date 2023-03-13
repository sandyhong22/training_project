package com.example.backend_project.controller.user.service;


import com.example.backend_project.entity.User;
import com.example.backend_project.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FindAllUserService {

   @Autowired
   UserRepository userRepository;

   public List<User> getAllAccount(){
      return userRepository.findAll();
   }

}
