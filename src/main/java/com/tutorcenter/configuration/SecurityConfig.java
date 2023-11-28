package com.tutorcenter.configuration;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
        private static final String[] WHITE_LIST_URL = { "/api/auth/**",
                        "/api/**",
                        "/uploads/**",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui.html" };
        private final JwtAuthenticationFilter jwtAuthFilter;
        private final AuthenticationProvider authenticationProvider;
        private final LogoutHandler logoutHandler;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http

                                .csrf(AbstractHttpConfigurer::disable)
                                .authorizeHttpRequests(req -> req.requestMatchers(WHITE_LIST_URL)
                                                .permitAll()
                                                // .requestMatchers("/api/v1/management/**")
                                                // .hasAnyRole(ADMIN.name(), MANAGER.name())
                                                // .requestMatchers(GET, "/api/v1/management/**")
                                                // .hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                                                // .requestMatchers(POST, "/api/v1/management/**")
                                                // .hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
                                                // .requestMatchers(PUT, "/api/v1/management/**")
                                                // .hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
                                                // .requestMatchers(DELETE, "/api/v1/management/**")
                                                // .hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())
                                                .anyRequest()
                                                .authenticated())
                                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                                .authenticationProvider(authenticationProvider)
                                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                                .logout(logout -> logout.logoutUrl("/api/auth/logout")
                                                .addLogoutHandler(logoutHandler)
                                                .logoutSuccessHandler(
                                                                (request, response,
                                                                                authentication) -> SecurityContextHolder
                                                                                                .clearContext()));

                return http.build();
        }

        private SecurityScheme createAPIKeyScheme() {
                return new SecurityScheme().type(SecurityScheme.Type.HTTP)
                                .bearerFormat("JWT")
                                .scheme("bearer");
        }

        @Bean
        public OpenAPI openAPI() {
                return new OpenAPI().addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                                .components(new Components().addSecuritySchemes("Bearer Authentication",
                                                createAPIKeyScheme()))
                                .info(new Info().title("My REST API")
                                                .description("Some custom description of API.")
                                                .version("1.0").contact(new Contact().name("Sallo Szrajbman")
                                                                .email("www.baeldung.com").url("salloszraj@gmail.com"))
                                                .license(new License().name("License of API")
                                                                .url("API license URL")));
        }
}
