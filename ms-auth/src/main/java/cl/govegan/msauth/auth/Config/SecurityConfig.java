package cl.govegan.msauth.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import cl.govegan.msauth.auth.jwt.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final JwtAuthenticationFilter JwtAuthenticationFilter;
        private final AuthenticationProvider authenticationProvider;
   
   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
       return http
               .csrf(csrf -> csrf.disable())
               .authorizeHttpRequests(authorizeRequests ->
                       authorizeRequests
                               .requestMatchers("/api/v1/auth/**").permitAll()
                               .anyRequest().authenticated()
               )
               .sessionManagement(sessionManagementCustomizer -> sessionManagementCustomizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
               .authenticationProvider(authenticationProvider)
               .addFilterBefore(JwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
               .build();

   }
}
