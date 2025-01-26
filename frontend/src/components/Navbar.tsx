import React from 'react';
import { Link } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import './Navbar.css';


export const Navbar: React.FC = () => {
  const { isAuthenticated, logout, user, isAdmin, isPatient, isStaff, isPractitioner } = useAuth();
  return (
    <nav>
      <Link to="/">Hem</Link>
      {isAuthenticated ? (
        <>
          {isAdmin && <Link to="/registerStaff">Registrera Personal</Link>}
          {(isAuthenticated && !isAdmin) && <Link to="/message">Medelanden</Link>}
          {(isAuthenticated && !isAdmin) && <Link to="/note">Notes</Link> }
          {isPatient && <li><Link to="/journal">Mina Journaler</Link></li>}
          {isStaff && <li><Link to="/journal">Journaler</Link></li>}
          {isPractitioner && <li><Link to="/image">Bilder</Link></li>}
          {isStaff && <li><Link to="/search">Sök Patienter</Link></li>}
          <button onClick={logout}>Logout</button>
        </>
      ) : (
        <>
          <Link to="/login">Logga In</Link>
          <Link to="/register">Registrera</Link>
        </>
      )}
    </nav>
  );
};