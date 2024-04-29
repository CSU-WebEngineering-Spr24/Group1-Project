import logo from './logo.svg';
import './App.css';
import { Outlet } from 'react-router-dom';
import Sidebar from './Components/SIdebar';

function App({name="home"} ) {
  return (
    <div className="app" style={{ display: "flex" }}>
    <Sidebar />
    <div style={{ flex: 1 }}>
      <Outlet />
    </div>
  </div>
  );
}

export default App;
