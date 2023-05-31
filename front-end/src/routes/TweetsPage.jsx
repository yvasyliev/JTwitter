import Tweets from '../components/Tweets';
import { useLoaderData } from 'react-router-dom';

export default function TweetsPage() {
  const { tweets } = useLoaderData();

  return <Tweets tweets={tweets} />;
}

export async function loader() {
  const response = await fetch("http://localhost:8080/api/v1/tweets");

  if (response.ok) {
    return await response.json();
  }

  return null;
}
