package com.allchat.allchatchatting.service;

import com.allchat.allchatchatting.collection.Chat;
import com.allchat.allchatchatting.collection.ChatRepository;
import com.allchat.allchatchatting.dto.ChatDTO;
import com.allchat.allchatchatting.dto.ChatJoinDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatRepository chatRepository;

    /**
     * 채팅방 메세지 불러오기
     */
    public Flux<Chat> chatList(Integer roomId){

        return chatRepository.mFindByRoomId(roomId);
    }


    /**
     * 메세지 저장
     */
    public Mono<Chat> saveMessage(ChatDTO chatDTO){

        Chat chat = chatDTO.toEntity();

        return chatRepository.save(chat);
    }

    /**
     * 방 참여 메세지 저장
     */
    public Mono<Chat> saveJoinMessage(ChatJoinDTO chatJoinDTO){

        Chat chat = chatJoinDTO.toEntity();

        return chatRepository.save(chat);
    }

}
