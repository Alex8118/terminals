package ru.dariedu.terminals.monitoring.api.security;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;
import static ru.dariedu.terminals.monitoring.api.common.Constants.API_PATH;
import static ru.dariedu.terminals.monitoring.api.common.Constants.HEADER_STRING;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import ru.dariedu.terminals.monitoring.api.users.UserRepository;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Configuration
    @Order(1)
    @Component
    public static class TerminalClientWebSecurityConfigurationAdapter  extends WebSecurityConfigurerAdapter {

       @Autowired
        private TerminalsFilter terminalsFilter;

        @Primary
        @Bean
        public FilterRegistrationBean terminalsFilterRegistration(TerminalsFilter filter) {
            FilterRegistrationBean registration = new FilterRegistrationBean(filter);
            registration.setEnabled(false);
            return registration;
        }

        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf()
                    .disable()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .requestMatchers(
                            matchers -> matchers
                                    .antMatchers(POST, API_PATH + "/terminals")
                                    .antMatchers(POST, API_PATH + "/payments")
                                    .antMatchers(PUT, API_PATH + "/statuses")
                    )
                    .addFilterBefore(terminalsFilter, BasicAuthenticationFilter.class)
                    .authorizeRequests()
                    .anyRequest()
                    .permitAll();
        }
    }

    @Configuration
    @Order(2)
    @Component
    public static class WebClientWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

        private static final String[] AUTH_WHITELIST = {
                "/v2/api-docs",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**",
                "/error",
                "/h2-console/**"
        };

        @Value("${jwt.token.expiration.minutes}")
        private long tokenExpirationTime;

        @Value("${jwt.token.secret}")
        private String jwtSecret;

        @Autowired
        private UserDetailsService userDetailsServiceImpl;

        @Autowired
        private BCryptPasswordEncoder bCryptPasswordEncoder;

        @Autowired
        private UserRepository userRepository;

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
            final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            CorsConfiguration config = new CorsConfiguration().applyPermitDefaultValues();
            config.addAllowedHeader(HEADER_STRING);
            config.addExposedHeader(HEADER_STRING);
            config.addAllowedMethod("*");
            source.registerCorsConfiguration("/**", config);
            return source;
        }

        @Override
        public void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(userDetailsServiceImpl).passwordEncoder(bCryptPasswordEncoder);
        }

        @Override
        public void configure(WebSecurity web) {
            web
                    .ignoring().antMatchers(POST, API_PATH + "/users")
                    .and()
                    .ignoring().antMatchers(AUTH_WHITELIST);
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .cors()
                    .and()
                    .csrf()
                    .disable()
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers(HttpMethod.POST, API_PATH + "/users").permitAll()
                    .antMatchers(AUTH_WHITELIST).permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .addFilterBefore(
                            new JWTAuthenticationFilter(
                                    API_PATH + "/users/login",
                                    authenticationManager(),
                                    jwtSecret,
                                    tokenExpirationTime,
                                    userRepository
                            ),
                            UsernamePasswordAuthenticationFilter.class
                    )
                    .addFilterBefore(
                            new JWTAuthorizationFilter(
                                    authenticationManager(),
                                    jwtSecret,
                                    userRepository
                            ),
                            UsernamePasswordAuthenticationFilter.class
                    );
        }
    }

}
