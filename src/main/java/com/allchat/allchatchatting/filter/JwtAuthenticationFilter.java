package com.allchat.allchatchatting.filter;

import com.allchat.allchatchatting.collection.Chat;
import com.allchat.allchatchatting.dto.AuthFilterDTO;
import com.allchat.allchatchatting.dto.ChatDTO;
import com.allchat.allchatchatting.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtUtil jwtUtil;

    private ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

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

        ServerHttpResponse response = exchange.getResponse();

        System.out.println(LocalDateTime.now());

        AuthFilterDTO authFilterDTO = AuthFilterDTO.builder()
                .timestamp(LocalDateTime.now().toString())
                .status(401)
                .error("Unauthorized")
                .message("Unauthorized")
                .path(request.getPath().toString())
                .build();

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, "application/json");
        DataBuffer dataBuffer = response.bufferFactory().wrap(objectMapper.writeValueAsBytes(authFilterDTO));

        return response.writeWith(Mono.just(dataBuffer));
    }
}
