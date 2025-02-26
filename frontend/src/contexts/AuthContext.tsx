import React, { createContext, useState, useContext, ReactNode } from 'react';
import {jwtDecode} from 'jwt-decode';


interface AuthContextType {
  user: any | null;
  token: string | null;
  isAuthenticated: boolean;
  login: (token: string) => void;
  logout: () => void;
  isAdmin: boolean
  isPatient: boolean;
  isStaff: boolean;
  isPractitioner: boolean,
  userId: string;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider = ({ children }: { children: ReactNode }) => {
  const storedToken = localStorage.getItem('token');
  let decodedUser = null;
  if (storedToken && storedToken !== "null") {
    try {
      decodedUser = jwtDecode(storedToken);
    } catch (error) {
      console.error("Fel vid dekryptering av JWT:", error);
      localStorage.removeItem('token');
    }
  }

  const [token, setToken] = useState<string | null>(localStorage.getItem('token'));
  const [user, setUser] = useState<any | null>(token ? jwtDecode(token) : null);

  const login = (tokenResponse: any) => {
    try {
      const newToken = tokenResponse?.access_token;

      if (!newToken || typeof newToken !== "string") {
        throw new Error("Token är ogiltig eller saknas.");
      }

      const decoded = jwtDecode(newToken);
      localStorage.setItem('token', newToken);
      setToken(newToken);
      setUser(decoded);
    } catch (error) {
      console.error("Felaktig token vid inloggning:", error);
    }
  };

  const logout = () => {
    localStorage.removeItem('token');
    setToken(null);
    setUser(null);
  };

  const roles = user?.realm_access?.roles || [];
  const isAdmin = roles.includes('Admin');
  const isPatient = roles.includes('Patient');
  const isStaff = roles.includes('Practitioner') || roles.includes('Other');
  const isPractitioner = roles.includes('Practitioner');
  const userId = user?.sub;


  return (
    <AuthContext.Provider value={{ user, token, isAuthenticated: !!token, login , logout, isAdmin, isPatient, isStaff, isPractitioner, userId }}>
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