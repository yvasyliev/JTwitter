import { useRef, useState } from "react";
import tweetService from "../service/TweetService";

export default function CreateTweetForm({
  onTweetCreated,
  username,
  parentTweetId,
}) {
  const tweetText = useRef();
  const [createTweetError, setCreateTweetError] = useState(null);

  async function onCreateTweet(event) {
    event.preventDefault();

    const { tweetId: id } =
      (await tweetService.createTweet(
        parentTweetId,
        tweetText.current.value
      )) || {};

    if (id) {
      onTweetCreated({
        id,
        text: tweetText.current.value,
        author: { username },
        createdAt: new Date().toString(),
        replies: 0,
        likes: 0,
      });
      tweetText.current.value = "";
      setCreateTweetError(null);
    } else {
      setCreateTweetError(<span>Something went wrong...</span>);
    }
  }

  return (
    <form method="post" onSubmit={onCreateTweet}>
      <textarea
        name="text"
        required
        placeholder="Type a tweet"
        ref={tweetText}
      />
      {createTweetError}
      <button type="submit">Create</button>
    </form>
  );
}
