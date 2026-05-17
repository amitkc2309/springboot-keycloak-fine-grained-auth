package com.sb.kc.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final KeycloakRoleConverter keycloakRoleConverter;

        private final KeycloakAuthFilter keycloakAuthFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

            http
                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth
                            .anyRequest().authenticated()
                    )
                    /*.oauth2ResourceServer(oauth ->
                            oauth.jwt(jwt ->
                                    jwt.jwtAuthenticationConverter(keycloakRoleConverter)
                            )
                    )*/
                    .oauth2ResourceServer(oauth2 ->
                            oauth2.jwt(Customizer.withDefaults())
                    )
                    .sessionManagement(sessionManagement ->
                            sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .addFilterAfter(
                            keycloakAuthFilter,
                            BearerTokenAuthenticationFilter.class
                    );;

            return http.build();
        }
}
