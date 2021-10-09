package com.example.authorizationcodegrant.authorizationserver.domain.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.Approval.ApprovalStatus;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.gfw.common.date.jodatime.JodaTimeDateFactory;



/**
 * トークンの取り消しを行うサービスクラスの実装クラス
 * 
 */
@Service
@Transactional
public class RevokeTokenServiceImpl implements RevokeTokenService {

    @Inject
    ConsumerTokenServices consumerService;

    @Inject
    TokenStore tokenStore;

    @Inject
    ApprovalStore approvalStore;

    @Inject
    JodaTimeDateFactory dateFactory;

    public ResponseEntity<Map<String,String>> revokeToken(String tokenValue, String clientId){

        OAuth2Authentication authentication = tokenStore.readAuthentication(tokenValue);

        Map<String,String> map = new HashMap<>();

        if (authentication != null) {
            if (clientId.equals(authentication.getOAuth2Request().getClientId())) {

                Authentication user = authentication.getUserAuthentication();
                if (user != null) {
                    Collection<Approval> approvals = new ArrayList<>();
                    for (String scope : authentication.getOAuth2Request().getScope()) {
                        approvals.add(
                                new Approval(user.getName(), clientId, scope, dateFactory.newDate(), ApprovalStatus.APPROVED));
                    }
                    approvalStore.revokeApprovals(approvals);
                }
                consumerService.revokeToken(tokenValue);
                return ResponseEntity.ok().body(map);

            } else {
                map.put("error", "invalid_client");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(map);
            }
        } else {
            map.put("error", "invalid_request");
            return ResponseEntity.badRequest().body(map);
        }
    }
}
