export interface CreateUserRequest {
    firstName: string;
    lastName: string;
    username: string;
    password: string;
    email: string;
    roleId: number;
    organizationId?: number | null;
}