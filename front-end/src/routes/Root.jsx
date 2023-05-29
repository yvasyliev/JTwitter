import { Form, Link, Outlet, redirect, useLoaderData } from 'react-router-dom';
import authService from '../auth/AuthService';

export default function Root() {
  const user = useLoaderData();

  return (
    <>
      <nav>
        <ol>
          <li><Link to="/">Home</Link></li>
          {user && <li><Link to={`/${user.username}`}>My Tweets</Link></li>}
          {user && <li><Link to={`/${user.username}/settings`}>Settings</Link></li>}
          {
            user
              ? (
                <li>
                  <Form method="post">
                    <button type="submit">Logout</button>
                  </Form>
                </li>
              ) : <li><Link to="/login">Login</Link></li>
          }
        </ol>
      </nav>
      <main>
        <Outlet />
      </main>
    </>
  )
}

export async function loader() {
  if (authService.isLoggedIn()) {
    const response = await fetch("http://localhost:8080/api/v1/users", {
      headers: {
        "Authorization": authService.authorization()
      }
    });

    if (response.ok) {
      return await response.json();
    }
  }

  return null;
}

export async function action() {
  await authService.logout()
  return redirect("/");
}
