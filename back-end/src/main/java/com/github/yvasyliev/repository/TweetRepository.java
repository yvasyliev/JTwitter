package com.github.yvasyliev.repository;

import com.github.yvasyliev.model.dto.TweetDTO;
import com.github.yvasyliev.model.entity.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TweetRepository extends JpaRepository<Tweet, Long> {
    @Query("""
            select
                new com.github.yvasyliev.model.dto.TweetDTO(
                    t.id,
                    t.text,
                    t.createdAt,
                    size(t.childTweets),
                    size(t.likes),
                    new com.github.yvasyliev.model.dto.UserDTO(
                        t.user.id,
                        t.user.username,
                        t.user.firstName,
                        t.user.lastName,
                        t.user.photo,
                        cast(null as string)
                    )
                )
            from
                Tweet t
            where
                t.id = :tweetId""")
    Optional<TweetDTO> findTweetDTOById(@Param("tweetId") Long tweetId);

    @Query(value = """
            select
                new com.github.yvasyliev.model.dto.TweetDTO(
                    t.id,
                    t.text,
                    t.createdAt,
                    size(t.childTweets),
                    size(t.likes),
                    new com.github.yvasyliev.model.dto.UserDTO(
                        t.user.id,
                        t.user.username,
                        t.user.firstName,
                        t.user.lastName,
                        t.user.photo,
                        cast(null as string)
                    )
                )
            from
                Tweet t"""
    )
    Page<TweetDTO> findTweetDTOs(Pageable pageable);


    @Query(value = """
            select
                new com.github.yvasyliev.model.dto.TweetDTO(
                    t.id,
                    t.text,
                    t.createdAt,
                    size(t.childTweets),
                    size(t.likes),
                    new com.github.yvasyliev.model.dto.UserDTO(
                        t.user.id,
                        t.user.username,
                        t.user.firstName,
                        t.user.lastName,
                        t.user.photo,
                        cast(null as string)
                    )
                )
            from
                Tweet t
            where
                t.user.username = :username"""
    )
    Page<TweetDTO> findTweetDTOsByUsername(@Param("username") String username, Pageable pageable);

    @Query(value = """
            select
                new com.github.yvasyliev.model.dto.TweetDTO(
                    t.id,
                    t.text,
                    t.createdAt,
                    size(t.childTweets),
                    size(t.likes),
                    new com.github.yvasyliev.model.dto.UserDTO(
                        t.user.id,
                        t.user.username,
                        t.user.firstName,
                        t.user.lastName,
                        t.user.photo,
                        cast(null as string)
                    )
                )
            from
                Tweet t
            where
                t.parentTweet.id = :parentTweetId"""
    )
    Page<TweetDTO> findTweetDTOsByParentTweetId(@Param("parentTweetId") Long parentTweetId, Pageable pageable);
}
