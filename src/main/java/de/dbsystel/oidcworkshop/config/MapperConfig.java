package de.dbsystel.oidcworkshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

@Configuration
public class MapperConfig {

    @Bean
    public GrantedAuthoritiesMapper userAuthoritiesMapper() {
        return (authorities) -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            authorities.forEach(authority -> {
                if (OidcUserAuthority.class.isInstance(authority)) {
                    OidcUserAuthority oidcUserAuthority = (OidcUserAuthority) authority;
                    Map<String, Object> attributes = oidcUserAuthority.getAttributes();
                    if (attributes.containsKey("groups")) {
                        String groups = (String) attributes.get("groups");
                        StringTokenizer tokenizer = new StringTokenizer(groups, "|");
                        while (tokenizer.hasMoreElements()) {
                            mappedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + tokenizer.nextToken().toUpperCase()));
                        }
                    }
                }
            });
            return mappedAuthorities;
        };
    }
}