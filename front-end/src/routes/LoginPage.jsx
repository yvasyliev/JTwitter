import LoginForm from '../components/LoginForm';
import { Link, redirect } from 'react-router-dom';
import authService from '../auth/AuthService';

export default function LoginPage() {
  return (
    <>
      <LoginForm />
      <Link to="/register">Or create an account.</Link>
    </>
  );
}

export async function action({ request }) {
  const formData = await request.formData();
  const credentials = {
    login: formData.get("login"),
    password: formData.get("password")
  }

  return await authService.login(credentials)
    ? redirect("/")
    : { message: "Incorrect username or password." }
}
