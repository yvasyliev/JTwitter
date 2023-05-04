import LoginForm from '../components/LoginForm';
import { redirect } from 'react-router-dom';

export default function LoginPage() {
  return <LoginForm />
}

export async function action({ request, params }) {
  const formData = await request.formData();
  const credentials = {
    login: formData.get('login'),
    password: formData.get('password')
  }

  const response = await fetch('http://localhost:8080/api/v1/users/signIn', {
    method: request.method,
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(credentials)
  });

  console.log(response.status, await response.json());

  return redirect('/');
}
