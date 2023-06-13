import authService from "./AuthService";

class TweetService {
  async fetchTweets(page = 0) {
    const params = new URLSearchParams({ page }).toString();
    return this.#fetch(`http://localhost:8080/api/v1/tweets?${params}`);
  }

  async fetchTweet(tweetId) {
    return this.#fetch(`http://localhost:8080/api/v1/tweets/${tweetId}`);
  }

  async fetchReplies(tweetId, page = 0) {
    const params = new URLSearchParams({ page }).toString();
    return this.#fetch(
      `http://localhost:8080/api/v1/tweets/${tweetId}/replies?${params}`
    );
  }

  async createTweet(parentTweetId, text) {
    const body = { parentTweetId, text };
    const response = await fetch("http://localhost:8080/api/v1/tweets", {
      method: "POST",
      headers: {
        Authorization: authService.authorization(),
        "Content-Type": "application/json",
      },
      body: JSON.stringify(body),
    });

    return response.ok ? await response.json() : null;
  }

  async #fetch(url) {
    const response = await fetch(url);
    return response.ok ? await response.json() : null;
  }
}

const tweetService = new TweetService();

export default tweetService;
