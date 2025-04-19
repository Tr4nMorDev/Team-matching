package ut.edu.teammatching.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JwtChannelInterceptor implements ChannelInterceptor {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        // Kiểm tra nếu đây là kết nối STOMP (WebSocket)
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = accessor.getFirstNativeHeader("Authorization");

            // Kiểm tra header "Authorization" có chứa Bearer token không
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);  // Lấy phần token sau "Bearer "

                // Kiểm tra token có hợp lệ không
                if (jwtTokenProvider.validateToken(token)) {
                    String userId = jwtTokenProvider.getUserIdFromToken(token); // Lấy userId từ token

                    // Gán userId làm Principal cho kết nối này
                    accessor.setUser(new UsernamePasswordAuthenticationToken(userId, null, List.of()));
                } else {
                    // Nếu token không hợp lệ, bạn có thể đưa ra thông báo lỗi hoặc từ chối kết nối
                    // Ví dụ: đóng kết nối hoặc trả lại lỗi thông qua exception
                    throw new RuntimeException("Invalid or expired JWT token");
                }
            } else {
                // Nếu không có token hoặc token không hợp lệ, có thể từ chối kết nối
                throw new RuntimeException("Authorization header is missing or malformed");
            }
        }

        return message;
    }
}
