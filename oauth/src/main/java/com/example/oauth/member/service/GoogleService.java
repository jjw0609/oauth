package com.example.oauth.member.service;

import com.example.oauth.member.dto.AccessTokenDto;
import com.example.oauth.member.dto.GoogleProfileDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

@Service
public class GoogleService {

    @Value("${oauth.google.client-id}")
    private String googleClientId;

    @Value("${oauth.google.client-secret}")
    private String googleClientSecret;

    @Value("${oauth.google.redirect-uri}")
    private String googleRedirectUri;


    public AccessTokenDto getAccessToken(String code) {
        //인가코드, clientId, client_secret, redirect_uri, grant_type

        //Spring6부터 RestTemplate 비추천 상태이기에, 대신 RestClient 사용
        RestClient restClient = RestClient.create();

        //MultiValueMap을 통해 자동으로 form-data 형식으로 body 조립
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", googleClientId);
        params.add("client_secret", googleClientSecret);
        params.add("redirect_uri", googleRedirectUri);
        params.add("grant_type", "authorization_code");

        ResponseEntity<String> response = restClient.post()
                .uri("https://oauth2.googleapis.com/token")
                .header("Content-Type", "application/x-www-form-urlencoded")
                //?code=xxx&client_id=yyy&
                .body(params)
                //retrieve: 응답 body값만을 추출
                .retrieve()
                .toEntity(String.class);

        System.out.println("응답 JSON " + response.getBody());

        return null;
    }

    public GoogleProfileDto getGoogleProfile(String token) {

    }
}
