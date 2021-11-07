package com.allchat.allchatchatting.dto;


import com.allchat.allchatchatting.collection.Chat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ChatNoticeDTO {

    private String participant;

    private Integer roomId;

    private Boolean join;

    public Chat toEntity(){

        Chat chat = Chat.builder()
                .msg(participant+"님이 방에 참여하셨습니다.")
                .sender(null)
                .roomId(roomId)
                .participant(participant)
                .build();

        return chat;
    }
}
