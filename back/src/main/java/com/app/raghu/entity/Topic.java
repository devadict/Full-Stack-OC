package com.app.raghu.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="topics")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topic {
    
    @Schema(description = "Topic identifier", example = "1")
    @Id
    @GeneratedValue
    private Integer id;

    @Schema(description = "Name of the topic", example = "Web 3")
    private String name;

    @Schema(description = "Details of the topic", example = "Learn how Web3 works")
    private String description;
    
    @Schema(description = "Users who follows the topic")
    @ManyToMany
    @JoinColumn(name = "topic_id")
    private List<User> subscribers;

}
