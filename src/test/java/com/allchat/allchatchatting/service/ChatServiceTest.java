package com.allchat.allchatchatting.service;

import com.allchat.allchatchatting.collection.Chat;
import com.allchat.allchatchatting.dto.ChatDTO;
import com.allchat.allchatchatting.dto.ChatJoinDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

@SpringBootTest
class ChatServiceTest {

    @Autowired ChatService chatService;

    @Test
    void 메세지_저장() throws Exception{
        //given
        ChatDTO chatDTO = ChatDTO.builder()
                .msg("안녕하세요")
                .roomId(1)
                .sender("이형우")
                .build();

        //when
        Mono<Chat> chatMono = chatService.saveMessage(chatDTO);

        //then
        Chat saveChat = chatMono.block();

        Assertions.assertThat(saveChat.getId()).isNotNull();
        Assertions.assertThat(saveChat.getMsg()).isEqualTo(chatDTO.getMsg());
        Assertions.assertThat(saveChat.getSender()).isEqualTo(chatDTO.getSender());
        Assertions.assertThat(saveChat.getRoomId()).isEqualTo(chatDTO.getRoomId());
        Assertions.assertThat(saveChat.getParticipant()).isNull();
        Assertions.assertThat(saveChat.getCreateDateTime()).isNotNull();
    }

    @Test
    void 방참여_알림() throws Exception{
        //given
        ChatJoinDTO chatJoinDTO = ChatJoinDTO.builder()
                .participant("이형우")
                .roomId(1)
                .build();

        //when
        Mono<Chat> chatMono = chatService.saveJoinMessage(chatJoinDTO);

        //then
        Chat saveChat = chatMono.block();

        Assertions.assertThat(saveChat.getId()).isNotNull();
        Assertions.assertThat(saveChat.getMsg()).isEqualTo("이형우님이 방에 참여하셨습니다.");
        Assertions.assertThat(saveChat.getSender()).isNull();
        Assertions.assertThat(saveChat.getRoomId()).isEqualTo(chatJoinDTO.getRoomId());
        Assertions.assertThat(saveChat.getParticipant()).isEqualTo(chatJoinDTO.getParticipant());
        Assertions.assertThat(saveChat.getCreateDateTime()).isNotNull();
    }


}