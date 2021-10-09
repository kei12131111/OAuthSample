package com.example.authorizationcodegrant.authorizationserver.domain.service;

import org.springframework.security.oauth2.provider.client.BaseClientDetails;

import com.example.authorizationcodegrant.authorizationserver.domain.model.Client;


/**
 * クライアントの詳細情報を拡張するためのクラス。
 * 
 */
public class OAuthClientDetails extends BaseClientDetails{

	private static final long serialVersionUID = 1L;
	
	//クライアント情報を保持するフィールド
	private Client client;

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

    
}
