export interface KeycloakLoginRequest {
  username: string;
  password: string;
  client_id: string;
  grant_type: string;
  // client_secret: string;
}