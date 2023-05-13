import LoginForm from '../components/LoginForm';
import { redirect } from 'react-router-dom';
import authService from '../auth/AuthService';

export default function LoginPage() {
  return <LoginForm />
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
