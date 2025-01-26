import { Role } from '../Role';

export interface UserResponse {
    id: number;
    fullName: string;
    role: Role;
}