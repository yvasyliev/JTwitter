import { useLoaderData } from "react-router-dom";
import Tweet from "../components/Tweet";

export default function TweetPage() {
  const { tweet, replies } = useLoaderData();
  // console.log("tweetData", tweetData);

  return (
    <div>
      <div>
        <div>{`@${tweet.author.username}`}</div>
        <div>{tweet.text}</div>
        <div>{tweet.createdAt}</div>
        <div>{tweet.replies}</div>
        <div>{tweet.likes}</div>
      </div>
      <ul>
        {replies.tweets.map((reply) => (
          <li key={reply.id}>
            <Tweet tweet={reply} />
          </li>
        ))}
      </ul>
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
