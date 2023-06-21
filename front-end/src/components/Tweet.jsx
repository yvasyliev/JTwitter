export default function Tweet({ tweet }) {
  return (
    <div>
      <img
        src={`http://localhost:8080/${tweet.author.photo}`}
        alt="Author"
        width="50"
      />
      <div>{`@${tweet.author.username}`}</div>
      <div>{tweet.text}</div>
      <div>{tweet.createdAt}</div>
      <div>{tweet.replies}</div>
      <div>{tweet.likes}</div>
    </div>
  );
}
