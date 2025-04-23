package ut.edu.teammatching.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import ut.edu.teammatching.auth.security.JwtChannelInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Đảm bảo chỉ cho phép các origin từ ứng dụng frontend của bạn
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*") // Hợp nhất các URL origin
                .withSockJS(); // SockJS fallback
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");  // Enable broker destinations
        config.setApplicationDestinationPrefixes("/app");  // Prefix cho các yêu cầu ứng dụng
        config.setUserDestinationPrefix("/user");  // Địa chỉ đến người dùng
    }
}
