import { UserResponse, ObservationResponse, LocationResponse } from '../responses';

export interface EncounterResponse {
  id: number;
  patient: UserResponse;
  staff: UserResponse;
  location: LocationResponse;
  observations: ObservationResponse[];
}