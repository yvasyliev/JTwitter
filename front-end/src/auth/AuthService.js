class AuthService {
  async login(credentials) {
    const response = await fetch("http://localhost:8080/api/v1/users/signIn", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(credentials)
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
    const response = await fetch("http://localhost:8080/api/v1/users/signOut", {
      method: "POST",
      headers: {
        "Authorization": this.authorization()
      }
    });

    if (response.ok) {
      localStorage.removeItem("token");
      localStorage.removeItem("expiresAt");
      return true;
    }

    return false;
  }
}

const authService = new AuthService();

export default authService;
