package com.github.yvasyliev.repository;

import com.github.yvasyliev.model.dto.TweetDTO;
import com.github.yvasyliev.model.entity.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
    List<Tweet> findByUserId(Long userId);

    @Query("""
            SELECT
                new com.github.yvasyliev.model.dto.TweetDTO(
                    t.id,
                    t.text,
                    t.createdAt,
                    (
                        SELECT
                            COUNT(c_t)
                        FROM
                            Tweet c_t
                        WHERE
                            c_t.parentTweet.id = t.id
                    )
                )
            FROM
                Tweet t
            WHERE
                t.id = ?1""")
    Optional<TweetDTO> findTweetDTOById(Long tweetId);
}
