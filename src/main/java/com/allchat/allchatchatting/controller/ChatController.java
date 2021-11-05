package com.allchat.allchatchatting.controller;

import com.allchat.allchatchatting.collection.Chat;
import com.allchat.allchatchatting.dto.ChatDTO;
import com.allchat.allchatchatting.service.ChatService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
public class ChatController {

    private final ChatService chatService;

    /**
     * 메세지 저장
     */
    @CrossOrigin
    @PostMapping("/chats")
    public Mono<Chat> saveMessage(@RequestBody ChatDTO chatDTO) {

        return chatService.saveMessage(chatDTO);
    }
}
