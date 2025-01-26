import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';
import { Navbar } from '../components';
import { fetchAllPatients, fetchEncountersByPatientId, createEncounter, createObservation, createCondition } from '../services/api';
import { EncounterResponse, ObservationResponse, UserResponse } from '../types/responses';
import { CreateConditionRequest, CreateEncounterRequest, CreateObservationRequest } from '../types/requests';
import './css/JournalPage.css';

const JournalPage: React.FC = () => {
  const { user, isPatient, isStaff } = useAuth();
  const navigate = useNavigate();

  const [patients, setPatients] = useState<UserResponse[]>([]);
  const [encounters, setEncounters] = useState<EncounterResponse[]>([]);
  const [selectedPatientId, setSelectedPatientId] = useState<number | null>(null);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (!user) {
      navigate('/login');
    } else if (isPatient) {
      loadPatientEncounters(user.id);
    } else if (isStaff) {
      loadAllPatients();
    }
  }, [user]);

  const loadPatientEncounters = async (patientId: number) => {
    try {
      const data = await fetchEncountersByPatientId(patientId);
      setEncounters(data);
    } catch (err) {
      console.error('Error fetching encounters:', err);
      setError('Kunde inte hämta encounters.');
    }
  };

  const loadAllPatients = async () => {
    try {
      const response = await fetchAllPatients();
      setPatients(response);
    } catch (err) {
      console.error('Error fetching patients:', err);
      setError('Kunde inte hämta patienter. Försök igen senare.');
    }
  };

  const handleCreateObservation = async (encounterId: number, ref: React.RefObject<HTMLTextAreaElement>) => {
    const description = ref.current?.value;
    if (!description) {
      alert('Vänligen skriv en observation.');
      return;
    }

    try {
      const request: CreateObservationRequest = {
        encounterId,
        description,
        visitDate: new Date(),
      };
      const newObservation = await createObservation(request);

      setEncounters((prev) =>
        prev.map((encounter) =>
          encounter.id === encounterId
            ? {
                ...encounter,
                observations: [...encounter.observations, newObservation],
              }
            : encounter
        )
      );

      if (ref.current) {
        ref.current.value = '';
      }
      alert('Observation skapad!');
    } catch (err) {
      console.error('Error creating observation:', err);
      setError('Kunde inte skapa observation.');
    }
  };

  const handleCreateCondition = async (
    observationId: number,
    nameRef: React.RefObject<HTMLInputElement>,
    descriptionRef: React.RefObject<HTMLTextAreaElement>
  ) => {
    const name = nameRef.current?.value;
    const description = descriptionRef.current?.value;

    if (!name || !description) {
      alert('Vänligen fyll i både namn och beskrivning.');
      return;
    }

    try {
      const request: CreateConditionRequest = {
        observationId,
        name,
        description,
      };
      const newCondition = await createCondition(request);

      setEncounters((prev) =>
        prev.map((encounter) => ({
          ...encounter,
          observations: encounter.observations.map((obs) =>
            obs.id === observationId
              ? { ...obs, conditions: [...obs.conditions, newCondition] }
              : obs
          ),
        }))
      );

      if (nameRef.current) nameRef.current.value = ''; 
      if (descriptionRef.current) descriptionRef.current.value = '';
      alert('Condition skapad!');
    } catch (err) {
      console.error('Error creating condition:', err);
      setError('Kunde inte skapa condition.');
    }
  };

  return (
    <div>
      <Navbar />
      <h2>Journal</h2>
      {error && <p>{error}</p>}

      {isPatient && (
        <div>
          <h3>Mina Encounters</h3>
          {encounters.map((encounter) => (
            <div key={encounter.id}>
              <h4>Med {encounter.staff.fullName}</h4>
              <div>
                {encounter.observations.map((obs) => {
                  return (
                    <div key={obs.id}>
                      <p>{obs.description}</p>
                      <ul>
                        {obs.conditions.map((cond) => (
                          <li key={cond.id}>
                            {cond.name}: {cond.description}
                          </li>
                        ))}
                      </ul>
                    </div>
                  );
                })}
              </div>
            </div>
          ))}
        </div>
      )}

      {isStaff && (
        <div>
          <h3>Patienter</h3>
          <div>
            <select
              onChange={async (e) => {
                const patientId = Number(e.target.value);
                setSelectedPatientId(patientId);
                if (patientId) {
                  await loadPatientEncounters(patientId);
                }
              }}
            >
              <option value="">Välj en patient...</option>
              {patients.map((patient) => (
                <option key={patient.id} value={patient.id}>
                  {patient.fullName}
                </option>
              ))}
            </select>
          </div>

          <h3>Encounters</h3>
          <button
            onClick={async () => {
              if (selectedPatientId) {
                try {
                  const request: CreateEncounterRequest = {
                    patientId: selectedPatientId,
                    staffId: user!.id,
                    locationId: null,
                  };
                  const newEncounter = await createEncounter(request);
                  setEncounters((prev) => [...prev, newEncounter]);
                  alert('Encounter skapad!');
                } catch (err) {
                  console.error('Error creating encounter:', err);
                  setError('Kunde inte skapa encounter.');
                }
              }
            }}
          >
            Skapa Encounter
          </button>

          {encounters.map((encounter) => {
            const observationRef = React.createRef<HTMLTextAreaElement>();

            return (
              <div key={encounter.id}>
                <h4>Med {encounter.patient.fullName}</h4>
                <h5>Observationer</h5>
                {encounter.observations.map((obs) => {
                  const conditionNameRef = React.createRef<HTMLInputElement>();
                  const conditionDescriptionRef = React.createRef<HTMLTextAreaElement>();

                  return (
                    <div key={obs.id} className="observation-section">
                        <p>{obs.description}</p>
                        <ul>
                            {obs.conditions.map((cond) => (
                            <li key={cond.id}>
                                {cond.name}: {cond.description}
                            </li>
                            ))}
                        </ul>
                        <div className="condition-form">
                            <input
                            type="text"
                            placeholder="Conditionens namn"
                            ref={conditionNameRef}
                            />
                            <textarea
                            placeholder="Conditionens beskrivning"
                            ref={conditionDescriptionRef}
                            />
                            <button
                            onClick={() =>
                                handleCreateCondition(obs.id, conditionNameRef, conditionDescriptionRef)
                            }
                            >
                            Lägg till Condition
                            </button>
                        </div>
                        </div>
                  );
                })}
                <textarea
                  placeholder="Observationens beskrivning"
                  ref={observationRef}
                />
                <button
                  onClick={() => handleCreateObservation(encounter.id, observationRef)}
                >
                  Lägg till Observation
                </button>
              </div>
            );
          })}
        </div>
      )}
    </div>
  );
};

export default JournalPage;