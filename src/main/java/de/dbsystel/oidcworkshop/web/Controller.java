package de.dbsystel.oidcworkshop.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import java.time.Instant;

@RestController
public class Controller {

    private WebClient webClient;

    public Controller(WebClient webClient) {
        this.webClient = webClient;
    }

    @GetMapping("/")
    public ResponseEntity<String> landingPage() {
        String html = "<a href='/private/info'>show token info</a>" +
                "<br><a href='/private/external'>call external service</a>" +
                "<br><a href='/private/only-with-role'>only with role</a>" +
                "<br><a href='/private/only-with-another-role'>only with another role</a>" +
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

    @GetMapping("/private/external")
    ResponseEntity<ResultExtern> external() {
        String response = webClient
                .get()
                .exchange().block()
                .bodyToMono(String.class).block();
        return ResponseEntity.ok(new ResultExtern(response));
    }

    @GetMapping("/private/only-with-role")
    ResponseEntity<String> onlyWithRole() {
        return ResponseEntity.ok("ok you have the role!");
    }

    @GetMapping("/private/only-with-another-role")
    ResponseEntity<String> onlyWithAnotherRole() {
        return ResponseEntity.ok("ok you have another role!");
    }

    @GetMapping("/private/login")
    ModelAndView login() {
        return new ModelAndView("redirect:http://localhost:4200");
    }

}