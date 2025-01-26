import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { Navbar } from '../components';
import { CreateNoteRequest } from '../types/requests';
import { fetchAllPatients, fetchNotesByPatientId, fetchNotesByStaffId, createNote } from '../services/api';
import { UserResponse, NoteResponse } from '../types/responses';
import './css/NotePage.css';

const NotePage: React.FC = () => {
  const { user, isPatient, isStaff } = useAuth();
  const navigate = useNavigate();
  const [patients, setPatients] = useState<UserResponse[]>([]);
  const [error, setError] = useState<string | null>(null);
  const [notes, setNotes] = useState<NoteResponse[]>([]);
  const [expandedPatientId, setExpandedPatientId] = useState<number | null>(null);
  const [noteText, setNoteText] = useState<string>('');
  

  useEffect(() => {
    if (!user) {
      navigate('/login');
      return;
    }
  
    if (isPatient) {
      const loadPatientNotes = async () => {
        try {
          const patientNotes = await fetchNotesByPatientId(user.id);
          setNotes(patientNotes);
        } catch (err) {
          console.error('Error fetching patient notes:', err);
          setError('Kunde inte hämta anteckningar. Försök igen senare.');
        }
      };
  
      loadPatientNotes();
    } else if (isStaff) {
      const loadAllPatients = async () => {
        try {
          const response = await fetchAllPatients();
          setPatients(response);
        } catch (err) {
          console.error('Error fetching patients:', err);
          setError('Kunde inte hämta patienter. Försök igen senare.');
        }
      };
  
      loadAllPatients();
    }
  }, [user, isPatient, isStaff, navigate]);

  return (
    <div>
      <Navbar />
      <h2>Anteckningar</h2>
      {error && <p className="error-message">{error}</p>}
  
      {isPatient && (
        <>
          <h3>Anteckningar om mig.</h3>
          {notes.length === 0 ? (
            <p>Du verkar vara fri från anteckningar.</p>
          ) : (
            notes.map((note) => (
              <div key={note.id}>
                <p>{note.text}</p>
              </div>
            ))
          )}
        </>
      )}
  
      {isStaff && (
        <>
          <h3>Patienter</h3>
          {patients.length === 0 ? (
            <p>Inga patienter tillgängliga.</p>
          ) : (
            <ul className="patient-list">
              {patients.map((patient) => (
                <li key={patient.id} className="patient-item">
                  <div
                    onClick={async () => {
                      if (expandedPatientId === patient.id) {
                        setExpandedPatientId(null);
                          setNotes([]);
                          return;
                      }
                      setExpandedPatientId(patient.id);
                      try {
                        const fetchedNotes = await fetchNotesByPatientId(patient.id);
                        setNotes(fetchedNotes);
                      } catch (err) {
                        console.error('Error fetching notes for patient:', err);
                        setError('Kunde inte hämta anteckningar.');
                      }
                    }}
                  >
                  {patient.fullName}
                  </div>
                {expandedPatientId === patient.id && (
                  <div className="notes-container">
                    {notes.length === 0 ? (
                      <p>Inga anteckningar för denna patient.</p>
                    ) : (
                      notes.map((note) => (
                        <div key={note.id} className="note-item">
                          <p>{note.text}</p>
                        </div>
                      ))
                    )}

                    {}
                    <div>
                      <textarea
                        value={noteText}
                        onChange={(e) => setNoteText(e.target.value)}
                        placeholder="Skriv en ny anteckning..."
                      />
                      <button
                        onClick={async () => {
                          try {
                            const noteRequest: CreateNoteRequest = {
                              // @ts-ignore
                              staffId: user.id,
                              patientId: patient.id,
                              text: noteText
                            };
                            var result = await createNote(noteRequest);
                            setNoteText(''); // töm fältet
                            // Hämta uppdaterad lista direkt
                            const updatedNotes = await fetchNotesByPatientId(patient.id);
                            setNotes(updatedNotes);
                          } catch (err) {
                            console.error('Error creating note:', err);
                            setError('Kunde inte skapa anteckning.');
                          }
                        }}
                      >
                        Spara anteckning
                      </button>
                    </div>
                  </div>
                )}
                </li>
              ))}
            </ul>
          )}
  
          {}
          {}
        </>
      )}
    </div>
  );
};

export default NotePage;