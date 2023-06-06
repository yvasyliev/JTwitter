export default function Tweet({ tweet }) {
  return (
    <div>
      <div>{`@${tweet.author.username}`}</div>
      <div>{tweet.text}</div>
      <div>{tweet.createdAt}</div>
      <div>{tweet.replies}</div>
      <div>{tweet.likes}</div>
    </div>
  );
}
