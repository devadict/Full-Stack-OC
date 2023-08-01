package com.app.raghu.dto.response;

import java.util.List;

import com.app.raghu.entity.Topic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TopicListsResponse {
     private List<Topic> topics;
}
