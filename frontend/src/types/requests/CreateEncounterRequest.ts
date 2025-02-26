export interface CreateEncounterRequest {
    patientId : string;
    staffId: string;
    locationId: number | null;
}