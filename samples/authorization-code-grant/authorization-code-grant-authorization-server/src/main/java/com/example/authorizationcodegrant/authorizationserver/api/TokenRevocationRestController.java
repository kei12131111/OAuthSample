package com.example.authorizationcodegrant.authorizationserver.api;

import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.authorizationcodegrant.authorizationserver.domain.service.RevokeTokenService;

/**
 * トークンの取り消しリクエストを受けるコントローラ
 * 
 */
@RestController
@RequestMapping("oauth")
public class TokenRevocationRestController {

    @Inject
    RevokeTokenService revokeTokenService;

    @RequestMapping(value = "tokens/revoke", method = RequestMethod.POST)
    public ResponseEntity<Map<String,String>> revoke(@RequestParam("token") String tokenValue,
        @AuthenticationPrincipal UserDetails user){


        String clientId = user.getUsername();
        ResponseEntity<Map<String,String>> result = revokeTokenService.revokeToken(tokenValue, clientId);
        return result;
    }
}