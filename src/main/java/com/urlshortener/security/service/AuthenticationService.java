package com.urlshortener.security.service;

import com.urlshortener.security.dto.AuthUserRequest;
import com.urlshortener.security.dto.AuthUserResponse;

public interface AuthenticationService  {
    AuthUserResponse authenticate(AuthUserRequest request) ;
}
