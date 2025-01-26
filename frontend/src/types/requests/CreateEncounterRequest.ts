export interface CreateEncounterRequest {
    patientId : number;
    staffId: number;
    locationId: number | null;
}