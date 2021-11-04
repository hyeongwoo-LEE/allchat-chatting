package com.allchat.allchatchatting.service;

import com.allchat.allchatchatting.collection.Chat;
import com.allchat.allchatchatting.collection.ChatRepository;
import com.allchat.allchatchatting.dto.ChatDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatRepository chatRepository;

    /**
     * 메세지 저장
     */
    public Mono<Chat> saveMessage(ChatDTO chatDTO){

        Chat chat = chatDTO.toEntity();

        return chatRepository.save(chat);
    }

}
