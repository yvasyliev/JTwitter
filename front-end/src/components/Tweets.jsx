import Tweet from './Tweet';

export default function Tweets({ tweets }) {
  return <ul>{tweets.map(tweet => <li key={tweet.id}><Tweet tweet={tweet} /></li>)}</ul>;
}
