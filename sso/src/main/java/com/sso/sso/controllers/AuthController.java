package com.sso.sso.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public ResponseEntity<Map<String, Object>> login(
            @AuthenticationPrincipal OidcUser oidcUser,
            @RegisteredOAuth2AuthorizedClient("azure") OAuth2AuthorizedClient authorizedClient
    ) {
        if (oidcUser == null) {
            return ResponseEntity.status(401).body(null);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("email", oidcUser.getEmail());
        response.put("family_name", oidcUser.getFamilyName());
        response.put("given_name", oidcUser.getGivenName());
        response.put("name", oidcUser.getFullName());
        response.put("id_token", oidcUser.getIdToken().getTokenValue());

        if (authorizedClient.getRefreshToken() != null) {
            response.put("refresh_token", authorizedClient.getRefreshToken().getTokenValue());
        }

        return ResponseEntity.ok(response);
    }


}
