package com.app.raghu.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    
    @Schema(description = "Comment identifier", example = "1")
    @Id
    @GeneratedValue
    private Integer id;

    private String content;

    @Schema(description = "Creation time of the comment", example = "2023-06-19T14:30:00Z")
    @CreationTimestamp
    private Date date;

    @Schema(description = "Author of comment")
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Schema(description = "Post that is commented")
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;
}
