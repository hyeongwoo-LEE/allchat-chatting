package com.allchat.allchatchatting.collection;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "allchat")
public class Chat {

    @Id
    private String id;

    private String msg;

    private String sender;

    private Long roomId;

    @Builder.Default
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm")
    private LocalDateTime createDateTime = LocalDateTime.now();

}