package com.github.yvasyliev.repository;

import com.github.yvasyliev.model.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
    public List<Tweet> findByUserId(Long userId);
}
