package ut.edu.teammatching.auth.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "your-secret-key-your-secret-key-your-secret-key"; // Ít nhất 32 ký tự
    private static final long EXPIRATION_TIME = 86400000; // 1 ngày

    private final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // ✅ Tạo JWT Token
    public String generateToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key)
                .compact();
    }

    // ✅ Lấy tất cả claims từ token
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()  // Dùng parser() cho phiên bản cũ
                .setSigningKey(key.getEncoded())  // Đặt khóa ký (HMAC key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ✅ Kiểm tra token có hợp lệ không
    public boolean isTokenExpired(String token) {
        return getAllClaimsFromToken(token).getExpiration().before(new Date());
    }

    // ✅ Xác thực token và lấy thông tin người dùng
    public Authentication getAuthentication(String token) {
        Claims claims = getAllClaimsFromToken(token);
        String username = claims.getSubject();
        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("USER")); // Có thể điều chỉnh role nếu cần

        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }

    // ✅ Kiểm tra token hợp lệ và chưa hết hạn
    public boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    // ✅ Lấy username từ token
    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }
}
