package de.dbsystel.oidcworkshop.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class Controller {

    @GetMapping("/")
    public ResponseEntity<String> landingPage() {
        String html = "<a href='/private/info'>show token info</a>" +
                "<br><a href='/logout'>logout</a>";
        return ResponseEntity.ok(html);
    }

    @GetMapping("/private/info")
    public ResponseEntity<UserInfo> info(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient) {
        String userName = authorizedClient.getPrincipalName();
        Instant expiration = authorizedClient.getAccessToken().getExpiresAt();
        String tokenValue = authorizedClient.getAccessToken().getTokenValue();
        return ResponseEntity.ok(new UserInfo(userName, expiration, tokenValue));
    }
}