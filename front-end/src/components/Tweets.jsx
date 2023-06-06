import TweetLink from "./TweetLink";

export default function Tweets({ tweets }) {
  return (
    <ul>
      {tweets.map((tweet) => (
        <li key={tweet.id}>
          <TweetLink tweet={tweet} />
        </li>
      ))}
    </ul>
  );
}
