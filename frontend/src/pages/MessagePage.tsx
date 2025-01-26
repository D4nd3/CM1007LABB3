import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Navbar } from '../components';
import { useAuth } from '../contexts/AuthContext';
import { fetchAllStaff, fetchAllUsers, sendMessage, getMessages, updateIsRead } from '../services/api';
import { UserResponse } from '../types/responses/UserResponse';
import { SendMessageRequest } from '../types/requests/SendMessageRequest';
import { SendMessageResponse } from '../types/responses/SendMessageResponse';
import './css/MessagePage.css';

const MessagePage: React.FC = () => {
  const { user } = useAuth();
  const navigate = useNavigate();
  const [staffList, setStaffList] = useState<UserResponse[]>([]);
  const [usersList, setUsersList] = useState<UserResponse[]>([]);
  const [selectedStaff, setSelectedStaff] = useState<number | null>(null);
  const [messageText, setMessageText] = useState<string>('');
  const [error, setError] = useState<string | null>(null);
  const [successMessage, setSuccessMessage] = useState<string | null>(null);
  const [messages, setMessages] = useState<SendMessageResponse[]>([]);
  const [expandedMessageId, setExpandedMessageId] = useState<number | null>(null);

  useEffect(() => {
    if(!user){
        navigate('/login'); 
      return;
    }
    const loadData = async () => {
        try {
        if (user.role === 'PATIENT') {
            const staffResponse = await fetchAllStaff();
            setStaffList(staffResponse);
        } else if (user.role === 'PRACTITIONER' || user.role === 'OTHER') {
            const usersResponse = await fetchAllUsers();
            setUsersList(usersResponse);
        }
    
        const messageResponse = await getMessages(user.id);
        setMessages(messageResponse);
        } catch (err) {
        console.error('Error loading data:', err);
        setError('Kunde inte ladda data. Försök igen senare.');
        }
    };
    loadData();
  }, [user]);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    setError(null);
    setSuccessMessage(null);

    if (!selectedStaff || !messageText) {
      setError('Vänligen välj en person och skriv ett meddelande.');
      return;
    }

    if (!user) {
      setError('Inloggad användare saknas. Logga in igen.');
      return;
    }

    const messageRequest: SendMessageRequest = {
      senderId: user.id,
      receiverId: selectedStaff,
      text: messageText
    };

    try {
      const response = await sendMessage(messageRequest);
      setSuccessMessage('Meddelandet skickades framgångsrikt!');
      setMessageText('');
      setSelectedStaff(null);
      const updatedMessages = await getMessages(user.id);
      setMessages(updatedMessages);
    } catch (err) {
      console.error('Error sending message:', err);
      setError('Kunde inte skicka meddelandet. Försök igen senare.');
    }
  };

  const handleExpandMessage = async (msg: SendMessageResponse) => {
    if (msg.receiverId === user?.id && !msg.isRead) {
      try {
        await updateIsRead(msg.id);
        const updatedMessages = messages.map((message) =>
          message.id === msg.id ? { ...message, isRead: true } : message
        );
        setMessages(updatedMessages);
      } catch (err) {
        console.error('Error updating read status:', err);
        setError('Kunde inte uppdatera meddelandets lässtatus.');
      }
    }
    setExpandedMessageId(expandedMessageId === msg.id ? null : msg.id);
  };

  return (
    <div>
      <Navbar />
      <h2>Skicka Meddelande</h2>
      {error && <p className="error-message">{error}</p>}
      {successMessage && <p className="success-message">{successMessage}</p>}

      <form onSubmit={handleSubmit}>
        <div>
          <label>Välj Personal:</label>
          <select
            value={selectedStaff || ''}
            onChange={(e) => setSelectedStaff(Number(e.target.value))}
          >
            <option value="" disabled>
              Välj en person...
            </option>
            {(user?.role === 'PATIENT' ? staffList : usersList).map((person) => (
            <option key={person.id} value={person.id}>
                {person.fullName} ({person.role})
            </option>
            ))}
          </select>
        </div>
        <div>
          <label>Meddelande:</label>
          <textarea
            placeholder="Skriv ditt meddelande här..."
            value={messageText}
            onChange={(e) => setMessageText(e.target.value)}
            required
          />
        </div>
        <button type="submit">Skicka</button>
      </form>

      <h3>Befintliga Meddelanden</h3>
      {messages.length === 0 ? (
        <p>Inga meddelanden.</p>
      ) : (
        <ul className="message-list">
          {messages.map((msg) => (
            <li key={msg.id}>
              <div onClick={() => handleExpandMessage(msg)}>
                <strong>
                  {msg.senderId === user?.id ? 'Du skickade till' : `${msg.senderName} skickade till`}:{' '}
                  {msg.receiverId === user?.id ? 'dig' : `${msg.receiverName}`}
                </strong>
                {!msg.isRead && (
                  <span className={`read-status ${msg.senderId === user?.id ? 'red' : 'green'}`}></span>
                )}
              </div>
              {expandedMessageId === msg.id && (
                <div>
                  <p>{msg.text}</p>
                  {msg.timestamp
                ? new Date(Number(msg.timestamp)).toLocaleString() 
                    : 'Tidsstämpel saknas'}
                </div>
              )}
            </li>
          ))}
        </ul>
      )}
    </div>
  );
};

export default MessagePage;