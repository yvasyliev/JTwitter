package com.github.yvasyliev.model.entity;

import com.github.yvasyliev.model.entity.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "tweets")
public class Tweet {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Tweet parentTweet;

    @OneToMany(mappedBy = "parentTweet", cascade = CascadeType.REMOVE)
    private Set<Tweet> childTweets;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false)
    @Size(min = 1, max = 256)
    private String text;

    @ManyToMany(mappedBy = "likedTweets")
    private Set<User> likes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Tweet getParentTweet() {
        return parentTweet;
    }

    public void setParentTweet(Tweet parentTweet) {
        this.parentTweet = parentTweet;
    }

    public Set<Tweet> getChildTweets() {
        return childTweets;
    }

    public void setChildTweets(Set<Tweet> childTweets) {
        this.childTweets = childTweets;
    }

    public Set<User> getLikes() {
        return likes;
    }

    public void setLikes(Set<User> likes) {
        this.likes = likes;
    }
}
