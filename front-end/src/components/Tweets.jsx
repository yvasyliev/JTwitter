import useTweets from "../hooks/use-tweets";
import CreateTweetForm from "./CreateTweetForm";
import TweetLink from "./TweetLink";

export default function Tweets({ tweetsFetcher, parentTweetId }) {
  const [tweets, hasMoreTweets, fetchMoreTweets, username, addTweet] =
    useTweets(tweetsFetcher);

  return (
    <div>
      {username && (
        <CreateTweetForm
          username={username}
          onTweetCreated={addTweet}
          parentTweetId={parentTweetId}
        />
      )}
      <ul>
        {tweets.map((tweet) => (
          <li key={tweet.id}>
            <TweetLink tweet={tweet} />
          </li>
        ))}
      </ul>
      {hasMoreTweets && (
        <button onClick={fetchMoreTweets}>Load more tweets</button>
      )}
    </div>
  );
}
