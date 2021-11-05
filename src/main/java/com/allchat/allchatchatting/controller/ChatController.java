package com.allchat.allchatchatting.controller;

import com.allchat.allchatchatting.collection.Chat;
import com.allchat.allchatchatting.collection.ChatRepository;
import com.allchat.allchatchatting.dto.ChatDTO;
import com.allchat.allchatchatting.dto.ChatJoinDTO;
import com.allchat.allchatchatting.service.ChatService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

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
    public Flux<Chat> findByRoomId(@PathVariable Integer roomId){

        return chatService.chatList(roomId)
                .subscribeOn(Schedulers.boundedElastic());
    }


    /**
     * 메세지 저장
     */
    @CrossOrigin
    @PostMapping("/chats")
    public Mono<Chat> saveMessage(@RequestBody ChatDTO chatDTO) {

        return chatService.saveMessage(chatDTO);
    }

    /**
     * 방참여 메시지 저장
     */
    @CrossOrigin
    @PostMapping("/chats/join")
    public Mono<Chat> saveJoinMessage(@RequestBody ChatJoinDTO chatJoinDTO) {

        return chatService.saveJoinMessage(chatJoinDTO);
    }
}
