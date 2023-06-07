import { useState } from "react";
import CreateTweetForm from "./CreateTweetForm";
import TweetLink from "./TweetLink";
import tweetService from "../service/TweetService";

export default function Tweets({ initTweets, initHasMoreTweets, username }) {
  const [tweets, setTweets] = useState(initTweets);
  const [hasMoreTweets, setHasMoreTweets] = useState(initHasMoreTweets);
  const [page, setPage] = useState(1);

  async function fetchMoreTweets() {
    const { tweets: moreTweets, hasMoreTweets: stillHasMoreTweets } =
      await tweetService.fetchTweets(page);
    setTweets((prevTweets) => [...prevTweets, ...moreTweets]);
    setPage((prevPage) => prevPage + 1);
    setHasMoreTweets(stillHasMoreTweets);
  }

  function onTweetCreated(newTweet) {
    setTweets((prevTweets) => [newTweet, ...prevTweets]);
  }

  return (
    <div>
      {username && (
        <CreateTweetForm onTweetCreated={onTweetCreated} username={username} />
      )}
      <ul>
        {tweets.map((tweet) => (
          <li key={tweet.id}>
            <TweetLink tweet={tweet} />
          </li>
        ))}
      </ul>
      {hasMoreTweets && (
        <button onClick={fetchMoreTweets}>Show more tweets.</button>
      )}
    </div>
  );
}
