package com.goit.urlshortener.security.service;

import com.goit.urlshortener.security.dto.AuthUserRequest;
import com.goit.urlshortener.security.dto.AuthUserResponse;

public interface AuthenticationService  {
    AuthUserResponse authenticate(AuthUserRequest request) ;
}
