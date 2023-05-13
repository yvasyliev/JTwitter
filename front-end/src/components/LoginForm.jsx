import { Form, useActionData } from 'react-router-dom';

export default function LoginForm() {
  const loginError = useActionData();

  const loginMessage = loginError ? loginError.message : null;

  return (
    <Form method="post">
      <input type="text" name="login" required placeholder="Username or email" />
      <input type="password" name="password" required placeholder="Password" />
      <button type="submit">Sing In</button>
      {loginMessage}
    </Form>
  );
}
