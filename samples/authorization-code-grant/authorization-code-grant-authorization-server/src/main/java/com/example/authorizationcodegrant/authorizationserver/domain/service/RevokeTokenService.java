package com.example.authorizationcodegrant.authorizationserver.domain.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;


/**
 * トークンの取り消しを行うサービスクラスのインタフェース
 * 
 */
public interface RevokeTokenService {

    ResponseEntity<Map<String,String>> revokeToken(String tokenValue, String clientId);

}
