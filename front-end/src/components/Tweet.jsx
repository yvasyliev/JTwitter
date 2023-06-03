import { Link } from "react-router-dom";

export default function Tweet({ tweet }) {
  return (
    <div>
      <Link to={`${tweet.author.username}/tweets/${tweet.id}`}>
        <div>{`@${tweet.author.username}`}</div>
        <div>{tweet.text}</div>
        <div>{tweet.createdAt}</div>
        <div>{tweet.replies}</div>
        <div>{tweet.likes}</div>
      </Link>
    </div>
  );
}
