import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Navbar } from '../components';
import { loginWithKeycloak } from '../services/api';
import { useAuth } from '../contexts/AuthContext';
import { UserResponse } from '../types/responses';
import Keycloak from "keycloak-js";
import jwtDecode from "jwt-decode";



const LoginPage: React.FC = () => {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();
  const { login } = useAuth();


  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);

    

    try {
      const tokenResponse = await loginWithKeycloak({
        username: username,
        password: password,
        client_id: 'frontend',
        grant_type: 'password',
        // client_secret: 'QbZGSI719PEFeeuJNMyELUUDpBq17bMo',
      });
      login(tokenResponse); 
      navigate('/');
    } catch (err) {
      setError('Inloggningen misslyckades. Försök igen.');
    }
  };

  return (
    <div>
      <Navbar />
      <h2>Logga In</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Användarnamn:</label>
          <input
            type="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Lösenord:</label>
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
          />
        </div>
        <button type="submit">Logga In</button>
        {error && <p style={{ color: 'red' }}>{error}</p>}
      </form>
    </div>
  );
}

export default LoginPage;