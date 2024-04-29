import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
// import reportWebVitals from './reportWebVitals';
import { createBrowserRouter, RouterProvider } from 'react-router-dom';
import Home from './Pages/Home';
import FormPage from './Pages/FormPage';
import AppUsagePage from './Pages/AppUsage';
import Quiz from './Pages/Quiz';

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    children: [
      { 
        path: "home",
        element: <Home />
      },
      {
        path: "facts",
        element: <FormPage />,
      },
      {
        path: "usage",
        element: <AppUsagePage />,
      },
      {
        path: "quiz",
        element: <Quiz />,
      },
    ],
  }
]);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
      <RouterProvider router={router} />
  </React.StrictMode>
);
