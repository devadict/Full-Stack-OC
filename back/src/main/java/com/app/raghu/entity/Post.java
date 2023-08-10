package com.app.raghu.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="posts")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    
    @Schema(description = "Post identifier", example = "1")
    @Id
    @GeneratedValue
    private Integer id;

    @Schema(description = "Title of the post", example = "Progressive Web Apps")
    private String title;

    @Schema(description = "Content inside the post", example = "PWA using Vue...")
    private String content;

    @Schema(description = "Creation time of the post", example = "2023-06-19T14:30:00Z")
    @CreationTimestamp
    private Date date;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @OneToMany
    @JsonIgnore
    private List<Comment> comments;

    public static Object builder() {
        return null;
    }
}
