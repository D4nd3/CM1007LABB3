import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Navbar } from '../components';
import { loginUser } from '../services/api';
import { useAuth } from '../contexts/AuthContext';
import { LoginRequest } from '../types/requests'
import { UserResponse } from '../types/responses';

const LoginPage: React.FC = () => {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState<string | null>(null);
  const navigate = useNavigate();
  const { login } = useAuth();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    setError(null);

    const loginRequest: LoginRequest = {
      email,
      password,
    };

    try {
      const response: UserResponse = await loginUser(loginRequest);
      login(response);
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
          <label>Email:</label>
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
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