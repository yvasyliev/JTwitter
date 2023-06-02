import Tweets from '../components/Tweets';
import { useLoaderData, useRouteLoaderData } from 'react-router-dom';
import { useState } from 'react';
import CreateTweetForm from '../components/CreateTweetForm';

export default function TweetsPage() {
  const { tweets: initTweets, hasMoreTweets: initHasMoreTweets } = useLoaderData();
  const [ tweets, setTweets ] = useState(initTweets);
  const [ page, setPage ] = useState(1);
  const [ hasMoreTweets, setHasMoreTweets ] = useState(initHasMoreTweets);

  const { username } = useRouteLoaderData("root") || {};

  function onTweetCreated(newTweet) {
    setTweets(prevTweets => [ newTweet, ...prevTweets ]);
  }

  async function fetchMoreTweets() {
    const { tweets: moreTweets, hasMoreTweets: stillHasMoreTweets } = await fetchTweets(page);
    setTweets(prevTweets => [ ...prevTweets, ...moreTweets ]);
    setPage(prevPage => prevPage + 1);
    setHasMoreTweets(stillHasMoreTweets);
  }

  return (
    <div>
      {username && <CreateTweetForm onTweetCreated={onTweetCreated} username={username} />}
      <Tweets tweets={tweets} />
      {hasMoreTweets && <button onClick={fetchMoreTweets}>Show more tweets.</button>}
    </div>
  );
}

export async function loader() {
  return await fetchTweets();
}

async function fetchTweets(page = 0) {
  const params = new URLSearchParams({ page }).toString();
  const response = await fetch(`http://localhost:8080/api/v1/tweets?${params}`);

  if (response.ok) {
    return await response.json();
  }

  return null;
}
