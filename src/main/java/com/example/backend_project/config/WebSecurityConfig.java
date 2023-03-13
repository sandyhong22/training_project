//package com.example.backend_project.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private JwtAuthFilter jwtAuthFilter;
//
//    // 這個 filter 利用 http request header 帶的 JWT(Json Web Token) 進行用戶認証與授權
//    @Autowired private JwtAuthFilter jwtAuthFilter;
//
//    ...etc
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        // 因為這裡設定 sessionCreationPolicy(SessionCreationPolicy.STATELESS)，所以
//        // 不用 http session 記錄資料，因此 SecurityContextHolder 不會將登入認証成功後的 Authentication 記到
//        // http session。也就是說登入認証成功後的 Authentication 不會被記錄到系統，每次的 http request 都必需重新
//        // 進行一次登入認証，不然從 SecurityContextHolder.getContext().getAuthentication() 取回登入認証成功後
//        // 的 Authentication instance 一定是 null。
//        http.cors().and()
//                .csrf().disable()
//
//                // 存取到未授權的 restful api 時，會導到 UnauthEntryPoint class 進行後續處理
//                .exceptionHandling().authenticationEntryPoint(new UnauthEntryPoint()).and()
//
//                // 因為走 jwt(json web token) auth，所以不用 http session，要設成 SessionCreationPolicy.STATELESS
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//
//                .authorizeRequests().antMatchers("/auth/**").permitAll()
//                .anyRequest().authenticated();
//
//        // jwtAuthFilter 放在 UsernamePasswordAuthenticationFilter 之前，代表先用 JWT(Json Web Token)對用戶進行認証與授權
//        // 如果失敗的話會經由 UsernamePasswordAuthenticationFilter 使用用戶帳號密碼再次進行認証與授權
//        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//    }
//}
