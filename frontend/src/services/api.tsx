import { CreateNoteRequest, CreateUserRequest, LoginRequest, SendMessageRequest, CreateConditionRequest, CreateObservationRequest, CreateEncounterRequest, PractitionerAndDateRequest } from '../types/requests';
import { UserResponse, SendMessageResponse, NoteResponse, LocationResponse, ConditionResponse, ObservationResponse, EncounterResponse, OrganizationResponse } from '../types/responses';

const API_USER_URL = '/api-user';
const API_NOTE_URL = '/api-note';
const API_MESSAGE_URL = '/api-message';
const API_ENCOUNTER_URL = '/api-encounter';
const API_IMAGE_URL = '/api-image'
const API_SEARCH_URL = '/api-search'

type HttpMethod = 'GET' | 'POST' | 'PUT' | 'DELETE';
interface ApiResponse<T> {
  success?: boolean;
  data?: T;
  message?: string;
}

//#region BACKEND-USER

export const registerUser = async (userData: CreateUserRequest): Promise<UserResponse> => {
  const response = await fetch(`${API_USER_URL}/users/register`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(userData),
  });

  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message || 'Något gick fel vid registreringen.');
  }

  return await response.json();
};

export const loginUser = async (userData: LoginRequest): Promise<UserResponse> => {
  const response = await fetch(`${API_USER_URL}/users/login`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(userData),
  });

  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message || 'Inloggningen misslyckades.');
  }

  return await response.json();
};

export const fetchOrganizations = async (): Promise<OrganizationResponse[]> => {
  const response = await fetch(`${API_USER_URL}/organizations`, {
      method: 'GET',
      headers: {
          'Content-Type': 'application/json',
      },
  });

  if (!response.ok) {
      const errorData = await response.json();
      throw new Error(errorData.message || 'Kunde inte hämta organisationer.');
  }

  return await response.json();
};

export const fetchAllStaff = async (): Promise<UserResponse[]> => {
  const response = await fetch(`${API_USER_URL}/users/allStaff`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  });

  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message || 'Kunde inte hämta personal.');
  }

  return await response.json();
};

export const fetchAllUsers = async (): Promise<UserResponse[]> => {
  const response = await fetch(`${API_USER_URL}/users/all`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  });

  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message || 'Kunde inte hämta personal.');
  }

  return await response.json();
};

export const fetchAllPatients = async (): Promise<UserResponse[]> =>{
  const response = await fetch(`${API_USER_URL }/patients/all`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  });

  if (!response.ok){
    const errorData = await response.json();
    throw new Error(errorData.message || 'Kinde inte hämta patienter');
  }

  return await response.json();
}

//#endregion

//#region BACKEND-MESSAGE

export const getMessages = async (userData: number): Promise<SendMessageResponse[]> => {
  const response = await fetch(`${API_MESSAGE_URL}/messages?userId=${userData}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json'
    }
  });

  if (!response.ok){
    const errorData = await response.json();
    throw new Error(errorData.message || 'Kunde inte hämta medelanden.')
  }

  return await response.json();
}

export const sendMessage = async (userData: SendMessageRequest): Promise<SendMessageResponse> => {
  const response = await fetch(`${API_MESSAGE_URL}/messages/send`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(userData),
  });

  if (!response.ok){
    const errorData = await response.json();
    throw new Error(errorData.message || 'Kunde inte hämta medelanden.')
  }

  return await response.json();
}

export const updateIsRead = async (userData: number): Promise<SendMessageResponse> => {
  const response = await fetch(`${API_MESSAGE_URL}/messages/updateIsRead?id=${userData}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json'
    }
  });

  if (!response.ok){
    const errorData = await response.json();
    throw new Error(errorData.message || 'Kunde inte hämta medelanden.')
  }

  return await response.json();
}

//#endregion

//#region BACKEND-NOTE

export const fetchNotesByStaffId = async (userData: number): Promise<NoteResponse[]> => {
  const response = await fetch(`${API_NOTE_URL}/notes/byStaffId?id=${userData}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json'
    }
  });

  if (!response.ok){
    const errorData = await response.json();
    throw new Error(errorData.message || 'Kunde inte hämta medelanden.')
  }

  return await response.json();
}

export const fetchNotesByPatientId = async (userData: number): Promise<NoteResponse[]> => {
  const response = await fetch(`${API_NOTE_URL}/notes/byPatientId?id=${userData}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json'
    }
  });

  if (!response.ok){
    const errorData = await response.json();
    throw new Error(errorData.message || 'Kunde inte hämta medelanden.')
  }

  return await response.json();
}

export const createNote = async(userData: CreateNoteRequest): Promise<NoteResponse> => {
  const response = await fetch(`${API_NOTE_URL}/notes/create`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(userData)
  });
  
  if (!response.ok){
    const errorData = await response.json();
    throw new Error(errorData.message || 'Kunde inte hämta medelanden.')
  }

  return await response.json()
}

//#endregion

//#region BACKEND-CONDITION

export const createCondition = async(userData: CreateConditionRequest): Promise<ConditionResponse> => {
  const response = await fetch(`${API_ENCOUNTER_URL}/encounters/createCondition`, {
    method: 'POST',
    headers: {
      'Content-Type' : 'application/json'
    },
    body: JSON.stringify(userData)
  });

  if (!response.ok){
    const errorData = await response.json();
    throw new Error(errorData.message || 'Kunde inte hämta medelanden.')
  }

  return await response.json()
}

export const createObservation = async(userData: CreateObservationRequest): Promise<ObservationResponse> => {
  const response = await fetch(`${API_ENCOUNTER_URL}/encounters/createObservation`, {
    method: 'POST',
    headers: {
      'Content-Type' : 'application/json'
    },
    body: JSON.stringify(userData)
  });

  if (!response.ok){
    const errorData = await response.json();
    throw new Error(errorData.message || 'Kunde inte hämta medelanden.')
  }

  return await response.json()
}

export const createEncounter = async(userData: CreateEncounterRequest): Promise<EncounterResponse> => {
  const response = await fetch(`${API_ENCOUNTER_URL}/encounters/createEncounter`, {
    method: 'POST',
    headers: {
      'Content-Type' : 'application/json'
    },
    body: JSON.stringify(userData)
  });

  if (!response.ok){
    const errorData = await response.json();
    throw new Error(errorData.message || 'Kunde inte hämta medelanden.')
  }

  return await response.json()
}

export const fetchEncountersByStaffId = async(userData: number): Promise<EncounterResponse[]> => {
  const response = await fetch(`${API_ENCOUNTER_URL}/encounters/byStaffId?id=${userData}`, {
    method: 'GET',
    headers: {
      'Content-Type' : 'application/json'
    }
  });

  if (!response.ok){
    const errorData = await response.json();
    throw new Error(errorData.message || 'Kunde inte hämta medelanden.')
  }

  return await response.json()
}

export const fetchEncountersByPatientId = async(userData: number): Promise<EncounterResponse[]> => {
  const response = await fetch(`${API_ENCOUNTER_URL}/encounters/byPatientId?id=${userData}`, {
    method: 'GET',
    headers: {
      'Content-Type' : 'application/json'
    }
  });

  if (!response.ok){
    const errorData = await response.json();
    throw new Error(errorData.message || 'Kunde inte hämta medelanden.')
  }

  return await response.json()
}

//#endregion

//#region BACKEND-IMAGE

export const uploadImage = async (formData: FormData): Promise<any> => {
  const response = await fetch(`${API_IMAGE_URL}/api/upload`, {
    method: 'POST',
    body: formData
    // Observera att Content-Type inte sätts manuellt vid filuppladdning med FormData
  });

  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message || 'Misslyckades med att ladda upp bilden.');
  }

  return await response.json();
};

export const fetchImage = async (filename: string): Promise<Blob> => {
  const response = await fetch(`${API_IMAGE_URL}/api/upload/${filename}`, {
    method: 'GET',
  });

  if (!response.ok) {
    throw new Error('Kunde inte hämta bilden.');
  }

  return await response.blob();
};

export const fetchAllImages = async (): Promise<string[]> => {
  const response = await fetch(`${API_IMAGE_URL}/api/upload/all`, {
    method: 'GET',
  });

  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message || 'Kunde inte hämta bildnamnen.');
  }

  var data = await response.json();
  return data.files;
};

//#endregion

//#region BACKEND-SEARCH

export const searchPatientsByName = async (userData: string): Promise<UserResponse[]> => {
  const response = await fetch(`${API_SEARCH_URL}/patients/searchName?name=${userData}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    }
  });

  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message || 'Något gick fel vid anrop av BACKEND-SEARCH.');
  }

  return await response.json();
};

export const searchPatientsByCondition = async (userData: string): Promise<UserResponse[]> => {
  const response = await fetch(`${API_SEARCH_URL}/patients/searchCondition?condition=${userData}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    }
  });

  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message || 'Något gick fel vid anrop av BACKEND-SEARCH.');
  }

  return await response.json();
};

export const searchPatientsByStaffName = async (userData: string): Promise<UserResponse[]> => {
  const response = await fetch(`${API_SEARCH_URL}/patients/searchStaff?name=${userData}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    }
  });

  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message || 'Något gick fel vid anrop av BACKEND-SEARCH.');
  }

  return await response.json();
};

export const searchPatientsByPractitioner = async (userData: string): Promise<UserResponse[]> => {
  const response = await fetch(`${API_SEARCH_URL}/practitioners/search?id=${userData}`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    }
  });

  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message || 'Något gick fel vid anrop av BACKEND-SEARCH.');
  }

  return await response.json();
};

export const searchPatientsByPractitionerAndDate = async (userData: PractitionerAndDateRequest): Promise<UserResponse[]> => {
  const response = await fetch(`${API_SEARCH_URL}/practitioners/searchPatientsByDay`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(userData)
  });

  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message || 'Något gick fel vid anrop av BACKEND-SEARCH.');
  }

  return await response.json();
};

//#endregion