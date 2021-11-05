package com.allchat.allchatchatting.controller;

import com.allchat.allchatchatting.collection.Chat;
import com.allchat.allchatchatting.collection.ChatRepository;
import com.allchat.allchatchatting.dto.ChatAuthDTO;
import com.allchat.allchatchatting.dto.ChatDTO;
import com.allchat.allchatchatting.dto.ChatJoinDTO;
import com.allchat.allchatchatting.service.ChatService;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@RestController
public class ChatController {

    private final ChatService chatService;
    private final ChatRepository chatRepository;

    /**
     * 채팅방 메세지 불러오기
     */
    @CrossOrigin
    @GetMapping(value = "/rooms/{roomId}/chats", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Chat> findByRoomId(@PathVariable Integer roomId, @RequestBody ChatAuthDTO chatAuthDTO,
                                   ServerHttpRequest request){

        String username = request.getHeaders().getFirst("username");

        //TODO 권한체크
        if(!username.equals(chatAuthDTO.getUsername())){
            System.out.println("에러 발생");
        }

        return chatService.chatList(roomId, chatAuthDTO.getLocalDateTime())
                .subscribeOn(Schedulers.boundedElastic());
    }

    /**
     * 메세지 저장
     */
    @CrossOrigin
    @PostMapping("/chats")
    public Mono<Chat> saveMessage(@RequestBody ChatDTO chatDTO, ServerHttpRequest request) {

        String username = request.getHeaders().getFirst("username");

        //TODO 권한체크

        return chatService.saveMessage(chatDTO);
    }

    /**
     * 방참여 메시지 저장
     */
    @CrossOrigin
    @PostMapping("/chats/join")
    public Mono<Chat> saveJoinMessage(@RequestBody ChatJoinDTO chatJoinDTO, ServerHttpRequest request) {

        String username = request.getHeaders().getFirst("username");

        //TODO 권한체크

        return chatService.saveJoinMessage(chatJoinDTO);
    }
}
