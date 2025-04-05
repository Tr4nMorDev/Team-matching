package ut.edu.teammatching.auth.security;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        System.out.println("🔍 Checking Authorization Header: " + authHeader);

        // Kiểm tra xem có header Authorization hay không
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);  // Cắt bỏ "Bearer "
        System.out.println("🔑 Extracted Token: " + token);

        try {
            if (jwtUtil.validateToken(token)) {
                Authentication authentication = jwtUtil.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);

                System.out.println("✅ Token is valid. User authenticated: " + authentication.getName());
                System.out.println("✅ User roles: " + authentication.getAuthorities());
            }
        } catch (Exception e) {
            System.err.println("❌ Lỗi xác thực token: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}
