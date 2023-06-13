import { useLoaderData } from "react-router-dom";
import Tweet from "../components/Tweet";
import Tweets from "../components/Tweets";
import tweetService from "../service/TweetService";
import { useCallback } from "react";

export default function TweetPage() {
  const tweet = useLoaderData();
  const tweetsFetcher = useCallback(
    async (page) => await tweetService.fetchReplies(tweet.id, page),
    [tweet.id]
  );

  return (
    <div>
      <Tweet tweet={tweet} />
      <Tweets tweetsFetcher={tweetsFetcher} parentTweetId={tweet.id} />
    </div>
  );
}

export async function loader({ params }) {
  return await tweetService.fetchTweet(params.tweetId);
}
