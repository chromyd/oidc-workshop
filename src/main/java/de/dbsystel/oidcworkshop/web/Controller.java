package de.dbsystel.oidcworkshop.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/")
    public ResponseEntity<String> landingPage() {
        String html = "<a href='/private/info'>show token info</a>";
        return ResponseEntity.ok(html);
    }

    @GetMapping("/private/info")
    public ResponseEntity<String> info() {
        return ResponseEntity.ok("userinfo");
    }

}