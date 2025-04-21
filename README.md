# Spring Boot Azure SSO Documentation

## Overview
This project provides an example of integrating Azure Active Directory (Azure AD) authentication with a Spring Boot application using OAuth2 and OpenID Connect (OIDC). It implements Single Sign-On (SSO) by leveraging Azure AD for authentication, enabling users to log in via their Microsoft Azure accounts.
The system uses OAuth2 authorization code flow and OpenID Connect for secure authentication. Once logged in, it retrieves the user information such as email, name, and roles. It also provides functionality to refresh access tokens using refresh tokens issued by Azure AD.

## Technology Stack
* Spring Boot: Framework for building Java applications.
* Spring Security: Provides authentication and authorization support for Spring applications.
* Spring OAuth2 Client: For integrating OAuth2 authorization and authentication.
* Azure Active Directory: Used for managing users and their authentication via OAuth2 and OpenID Connect.
* JWT (JSON Web Token): For validating tokens.
* RestTemplate: For making HTTP requests, such as refreshing tokens.

## Application Structure

1. Application Configuration
`SecurityConfig.java`
* Security Configuration: This class configures the security settings for the Spring Boot application.
  * It disables CSRF protection and configures CORS (Cross-Origin Resource Sharing).
  * The app allows unauthenticated access to OAuth2 endpoints and certain URLs (`/auth/**`, `/login/**`).
  * It configures OAuth2 login with a success handler that redirects to `/auth/login`.

2. OAuth2 Authentication Controller
`AuthController.java`
* Login Endpoint: This endpoint handles the OAuth2 login flow.
  * OidcUser: Injected by Spring Security to represent the authenticated user from Azure AD.
  * OAuth2AuthorizedClient: Provides access to the `refresh_token` and the `access_token`.
  * The endpoint retrieves the user's details (email, family name, etc.) and tokens (id_token and refresh_token) and returns them in the response.

3. JWT Configuration
`JwtConfig.java`
* JWT Decoder: Configures the JWT decoder with the jwk-set-uri to validate and decode JWT tokens.
  * The jwk-set-uri is provided from Azure's public key set used to verify the signature of the tokens issued by Azure AD.

4. Azure AD Application Configuration
In the `application.properties` file, you need to specify various configuration details for Azure AD integration.
* Azure AD Configuration:
  * `client-id:` The Azure AD Application (client) ID.
  * `client-secret:` The Azure AD Application client secret.
  * `scope:` The permissions the application requests, including offline_access for refresh tokens.
  * `redirect-uri:` The URI that Azure AD redirects to after successful authentication.
  * `authorization-uri:` The URI for Azure AD's authorization endpoint, with the prompt=consent to request user consent.
  * `issuer-uri:` The OIDC issuer URI for validating the token's issuer.
  * `jwk-set-uri:` The URI to retrieve the JWK set for validating JWT tokens.

## Author
Developed with ❤️ by [Ali FAHS](https://github.com/fahsAli)
