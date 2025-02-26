import { Role } from '../Role';

export interface UserResponse {
    id: string;
    fullName: string;
    role: Role;
}