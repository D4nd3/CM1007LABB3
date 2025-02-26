import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Navbar } from '../components';
import { loginWithKeycloak,registerUser } from '../services/api';
import { CreateUserRequest, KeycloakLoginRequest } from '../types/requests';
import { UserResponse} from '../types/responses'
import { useAuth } from '../contexts/AuthContext';
import './css/RegisterPage.css';



const RegisterPage: React.FC = () => {
  const [firstName, setFirstName] = useState<string>('');
  const [lastName, setLastName] = useState<string>('');
  const [email, setEmail] = useState<string>('');
  const [username, setUsername] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState<string | null>(null);
  const navigate = useNavigate();
  const { login } = useAuth();

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError(null);
    setSuccess(null);

    const userRequest: CreateUserRequest = {
      firstName,
      lastName,
      email,
      username,
      password,
      roleId: 1,
    };

    try {
      await registerUser(userRequest);
      const keycloakRequest: KeycloakLoginRequest = {
        username: username,
        password: password,
        client_id: 'postman',
        grant_type: 'password',
        // client_secret: 'cNKdFVrU1OCqLbDZSxWrsvDyTg3pmZrC', 
      };

      const tokenResponse = await loginWithKeycloak(keycloakRequest);
      login(tokenResponse);
      navigate('/');
    } catch (err) {
      setError('Registreringen misslyckades. Försök igen.');
      console.error(err);
    }
  };


  return (
    <div>
      <Navbar/>
      <h2>Skapa Användare</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>Förnamn:</label>
          <input
            type="text"
            value={firstName}
            onChange={(e) => setFirstName(e.target.value)}
            required
          />
        </div>
        <div>
          <label>Efternamn:</label>
          <input
            type="text"
            value={lastName}
            onChange={(e) => setLastName(e.target.value)}
            required
          />
        </div>
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
          <label>Användarnamn:</label>
          <input
            type="text"
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
        <button type="submit">Registrera</button>
      </form>
    </div>
  );
}

export default RegisterPage;