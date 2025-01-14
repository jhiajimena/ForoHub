package lescano.forohub.config;

import lescano.forohub.config.filter.JwtTokenValidator;
import lescano.forohub.utils.JwtUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private static final String API_PERMISSIONS = "/api/permissions/**";
    private static final String ROLE_DEVELOPER = "DEVELOPER";
    private static final String API_PROFILES = "/api/profiles/**";
    private static final String API_CATEGORIES = "/api/categories/**";
    private static final String API_SUBCATEGORIES = "/api/subcategories/**";
    private static final String API_COURSES = "/api/courses/**";
    private static final String API_TOPICS = "/api/topics/**";
    private static final String API_RESPONSES = "/api/responses/**";
    /*
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(crsf -> crsf.disable())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

     */


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, JwtUtils jwtUtils) throws Exception {
        return httpSecurity
                .csrf(crsf -> crsf.disable())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {
                    //public endpoints
                    http.requestMatchers("/swagger-ui/**").permitAll();
                    http.requestMatchers("/swagger-ui.html").permitAll();
                    http.requestMatchers("/v3/api-docs/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll();
                    //private endpoints
                    http.requestMatchers(HttpMethod.GET, API_CATEGORIES).authenticated();
                    http.requestMatchers(HttpMethod.POST, API_CATEGORIES).hasAuthority("CREATE_CATEGORY");
                    http.requestMatchers(HttpMethod.PUT, API_CATEGORIES).hasAuthority("EDIT_CATEGORY");
                    http.requestMatchers(HttpMethod.DELETE, API_CATEGORIES).hasAuthority("DELETE_CATEGORY");

                    http.requestMatchers(HttpMethod.GET, API_SUBCATEGORIES).authenticated();
                    http.requestMatchers(HttpMethod.POST, API_SUBCATEGORIES).hasAuthority("CREATE_SUB_CATEGORY");
                    http.requestMatchers(HttpMethod.PUT, API_SUBCATEGORIES).hasAuthority("EDIT_SUBCATEGORY");
                    http.requestMatchers(HttpMethod.DELETE, API_SUBCATEGORIES).hasAuthority("DELETE_SUBCATEGORY");

                    http.requestMatchers(HttpMethod.GET, API_COURSES).authenticated();
                    http.requestMatchers(HttpMethod.POST, API_COURSES).hasAuthority("CREATE_COURSE");
                    http.requestMatchers(HttpMethod.PUT, API_COURSES).hasAuthority("EDIT_COURSE");
                    http.requestMatchers(HttpMethod.DELETE, API_COURSES).hasAuthority("DELETE_COURSE");

                    http.requestMatchers(HttpMethod.GET, API_TOPICS).authenticated();
                    http.requestMatchers(HttpMethod.POST, API_TOPICS).hasAuthority("CREATE_TOPIC");
                    http.requestMatchers(HttpMethod.PUT, API_TOPICS).hasAnyAuthority("EDIT_OWN_TOPIC", "EDIT_ANY_TOPIC");
                    http.requestMatchers(HttpMethod.DELETE, API_TOPICS).hasAuthority("DELETE_ANY_TOPIC");

                    http.requestMatchers(HttpMethod.GET, API_RESPONSES).authenticated();
                    http.requestMatchers(HttpMethod.POST, API_RESPONSES).authenticated();
                    http.requestMatchers(HttpMethod.PUT, API_RESPONSES).hasAnyAuthority("EDIT_OWN_COMMENT", "EDIT_ANY_COMMENT");
                    http.requestMatchers(HttpMethod.DELETE, API_RESPONSES).hasAuthority("DELETE_ANY_RESPONSE");

                    http.requestMatchers(HttpMethod.GET, "/api/users/**").hasAnyRole("ADMIN", ROLE_DEVELOPER);
                    http.requestMatchers(HttpMethod.GET, "/api/users/{id}/topics").authenticated();
                    http.requestMatchers(HttpMethod.GET, "/api/users/{id}/topics/participate").authenticated();
                    http.requestMatchers(HttpMethod.GET, "/api/dashboard/**").hasAuthority("VIEW_REPORTS");

                    //Only Developer endPoints
                    http.requestMatchers(HttpMethod.GET, API_PROFILES).hasRole(ROLE_DEVELOPER);
                    http.requestMatchers(HttpMethod.POST, API_PROFILES).hasRole(ROLE_DEVELOPER);
                    http.requestMatchers(HttpMethod.PUT, API_PROFILES).hasRole(ROLE_DEVELOPER);
                    http.requestMatchers(HttpMethod.DELETE, API_PROFILES).hasRole(ROLE_DEVELOPER);
                    http.requestMatchers(HttpMethod.GET, API_PERMISSIONS).hasRole(ROLE_DEVELOPER);
                    http.requestMatchers(HttpMethod.POST, API_PERMISSIONS).hasRole(ROLE_DEVELOPER);
                    http.requestMatchers(HttpMethod.PUT, API_PERMISSIONS).hasRole(ROLE_DEVELOPER);
                    http.requestMatchers(HttpMethod.DELETE, API_PERMISSIONS).hasRole(ROLE_DEVELOPER);

                    http.requestMatchers(HttpMethod.POST, "/api/profile-permission").hasRole(ROLE_DEVELOPER);


                    http.requestMatchers(HttpMethod.PUT, "/api/users/{id}").hasAuthority("EDIT_USER");
                    http.requestMatchers(HttpMethod.DELETE, "/api/users/{id}").hasAuthority("DELETE_USER");


                    //non specification endpoint
                    http.anyRequest().denyAll();//.authenticated()
                })
                .addFilterBefore(new JwtTokenValidator(jwtUtils), BasicAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);//userDetailsService());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
    /*
    @Bean
    public UserDetailsService userDetailsService() {
        List<UserDetails> userDetails = new ArrayList<>();

        userDetails.add(User.withUsername("mañeco")
                .password("1234")
                .roles("ADMIN")
                .authorities("READ", "CREATE")
                .build());

        userDetails.add(User.withUsername("ñañela")
                .password("1234")
                .roles("USER")
                .authorities("READ")
                .build());
        return new InMemoryUserDetailsManager(userDetails);
    }
     */

