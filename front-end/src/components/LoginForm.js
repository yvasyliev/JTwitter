import { Form } from 'react-router-dom';

export default function LoginForm() {
  return (
    <Form method="post">
      <input type="text" name="login" required placeholder="Username or email" />
      <input type="password" name="password" required placeholder="Password" />
      <button>Sing In</button>
    </Form>
  );
}
