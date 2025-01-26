import React, { createContext, useState, useContext, ReactNode } from 'react';
import { UserResponse } from '../types/responses';


interface AuthContextType {
  user: UserResponse | null;
  isAuthenticated: boolean;
  login: (userData: UserResponse) => void;
  logout: () => void;
  isAdmin: boolean
  isPatient: boolean;
  isStaff: boolean;
  isPractitioner: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const [user, setUser] = useState<UserResponse | null>(
    () => JSON.parse(localStorage.getItem('user') || 'null')
  );

  const login = (userData: UserResponse) => {
    setUser(userData);
    localStorage.setItem('user', JSON.stringify(userData));
  };

  const logout = () => {
    setUser(null);
    localStorage.removeItem('user');
  };

  const isAdmin = user?.role === 'ADMIN';
  const isPatient = user?.role === 'PATIENT';
  const isStaff = user?.role === 'PRACTITIONER' || user?.role === 'OTHER';
  const isPractitioner = user?.role === 'PRACTITIONER';

  return (
    <AuthContext.Provider value={{ user, isAuthenticated: !!user, login , logout, isAdmin, isPatient, isStaff, isPractitioner }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};