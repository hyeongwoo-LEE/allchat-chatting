package com.allchat.allchatchatting.service;

import com.allchat.allchatchatting.collection.Chat;
import com.allchat.allchatchatting.dto.ChatDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChatServiceTest {

    @Autowired ChatService chatService;

    @Rollback
    @Test
    void 메세지_저장() throws Exception{
        //given
        ChatDTO chatDTO = ChatDTO.builder()
                .msg("안녕하세요")
                .roomId(1L)
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
        Assertions.assertThat(saveChat.getCreateDateTime()).isNotNull();
    }


}