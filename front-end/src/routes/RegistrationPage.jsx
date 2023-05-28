import RegistrationForm from '../components/RegistrationForm';
import authService from '../auth/AuthService';
import { Link, redirect } from 'react-router-dom';

export default function RegistrationPage() {
  return (
    <>
      <RegistrationForm />
      <Link to="/login">Or login.</Link>
    </>
  );
}

export async function action({ request }) {
  const formData = await request.formData();
  const user = {
    firstName: formData.get("firstName"),
    lastName: formData.get("lastName"),
    username: formData.get("username"),
    email: formData.get("email"),
    password: formData.get("password"),
    confirmPassword: formData.get("confirmPassword")
  }

  try {
    await authService.register(user);
    return redirect("/");
  } catch (err) {
    return JSON.parse(err.message);
  }
}
