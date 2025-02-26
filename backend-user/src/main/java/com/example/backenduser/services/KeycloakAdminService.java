package com.example.backenduser.services;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.example.backenduser.dto.requests.users.CreateUserRequest;
import com.example.backenduser.util.*;

import jakarta.ws.rs.core.Response;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class KeycloakAdminService {

    @Value("${keycloak.auth-server-url}")
    private String keycloakUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.client-id}")
    private String clientId;

    @Value("${keycloak.client-secret}")
    private String clientSecret;

    private Keycloak getKeycloakInstance() {
        return KeycloakBuilder.builder()
                .serverUrl(keycloakUrl)
                .realm(realm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
    }

    public boolean isUserExists(String email, String username) {
      Keycloak keycloak = getKeycloakInstance();
      RealmResource realmResource = keycloak.realm(realm);
      UsersResource usersResource = realmResource.users();

      
      List<UserRepresentation> usersByEmail = usersResource.search(null, null, email, null, 0, 1);
      if (!usersByEmail.isEmpty()) {
          return true; 
      }

      
      List<UserRepresentation> usersByUsername = usersResource.search(username, null, null, null, 0, 1);
      return !usersByUsername.isEmpty();
  }

    public IResult createUserWithGroup(CreateUserRequest request, String groupName) {
      Keycloak keycloak = getKeycloakInstance();
      RealmResource realmResource = keycloak.realm(realm);
      UsersResource usersResource = realmResource.users();

      UserRepresentation user = new UserRepresentation();
      user.setUsername(request.getUsername());
      user.setEmail(request.getEmail());
      user.setFirstName(request.getFirstName());
      user.setLastName(request.getLastName());
      user.setEnabled(true);
      user.setEmailVerified(true);

      CredentialRepresentation credential = new CredentialRepresentation();
      credential.setTemporary(false);
      credential.setType(CredentialRepresentation.PASSWORD);
      credential.setValue(request.getPassword());
      user.setCredentials(Collections.singletonList(credential));

      
      Response response = usersResource.create(user);

      if (response.getStatus() == 201) {
          String userId = CreatedResponseUtil.getCreatedId(response);
          return assignUserToGroup(userId, groupName);
      }
      else{
          return new Result<>(false, "Failed to create user: " + response.getStatus());
      }


  }

  private IResult assignUserToGroup(String userId, String groupName) {
      Keycloak keycloak = getKeycloakInstance();
      RealmResource realmResource = keycloak.realm(realm);

    // realmResource.roles()

      List<GroupRepresentation> groups = realmResource.groups().groups();
      String groupId = null;

      for (GroupRepresentation group : groups) {
          if (group.getName().equalsIgnoreCase(groupName)) {
              groupId = group.getId();
              break;
          }
      }

      if (groupId == null) {
          return new Result<>(false, "Group not found: " + groupName);
      }

      realmResource.users().get(userId).joinGroup(groupId);
      return new Result<String>(true, "",userId);
  }

  public static boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof JwtAuthenticationToken jwtToken)) {
            return false;
        }

        Jwt jwt = jwtToken.getToken();
        Map<String, Object> realmAccess = jwt.getClaim("realm_access");

        if (realmAccess != null) {
            List<String> roles = (List<String>) realmAccess.get("roles");

            if (roles != null && roles.contains(role)) {
                return true;
            }
        }

        return false;
    }
}