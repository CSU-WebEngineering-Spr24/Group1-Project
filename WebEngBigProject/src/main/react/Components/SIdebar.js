import React, { useState } from 'react';
import {
  CDBSidebar,
  CDBSidebarContent,
  CDBSidebarFooter,
  CDBSidebarHeader,
  CDBSidebarMenu,
  CDBSidebarMenuItem,
} from 'cdbreact';
import { NavLink } from 'react-router-dom';
import { Home, Equalizer, ViewColumn, Person } from '@mui/icons-material';
import DashboardIcon from '@mui/icons-material/Dashboard';

const Sidebar = () => {
  const [username, setUsername] = useState(localStorage.getItem('user') || '');

  const handleUsernameChange = (event) => {
    setUsername(event.target.value);
  };

  const handleSaveUsername = () => {
    localStorage.setItem('user', username);
    alert('Username saved successfully!');
  };

  return (
    <div style={{ display: 'flex', height: '100vh', overflow: 'scroll initial' }}>
      <CDBSidebar textColor="#fff" backgroundColor="#333">
        <CDBSidebarHeader prefix={<Home />}>
          <a href="/" className="text-decoration-none" style={{ color: 'inherit' }}>
            Quiz App
          </a>
        </CDBSidebarHeader>

        <CDBSidebarContent className="sidebar-content">
          <CDBSidebarMenu>
            <NavLink exact to="/home" activeClassName="activeClicked">
              <CDBSidebarMenuItem suffix={<Home />}>Home</CDBSidebarMenuItem>
            </NavLink>
            <NavLink exact to="/quiz" activeClassName="activeClicked">
              <CDBSidebarMenuItem suffix={<Equalizer />}>Quiz Page</CDBSidebarMenuItem>
            </NavLink>
            <NavLink exact to="/readfacts" activeClassName="activeClicked">
              <CDBSidebarMenuItem suffix={<ViewColumn />}>Facts</CDBSidebarMenuItem>
            </NavLink>
            <NavLink exact to="/usage" activeClassName="activeClicked">
              <CDBSidebarMenuItem suffix={<Person />}>App Usage page</CDBSidebarMenuItem>
            </NavLink>
            <NavLink exact to="/dashboard" activeClassName="activeClicked">
              <CDBSidebarMenuItem suffix={<DashboardIcon />}>Dashboard</CDBSidebarMenuItem>
            </NavLink>
          </CDBSidebarMenu>
        </CDBSidebarContent>

        <CDBSidebarFooter style={{ textAlign: 'center' }}>
          <div style={{ padding: '20px 5px' }}>
            <input
              type="text"
              placeholder="Enter username"
              value={username}
              onChange={handleUsernameChange}
            />
            <button onClick={handleSaveUsername}>Save Username</button>
          </div>
        </CDBSidebarFooter>
      </CDBSidebar>
    </div>
  );
};

export default Sidebar;
