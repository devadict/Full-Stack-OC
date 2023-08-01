package com.app.raghu.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateComment {
    private Integer id;
    
    private String content;
    
    private Integer postId;
}
