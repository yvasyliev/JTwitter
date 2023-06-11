import Tweets from "../components/Tweets";
import tweetService from "../service/TweetService";
import { useCallback } from "react";

export default function TweetsPage() {
  const tweetsFetcher = useCallback(
    async (page) => await tweetService.fetchTweets(page),
    []
  );

  return <Tweets tweetsFetcher={tweetsFetcher} />;
}
