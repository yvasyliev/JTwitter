import { host } from "./global-config";

class UserService {
  async login(credentials) {
    const response = await fetch(`${host}/api/v1/users/signIn`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(credentials),
    });

    if (response.ok) {
      const { token, expiresAt } = await response.json();
      localStorage.setItem("token", token);
      localStorage.setItem("expiresAt", expiresAt);
      return true;
    }

    return false;
  }

  isLoggedIn() {
    const token = localStorage.getItem("token");
    const expiresAt = localStorage.getItem("expiresAt");
    return token && expiresAt && new Date(expiresAt) > new Date();
  }

  authorization() {
    return `Bearer ${localStorage.getItem("token")}`;
  }

  async logout() {
    const response = await fetch(`${host}/api/v1/users/signOut`, {
      method: "POST",
      headers: {
        Authorization: this.authorization(),
      },
    });

    if (response.ok) {
      localStorage.removeItem("token");
      localStorage.removeItem("expiresAt");
      return true;
    }

    return false;
  }

  async register(user) {
    const response = await fetch(`${host}/api/v1/users`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(user),
    });

    const responseBody = await response.json();

    if (response.ok) {
      const { token, expiresAt } = responseBody;
      localStorage.setItem("token", token);
      localStorage.setItem("expiresAt", expiresAt);
    } else {
      throw new Error(JSON.stringify(responseBody));
    }
  }

  async setUserPhoto(photo) {
    const formData = new FormData();
    formData.append("photo", photo);

    const response = await fetch(`${host}/api/v1/users/photo`, {
      method: "PATCH",
      headers: {
        Authorization: this.authorization(),
      },
      body: formData,
    });

    return response.ok;
  }
}

const userService = new UserService();

export default userService;
