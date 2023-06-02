import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import reportWebVitals from './reportWebVitals';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import LoginPage, { action as signIn } from './routes/LoginPage';
import Root, { loader as getMe, action as logout } from './routes/Root';
import RegistrationPage, { action as register } from './routes/RegistrationPage';
import TweetsPage, { loader as fetchTweets } from './routes/TweetsPage';

const router = createBrowserRouter([
  {
    id: "root",
    index: '/',
    element: <Root />,
    children: [
      {
        index: true,
        element: <TweetsPage />,
        loader: fetchTweets
      }
    ],
    loader: getMe,
    action: logout
  },
  {
    path: '/login',
    element: <LoginPage />,
    action: signIn
  },
  {
    path: '/register',
    element: <RegistrationPage />,
    action: register
  }
]);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
