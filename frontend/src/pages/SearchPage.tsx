import React, { useState, useEffect } from 'react';
import { Navbar } from '../components';
import {
  searchPatientsByName,
  searchPatientsByCondition,
  searchPatientsByStaffName,
  searchPatientsByPractitioner,
  searchPatientsByPractitionerAndDate,
} from '../services/api';
import { fetchAllStaff } from '../services/api';
import { PractitionerAndDateRequest } from '../types/requests';
import { UserResponse } from '../types/responses';
import './css/SearchPage.css';

const SearchPage: React.FC = () => {
  const [selectedTab, setSelectedTab] = useState<'name' | 'practitioner'>('name');
  const [radioOption, setRadioOption] = useState<'patientName' | 'condition' | 'staffName' | null>(null);
  const [searchInput, setSearchInput] = useState('');
  const [results, setResults] = useState<UserResponse[]>([]);
  const [staffList, setStaffList] = useState<UserResponse[]>([]);
  const [selectedPractitionerId, setSelectedPractitionerId] = useState<number | null>(null);
  const [includeDate, setIncludeDate] = useState(false);
  const [selectedDate, setSelectedDate] = useState<string>('');
  const [error, setError] = useState<string | null>(null);
  const [noResultsMessage, setNoResultsMessage] = useState<string | null>(null);

  useEffect(() => {
    if (selectedTab === 'practitioner') {
      loadStaffList();
    }
  }, [selectedTab]);

  const loadStaffList = async () => {
    try {
      const staff = await fetchAllStaff();
      const practitioners = staff.filter((user) => user.role === 'PRACTITIONER');
      setStaffList(practitioners);
    } catch (err) {
      console.error('Error fetching staff:', err);
      setError('Kunde inte hämta personal. Försök igen senare.');
    }
  };

  const handleSearch = async () => {
    setError(null);
    setNoResultsMessage(null);

    try {
      let searchResults: UserResponse[] = [];
      if (selectedTab === 'name' && radioOption) {
        switch (radioOption) {
          case 'patientName':
            searchResults = await searchPatientsByName(searchInput);
            break;
          case 'condition':
            searchResults = await searchPatientsByCondition(searchInput);
            break;
          case 'staffName':
            searchResults = await searchPatientsByStaffName(searchInput);
            break;
        }
      } else if (selectedTab === 'practitioner' && selectedPractitionerId) {
        if (includeDate) {
          const request: PractitionerAndDateRequest = {
            practitionerId: selectedPractitionerId,
            date: new Date(selectedDate),
          };
          searchResults = await searchPatientsByPractitionerAndDate(request);
        } else {
          searchResults = await searchPatientsByPractitioner(selectedPractitionerId.toString());
        }
      }

      if (searchResults.length === 0) {
        setNoResultsMessage('Inga resultat hittades för din sökning.');
      } else {
        setResults(searchResults);
      }
    } catch (err: any) {
      console.error('Error performing search:', err);
      setError(err.message || 'Ett fel inträffade.');
    }
  };

  return (
    <div>
      <Navbar />
      <div className="search-container">
        <div className="tabs">
          <button
            className={selectedTab === 'name' ? 'active' : ''}
            onClick={() => setSelectedTab('name')}
          >
            Sök med namn
          </button>
          <button
            className={selectedTab === 'practitioner' ? 'active' : ''}
            onClick={() => setSelectedTab('practitioner')}
          >
            Sök med läkare
          </button>
        </div>

        {selectedTab === 'name' && (
          <div className="name-tab">
            <div className="radio-buttons">
              <label>
                <input
                  type="radio"
                  name="searchType"
                  value="patientName"
                  checked={radioOption === 'patientName'}
                  onChange={() => setRadioOption('patientName')}
                />
                Patient Namn
              </label>
              <label>
                <input
                  type="radio"
                  name="searchType"
                  value="condition"
                  checked={radioOption === 'condition'}
                  onChange={() => setRadioOption('condition')}
                />
                Condition
              </label>
              <label>
                <input
                  type="radio"
                  name="searchType"
                  value="staffName"
                  checked={radioOption === 'staffName'}
                  onChange={() => setRadioOption('staffName')}
                />
                Personal Namn
              </label>
            </div>
            <input
              type="text"
              placeholder="Skriv sökterm..."
              value={searchInput}
              onChange={(e) => setSearchInput(e.target.value)}
              disabled={!radioOption}
            />
            <button onClick={handleSearch} disabled={!radioOption}>
              Sök
            </button>
          </div>
        )}

        {selectedTab === 'practitioner' && (
          <div className="practitioner-tab">
            <select
              onChange={(e) => setSelectedPractitionerId(Number(e.target.value))}
            >
              <option value="">Välj en läkare...</option>
              {staffList.map((staff) => (
                <option key={staff.id} value={staff.id}>
                  {staff.fullName}
                </option>
              ))}
            </select>
            <div className="date-checkbox">
              <label>
                <input
                  type="checkbox"
                  checked={includeDate}
                  onChange={(e) => setIncludeDate(e.target.checked)}
                />
                Inkludera datum
              </label>
              {includeDate && (
                <input
                  type="date"
                  value={selectedDate}
                  onChange={(e) => setSelectedDate(e.target.value)}
                />
              )}
            </div>
            <button onClick={handleSearch} disabled={!selectedPractitionerId}>
              Sök
            </button>
          </div>
        )}

        <div className="results">
          {error && <p className="error">{error}</p>}
          {noResultsMessage && <p className="no-results">{noResultsMessage}</p>} {/* Lägg till visning av noResultsMessage */}
          <ul>
            {results.map((result) => (
              <li key={result.id}>{result.fullName}</li>
            ))}
          </ul>
        </div>
      </div>
    </div>
  );
};

export default SearchPage;