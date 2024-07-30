package by.ak.todo_restapi_app.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final RsaKeyProperties rsaKey;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v1/signIn").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKey.publicKey()).build();
    }

    @Bean
    JwtEncoder jwtEncoder(){
        JWK jwk = new RSAKey.Builder(rsaKey.publicKey()).privateKey(rsaKey.privateKey()).build();
        JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));

        return new NimbusJwtEncoder(jwks);
    }


    /**
     *     @Bean
     *     public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
     *         http
     *             .csrf(AbstractHttpConfigurer::disable)
     *             .authorizeHttpRequests(auth -> auth
     *                 .anyRequest().authenticated()
     *             )
     *             .oauth2ResourceServer(oauth2 -> oauth2
     *                 .jwt(jwt -> jwt
     *                     .jwtAuthenticationConverter(jwtAuthenticationConverter())
     *                 )
     *             )
     *             .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
     *             .httpBasic(AbstractHttpConfigurer::withDefaults);
     *
     *         return http.build();
     *     }
     *
     *     @Bean
     *     public JwtAuthenticationConverter jwtAuthenticationConverter() {
     *         JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
     *
     *         jwtAuthenticationConverter.setPrincipalClaimName("sub"); // Здесь укажите имя claim, содержащего username
     *
     *         return jwtAuthenticationConverter;
     *     }
     * }
     */
}
