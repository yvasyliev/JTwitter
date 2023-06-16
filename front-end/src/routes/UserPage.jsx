import { useCallback } from "react";
import { useParams, useRouteLoaderData } from "react-router-dom";
import tweetService from "../service/TweetService";
import Tweets from "../components/Tweets";

export default function UserPage() {
  const { username: pathUsername } = useParams();
  const { username } = useRouteLoaderData("root") || {};
  const tweetsFetcher = useCallback(
    async (page) => await tweetService.fetchUserTweets(pathUsername, page),
    [pathUsername]
  );

  return (
    <Tweets
      tweetsFetcher={tweetsFetcher}
      canAddTweet={pathUsername === username}
    />
  );
}
