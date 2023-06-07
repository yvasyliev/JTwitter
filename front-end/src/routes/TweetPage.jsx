import { useLoaderData, useRouteLoaderData } from "react-router-dom";
import Tweet from "../components/Tweet";
import Tweets from "../components/Tweets";
import tweetService from "../service/TweetService";

export default function TweetPage() {
  const { tweet, replies } = useLoaderData();
  const { username } = useRouteLoaderData("root") || {};

  return (
    <div>
      <Tweet tweet={tweet} />
      <Tweets
        initTweets={replies.tweets}
        initHasMoreTweets={replies.hasMoreTweets}
        username={username}
      />
    </div>
  );
}

export async function loader({ params }) {
  const tweet = await tweetService.fetchTweet(params.tweetId);
  if (tweet) {
    const replies = await tweetService.fetchReplies(params.tweetId);
    if (replies) {
      return { tweet, replies };
    }
  }

  return null;
}
