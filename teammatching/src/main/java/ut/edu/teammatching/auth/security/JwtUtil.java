package ut.edu.teammatching.auth.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;
import java.security.Key;
import java.util.Date;


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
                .signWith(key )
                .compact();
    }
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()  // Dùng parser() cho phiên bản cũ
                .setSigningKey(key.getEncoded())  // Đặt khóa ký (HMAC key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
