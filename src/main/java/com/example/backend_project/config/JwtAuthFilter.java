//package com.example.backend_project.config;
//
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.List;
//
//public class JwtAuthFilter extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException {
//        try {
//            String token = retriveJwt(request); // 從 http header 取出 JWT(Json Web Token)
//            if (token != null){
//                // 因為 WebSecurityConfig 設定 sessionCreationPolicy(SessionCreationPolicy.STATELESS)，所以
//                // 不用 http session 記錄資料，因此 SecurityContextHolder 不會將登入認証成功後的 Authentication 記到
//                // http session。也就是說登入認証成功後的 Authentication 不會被記錄到系統，每次的 http request 都必需重新
//                // 進行一次登入認証，不然從 SecurityContextHolder.getContext().getAuthentication() 取回登入認証成功後
//                // 的 Authentication instance 一定是 null。
//
//                //region 當 token 變數可以成功解析出 userName 與 userRoles 時，代表是合法 token，這時可以用 token 裡的資料產生
//                //       登入認証成功後的 Authentication，並將它存放到 SecurityContextHolder.getContext().setAuthentication(...) method
//                //       讓系統知道用戶已登入認証成功
//                String userName = jwtUtil.parseUserNameFromToken(token); // 解析失敗時會丟出 exception
//                List<SimpleGrantedAuthority> userAuthorities = jwtUtil.parseUserAuthoritiesFromToken(token); // 解析失敗時會丟出 exception
//                UserDetails userDetails = new UserDetailsImpl(userName, null, userAuthorities);
//
//                UsernamePasswordAuthenticationToken authAfterSuccessLogin = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                authAfterSuccessLogin.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                SecurityContextHolder.getContext().setAuthentication(authAfterSuccessLogin);
//                //endregion
//            }
//        }
//        catch (Exception e) {
//            ...etc
//
//            return;
//        }
//
//        filterChain.doFilter(request, response);
//}
