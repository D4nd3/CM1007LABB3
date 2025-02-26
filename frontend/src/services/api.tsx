import { CreateNoteRequest, CreateUserRequest, LoginRequest, SendMessageRequest, CreateConditionRequest, CreateObservationRequest, CreateEncounterRequest, PractitionerAndDateRequest, KeycloakLoginRequest } from '../types/requests';
import { UserResponse, SendMessageResponse, NoteResponse, LocationResponse, ConditionResponse, ObservationResponse, EncounterResponse, OrganizationResponse } from '../types/responses';
import {useAuth} from '../contexts/AuthContext';

const API_USER_URL = '/api-user';
const API_NOTE_URL = '/api-note';
const API_MESSAGE_URL = '/api-message';
const API_ENCOUNTER_URL = '/api-encounter';
const API_IMAGE_URL = '/api-image'
const API_SEARCH_URL = '/api-search'
const KEYCLOAK_URL = '/keycloak/realms/master/protocol/openid-connect/token';

type HttpMethod = 'GET' | 'POST' | 'PUT' | 'DELETE';
interface ApiResponse<T> {
  success?: boolean;
  data?: T;
  message?: string;
}

const fetchWithAuth = async (url: string, method: HttpMethod, token?: string ,body?: any, contentType: 'json' | 'form-data' = 'json') => {

  const headers: HeadersInit = {};

 

  if (token) {
    headers['Authorization'] = `Bearer ${token}`;
  }

  let formattedBody: any;

  if (contentType === 'json') {
    headers['Content-Type'] = 'application/json';
    formattedBody = body ? JSON.stringify(body) : undefined;
  } else if (contentType === 'form-data') {
    formattedBody = body;
  }

  console.log("🔵 FETCH REQUEST:", {
    url,
    method,
    headers,
    body: formattedBody
  });

  var tmp = await fetch(url, {
    method,
    headers,
    body: formattedBody
  });

  console.log(tmp);

  return tmp;
};

//#region BACKEND-USER

export const registerUser = async (userData: CreateUserRequest, token?: string): Promise<UserResponse> => {
  console.log(userData);
  const response = await fetchWithAuth(`${API_USER_URL}/users/register`, 'POST', token ,userData);
  console.log(response);
  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message || 'Något gick fel vid registreringen.');
  }

  return await response.json();
};

export const fetchOrganizations = async (token: string): Promise<OrganizationResponse[]> => {
  const response = await fetchWithAuth(`${API_USER_URL}/organizations`,'GET',token);

  if (!response.ok) {
      const errorData = await response.json();
      throw new Error(errorData.message || 'Kunde inte hämta organisationer.');
  }

  return await response.json();
};

export const fetchAllStaff = async (token: string): Promise<UserResponse[]> => {
  console.log("requesting all staff");
  const response = await fetchWithAuth(`${API_USER_URL}/users/allStaff`,'GET', token);
  console.log("response from all staff");

  console.log(response);
  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message || 'Kunde inte hämta personal.');
  }

  return await response.json();
};

export const fetchAllUsers = async (token: string): Promise<UserResponse[]> => {
  const response = await fetchWithAuth(`${API_USER_URL}/users/all`,'GET', token);

  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message || 'Kunde inte hämta personal.');
  }

  return await response.json();
};

export const fetchAllPatients = async (token: string): Promise<UserResponse[]> =>{
  const response = await fetchWithAuth(`${API_USER_URL }/patients/all`,'GET', token);

  if (!response.ok){
    const errorData = await response.json();
    throw new Error(errorData.message || 'Kinde inte hämta patienter');
  }

  return await response.json();
}

//#endregion

//#region BACKEND-MESSAGE

export const getMessages = async (userData: string, token: string): Promise<SendMessageResponse[]> => {
  const response = await fetchWithAuth(`${API_MESSAGE_URL}/messages?userId=${userData}`,'GET',token); 

  if (!response.ok){
    const errorData = await response.json();
    throw new Error(errorData.message || 'Kunde inte hämta medelanden.')
  }

  return await response.json();
}

export const sendMessage = async (userData: SendMessageRequest, token: string): Promise<SendMessageResponse> => {
  const response = await fetchWithAuth(`${API_MESSAGE_URL}/messages/send`,'POST',token,userData);

  if (!response.ok){
    const errorData = await response.json();
    throw new Error(errorData.message || 'Kunde inte hämta medelanden.')
  }

  return await response.json();
}

export const updateIsRead = async (userData: number, token: string): Promise<SendMessageResponse> => {
  const response = await fetchWithAuth(`${API_MESSAGE_URL}/messages/updateIsRead?id=${userData}`,'PUT',token);

  if (!response.ok){
    const errorData = await response.json();
    throw new Error(errorData.message || 'Kunde inte hämta medelanden.')
  }

  return await response.json();
}

//#endregion

//#region BACKEND-NOTE

export const fetchNotesByStaffId = async (userData: number, token: string): Promise<NoteResponse[]> => {
  const response = await fetchWithAuth(`${API_NOTE_URL}/notes/byStaffId?id=${userData}`,'GET',token);

  if (!response.ok){
    const errorData = await response.json();
    throw new Error(errorData.message || 'Kunde inte hämta medelanden.')
  }

  return await response.json();
}

export const fetchNotesByPatientId = async (userData: string, token: string): Promise<NoteResponse[]> => {
  const response = await fetchWithAuth(`${API_NOTE_URL}/notes/byPatientId?id=${userData}`,'GET',token);

  if (!response.ok){
    const errorData = await response.json();
    throw new Error(errorData.message || 'Kunde inte hämta medelanden.')
  }

  return await response.json();
}

export const createNote = async(userData: CreateNoteRequest, token: string): Promise<NoteResponse> => {
  const response = await fetchWithAuth(`${API_NOTE_URL}/notes/create`,'POST',token, userData);
  
  if (!response.ok){
    const errorData = await response.json();
    throw new Error(errorData.message || 'Kunde inte hämta medelanden.')
  }

  return await response.json()
}

//#endregion

//#region BACKEND-CONDITION

export const createCondition = async(userData: CreateConditionRequest, token: string): Promise<ConditionResponse> => {
  const response = await fetchWithAuth(`${API_ENCOUNTER_URL}/encounters/createCondition`,'POST',token,userData);

  if (!response.ok){
    const errorData = await response.json();
    throw new Error(errorData.message || 'Kunde inte hämta medelanden.')
  }

  return await response.json()
}

export const createObservation = async(userData: CreateObservationRequest, token: string): Promise<ObservationResponse> => {
  const response = await fetchWithAuth(`${API_ENCOUNTER_URL}/encounters/createObservation`,'POST',token,userData);

  if (!response.ok){
    const errorData = await response.json();
    throw new Error(errorData.message || 'Kunde inte hämta medelanden.')
  }

  return await response.json()
}

export const createEncounter = async(userData: CreateEncounterRequest, token: string): Promise<EncounterResponse> => {
  const response = await fetchWithAuth(`${API_ENCOUNTER_URL}/encounters/createEncounter`,'POST',token,userData);

  if (!response.ok){
    const errorData = await response.json();
    throw new Error(errorData.message || 'Kunde inte hämta medelanden.')
  }

  return await response.json()
}

export const fetchEncountersByStaffId = async(userData: string, token: string): Promise<EncounterResponse[]> => {
  const response = await fetchWithAuth(`${API_ENCOUNTER_URL}/encounters/byStaffId?id=${userData}`,'GET',token);

  if (!response.ok){
    const errorData = await response.json();
    throw new Error(errorData.message || 'Kunde inte hämta medelanden.')
  }

  return await response.json()
}

export const fetchEncountersByPatientId = async(userData: string, token: string): Promise<EncounterResponse[]> => {
  const response = await fetchWithAuth(`${API_ENCOUNTER_URL}/encounters/byPatientId?id=${userData}`,'GET',token);

  if (!response.ok){
    const errorData = await response.json();
    throw new Error(errorData.message || 'Kunde inte hämta medelanden.')
  }

  return await response.json()
}

//#endregion

//#region BACKEND-IMAGE

export const uploadImage = async (formData: FormData, token:string): Promise<any> => {
  const response = await fetchWithAuth(`${API_IMAGE_URL}/api/upload`, 'POST',token, formData,'form-data' );

  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message || 'Misslyckades med att ladda upp bilden.');
  }

  return await response.json();
};

export const fetchImage = async (filename: string, token:string): Promise<Blob> => {
  const response = await fetchWithAuth(`${API_IMAGE_URL}/api/upload/${filename}`, 'GET', token );

  if (!response.ok) {
    throw new Error('Kunde inte hämta bilden.');
  }

  return await response.blob();
};

export const fetchAllImages = async (token:string): Promise<string[]> => {
  const response = await fetchWithAuth(`${API_IMAGE_URL}/api/upload/all`,'GET',token);

  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message || 'Kunde inte hämta bildnamnen.');
  }

  var data = await response.json();
  return data.files;
};

//#endregion

//#region BACKEND-SEARCH

export const searchPatientsByName = async (userData: string, token: string): Promise<UserResponse[]> => {
  const response = await fetchWithAuth(`${API_SEARCH_URL}/patients/searchName?name=${userData}`,'GET',token)

  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message || 'Något gick fel vid anrop av BACKEND-SEARCH.');
  }

  return await response.json();
};

export const searchPatientsByCondition = async (userData: string,token: string): Promise<UserResponse[]> => {
  const response = await fetchWithAuth(`${API_SEARCH_URL}/patients/searchCondition?condition=${userData}`,'GET',token)

  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message || 'Något gick fel vid anrop av BACKEND-SEARCH.');
  }

  return await response.json();
};

export const searchPatientsByStaffName = async (userData: string, token:string): Promise<UserResponse[]> => {
  const response = await fetchWithAuth(`${API_SEARCH_URL}/patients/searchStaff?name=${userData}`,'GET',token)

  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message || 'Något gick fel vid anrop av BACKEND-SEARCH.');
  }

  return await response.json();
};

export const searchPatientsByPractitioner = async (userData: string, token:string): Promise<UserResponse[]> => {
  const response = await fetchWithAuth(`${API_SEARCH_URL}/practitioners/search?id=${userData}`,'GET',token)

  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message || 'Något gick fel vid anrop av BACKEND-SEARCH.');
  }

  return await response.json();
};

export const searchPatientsByPractitionerAndDate = async (userData: PractitionerAndDateRequest,token:string): Promise<UserResponse[]> => {
  const response = await fetchWithAuth(`${API_SEARCH_URL}/practitioners/searchPatientsByDay`,'GET',token)

  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message || 'Något gick fel vid anrop av BACKEND-SEARCH.');
  }

  return await response.json();
};

//#endregion

//#region KEYCLOAK

export const loginWithKeycloak = async (loginData: KeycloakLoginRequest): Promise<any> => {

  console.log(loginData);
  const response = await fetch(KEYCLOAK_URL, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded',
    },
    body: new URLSearchParams({
      username: loginData.username,
      password: loginData.password,
      client_id: loginData.client_id,
      grant_type: loginData.grant_type,
      // client_secret: loginData.client_secret,
    }),
  });

  console.log(response);

  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.error_description || 'Inloggningen misslyckades.');
  }

  return await response.json();
};


//#endregion