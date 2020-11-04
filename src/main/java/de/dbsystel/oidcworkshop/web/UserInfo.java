package de.dbsystel.oidcworkshop.web;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class UserInfo {
    String principalName;
    Instant expiration;
    String tokenValue;
}