import { Form, useActionData } from 'react-router-dom';

export default function RegistrationForm() {
  const registrationErrors = useActionData();

  const incorrectFirstName = registrationErrors && registrationErrors.firstName
    ? <span>{registrationErrors.firstName[0]}</span>
    : null;

  const incorrectLastName = registrationErrors && registrationErrors.lastName
    ? <span>{registrationErrors.lastName[0]}</span>
    : null;

  const incorrectUsername = registrationErrors && registrationErrors.username
    ? <span>{registrationErrors.username[0]}</span>
    : null;

  const incorrectEmail = registrationErrors && registrationErrors.email
    ? <span>{registrationErrors.email[0]}</span>
    : null;

  const incorrectPassword = registrationErrors && registrationErrors.password
    ? <span>{registrationErrors.password[0]}</span>
    : null;

  const incorrectConfirmPassword = registrationErrors && registrationErrors.confirmPassword
    ? <span>{registrationErrors.confirmPassword[0]}</span>
    : null;

  return (
    <Form method="post">
      <input type="text" name="firstName" required placeholder="First name" />
      {incorrectFirstName}
      <input type="text" name="lastName" required placeholder="Last name" />
      {incorrectLastName}
      <input type="text" name="username" required placeholder="Username" />
      {incorrectUsername}
      <input type="email" name="email" required placeholder="Email" />
      {incorrectEmail}
      <input type="password" name="password" required placeholder="Password" />
      {incorrectPassword}
      <input type="password" name="confirmPassword" required placeholder="Password" />
      {incorrectConfirmPassword}
      <button type="submit">Register</button>
    </Form>
  );
}