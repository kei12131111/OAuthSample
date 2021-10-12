package com.example.oauth2clientsample.domain.service.todo;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;

@Service
public class RevokeTokenClientServiceImpl implements RevokeTokenClientService {

    @Value("${auth.serverUrl}/api/v1/oauth/tokens/revoke")
    String revokeTokenUrl; // (1)

    @Inject
    @Named("todoAuthCodeGrantResourceRestTemplate")
    OAuth2RestOperations oauth2RestOperations; // (2)

    @Inject
    @Named("revokeRestTemplate")
    RestOperations revokeRestOperations; // (3)
    
    @Override
    public String revokeToken() {

        String tokenValue = getTokenValue(oauth2RestOperations);

        String result = "";

        if(StringUtils.hasLength(tokenValue)){
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            MultiValueMap<String, String> variables = new LinkedMultiValueMap<>();
            variables.add("token", tokenValue);

            try {
                revokeRestOperations.postForObject(revokeTokenUrl,
                        new HttpEntity<MultiValueMap<String, String>>(variables, headers),
                        Void.class); // (4)
                result = "success";
                initContextToken(oauth2RestOperations); // (5)
            } catch (HttpClientErrorException e) {
                result = "invalid request";
            }
        }else{
            result ="token not exist";
        }
        return result;
    }

    // (6)
    private String getTokenValue(OAuth2RestOperations oauth2RestOperations) {
        OAuth2AccessToken token = oauth2RestOperations.getOAuth2ClientContext()
                .getAccessToken();
        if (token == null) {
            return "";
        }
        return token.getValue();
    }

    // (7)
    private void initContextToken(OAuth2RestOperations oauth2RestOperations) {
        oauth2RestOperations.getOAuth2ClientContext().setAccessToken(null);
    }
}