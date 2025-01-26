import React from 'react';
import './UserRoleDisplay.css'; 
import { useAuth } from '../contexts/AuthContext';

export const UserRoleDisplay: React.FC = () => {
  const { user } = useAuth();

  return (
    <div style={{ textAlign: 'center', margin: '20px 0', fontSize: '1.5rem', fontWeight: 'bold' }}>
      {user ? user.role : ''}
    </div>
  );
};