import Tweets from "../components/Tweets";
import { useLoaderData, useRouteLoaderData } from "react-router-dom";
import tweetService from "../service/TweetService";

export default function TweetsPage() {
  const { tweets, hasMoreTweets } = useLoaderData();

  const { username } = useRouteLoaderData("root") || {};

  return (
    <div>
      <Tweets
        initTweets={tweets}
        initHasMoreTweets={hasMoreTweets}
        username={username}
      />
    </div>
  );
}

export async function loader() {
  return await tweetService.fetchTweets();
}
