package com.kleber.helpdesk.config;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.kleber.helpdesk.domain.Pessoa;
import com.kleber.helpdesk.repositories.PessoaRepository;
import com.kleber.helpdesk.security.JWTAuthenticationFilter;
import com.kleber.helpdesk.security.JWTUtil;
import com.kleber.helpdesk.security.UserSS;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    private static final String[] PUBLIC_MATCHES = { "/h2-console/**"};
    private  AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private Environment env;
    @Autowired
    private JWTUtil jwtUtil;
    //@Autowired
    //private UserDetailsService userDetailsService;
    @Autowired
    private   PessoaRepository repository;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
             
            @Override
            public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
                Optional<Pessoa> user = repository.findByEmail(email);
                if(user.isPresent()){
                    return new UserSS(user.get().getId(), user.get().getEmail(), user.get().getSenha(), user.get().getPerfis());
                }
                 throw new UsernameNotFoundException(email);
            }
        };
    }
/* 
    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        AuthenticationManager kls=  authenticationConfiguration.getAuthenticationManager();
        return kls;
    }
    */
    

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
    

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
            http.headers().frameOptions().disable();
        }

        http.cors().and().csrf().disable();
        http.addFilter(new JWTAuthenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), jwtUtil));

        http.authorizeRequests().antMatchers(PUBLIC_MATCHES).permitAll().anyRequest().authenticated();

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }



    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration().applyPermitDefaultValues();
        configuration.setAllowedMethods(Arrays.asList("POST","GET","PUT","DELETE","OPTIONS"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
