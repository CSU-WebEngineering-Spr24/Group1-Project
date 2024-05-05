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
import Dashboard from './Pages/Dashboard';

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
    children: [
      { 
        path: "/",
        element: <Home />
      },
      { 
        path: "/home",
        element: <Home />
      },
      {
        path: "readfacts",
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
      {
        path: "dashboard",
        element: <Dashboard />,
      }
    ]
  }
]);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
      <RouterProvider router={router} />
  </React.StrictMode>
);
