import { Link } from "react-router-dom";
import Tweet from "./Tweet";

export default function TweetLink({ tweet }) {
  return (
    <Link to={`/${tweet.author.username}/tweets/${tweet.id}`}>
      <Tweet tweet={tweet} />
    </Link>
  );
}
