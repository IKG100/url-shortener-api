package io.github.devexhale.urlshortener.security.service;

import io.github.devexhale.urlshortener.security.dto.AuthUserRequest;
import io.github.devexhale.urlshortener.security.dto.AuthUserResponse;

public interface AuthenticationService  {

    AuthUserResponse authenticate(AuthUserRequest request) ;
}
