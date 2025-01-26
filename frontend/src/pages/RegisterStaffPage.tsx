import React, { useState, useEffect } from 'react';
import { useAuth } from '../contexts/AuthContext';
import { Navbar } from '../components';
import { registerUser, fetchOrganizations } from '../services/api';
import './css/RegisterStaffPage.css';
import { CreateUserRequest } from '../types/requests';
import { OrganizationResponse } from '../types/responses';

const RegisterStaffPage: React.FC = () => {
  const [firstName, setFirstName] = useState<string>('');
  const [lastName, setLastName] = useState<string>('');
  const [email, setEmail] = useState<string>('');
  const [username, setUsername] = useState<string>('');
  const [password, setPassword] = useState<string>('');
  const [roleId, setRoleId] = useState<number>(2);
  const [organizationId, setOrganizationId] = useState<number | null>(null);
  const [organizations, setOrganizations] = useState<OrganizationResponse[]>([]);
  const [success, setSuccess] = useState<string | null>(null);
  const [error, setError] = useState<string | null>(null);

  const { user } = useAuth();

  useEffect(() => {
    const loadOrganizations = async () => {
      try {
        const data = await fetchOrganizations();
        setOrganizations(data);
      } catch (err) {
        console.error('Error fetching organizations:', err);
      }
    };

    loadOrganizations();
  }, []);

  const roles = [
    { id: 2, name: 'Practitioner' },
    { id: 3, name: 'Other' },
  ];

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError(null);
    setSuccess(null);

    try {
      const userData: CreateUserRequest = {
        firstName,
        lastName,
        email,
        username,
        password,
        roleId,
        organizationId,
      };

      const response = await registerUser(userData);
      setSuccess('Användaren skapades framgångsrikt!');
    } catch (err) {
      setError('Ett fel uppstod vid registreringen.');
      console.error(err);
    }
  };

  
  if (!user || user.role !== 'ADMIN') {
    return (
      <div>
        <Navbar />
        <h2>Åtkomst nekad</h2>
        <p>Du har inte behörighet att se den här sidan.</p>
      </div>
    );
  }

  return (
    <div>
      <Navbar />
      <h2>Registrera Personal</h2>
      <form onSubmit={handleSubmit} className="register-staff-form">
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
        <div>
          <label>Roll:</label>
          <select value={roleId} onChange={(e) => setRoleId(Number(e.target.value))}>
            {roles.map((role) => (
              <option key={role.id} value={role.id}>
                {role.name}
              </option>
            ))}
          </select>
        </div>
        <div>
          <label>Organisation:</label>
          <select
            value={organizationId || ''}
            onChange={(e) => setOrganizationId(Number(e.target.value))}
          >
            <option value="">Ingen organisation</option>
            {organizations.map((org) => (
              <option key={org.id} value={org.id}>
                {org.name}
              </option>
            ))}
          </select>
        </div>
        <button type="submit">Registrera</button>
        {success && <p className="success-message">{success}</p>}
        {error && <p className="error-message">{error}</p>}
      </form>
    </div>
  );
};

export default RegisterStaffPage;