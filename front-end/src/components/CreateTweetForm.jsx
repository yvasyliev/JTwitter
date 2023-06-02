import { useRef, useState } from 'react';
import authService from '../auth/AuthService';

export default function CreateTweetForm({ onTweetCreated, username }) {
  const tweetText = useRef();
  const [ createTweetError, setCreateTweetError ] = useState(null);

  async function onCreateTweet(event) {
    event.preventDefault();

    const body = { text: tweetText.current.value };
    const response = await fetch("http://localhost:8080/api/v1/tweets", {
      method: "POST",
      headers: {
        "Authorization": authService.authorization(),
        "Content-Type": "application/json"
      },
      body: JSON.stringify(body)
    });

    if (response.ok) {
      const { tweetId: id } = await response.json();
      onTweetCreated({
        id,
        text: tweetText.current.value,
        author: { username },
        createdAt: new Date().toString(),
        replies: 0,
        likes: 0
      });
      tweetText.current.value = "";
      setCreateTweetError(null);
    } else {
      setCreateTweetError(<span>Something went wrong...</span>);
    }
  }

  return (
    <form method="post" onSubmit={onCreateTweet}>
      <textarea name="text" required placeholder="Type a tweet" ref={tweetText} />
      {createTweetError}
      <button type="submit">Create</button>
    </form>
  );
}