import { useLoaderData } from "react-router-dom";
import Tweet from "../components/Tweet";
import Tweets from "../components/Tweets";

export default function TweetPage() {
  const { tweet, replies } = useLoaderData();

  return (
    <div>
      <Tweet tweet={tweet} />
      <Tweets tweets={replies.tweets} />
    </div>
  );
}

export async function loader({ params }) {
  const tweet = await fetchTweet(params.tweetId);
  if (tweet) {
    const replies = await fetchReplies(params.tweetId);
    if (replies) {
      return { tweet, replies };
    }
  }

  return null;
}

async function fetchTweet(tweetId) {
  const response = await fetch(
    `http://localhost:8080/api/v1/tweets/${tweetId}`
  );

  return response.ok ? await response.json() : null;
}

async function fetchReplies(tweetId) {
  const response = await fetch(
    `http://localhost:8080/api/v1/tweets/${tweetId}/replies`
  );

  return response.ok ? await response.json() : null;
}
