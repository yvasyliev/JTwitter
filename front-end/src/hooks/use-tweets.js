import { useEffect, useState } from "react";
import { useRouteLoaderData } from "react-router-dom";

export default function useTweets(tweetsFetcher) {
  const [tweets, setTweets] = useState([]);
  const [hasMoreTweets, setHasMoreTweets] = useState(false);
  const [page, setPage] = useState(0);
  const { username } = useRouteLoaderData("root") || {};

  useEffect(() => {
    tweetsFetcher().then(({ tweets, hasMoreTweets }) => {
      setTweets(tweets);
      setHasMoreTweets(hasMoreTweets);
      setPage(1);
    });
  }, [tweetsFetcher]);

  async function fetchMoreTweets() {
    const { tweets, hasMoreTweets } = await tweetsFetcher(page);
    setTweets((prevTweets) => [...prevTweets, ...tweets]);
    setHasMoreTweets(hasMoreTweets);
    setPage((page) => page + 1);
  }

  function addTweet(tweet) {
    setTweets((prevTweets) => [tweet, ...prevTweets]);
  }

  return [tweets, hasMoreTweets, fetchMoreTweets, username, addTweet];
}
