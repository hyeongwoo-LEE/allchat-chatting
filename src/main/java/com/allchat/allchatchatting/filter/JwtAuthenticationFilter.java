package com.allchat.allchatchatting.filter;

import com.allchat.allchatchatting.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtUtil jwtUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        System.out.println("================================");
        System.out.println("웹필터 통과");
        ServerHttpRequest request = exchange.getRequest();

        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")) {

            //jwt 토큰 검증
            String username = jwtUtil.validateAndExtract(authHeader.substring(7));
            System.out.println(username);

            if (username != null) {
                ;
                ServerHttpRequest mutateRequest = exchange.getRequest().mutate().header("username", username).build();
                ServerWebExchange mutatedExchange = exchange.mutate().request(mutateRequest).build();

                return chain.filter(mutatedExchange);
            }
        }
        return null;
    }
}
