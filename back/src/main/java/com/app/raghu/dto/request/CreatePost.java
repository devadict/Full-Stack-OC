package com.app.raghu.dto.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePost {

    private String title;

    private String content;
    
    private Integer topicId;

}
