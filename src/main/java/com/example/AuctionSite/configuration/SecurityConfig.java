package com.example.AuctionSite.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class SecurityConfig {
    @NonFinal
    CustomJwtDecoder customJwtDecoder;
    
    String[] PUBLIC_ENDPOINTS_POST = {
        "/users/register",
        "/authenticates/login",
        "/authenticates/logout",
        "/authenticates/refresh",
        "/authenticates/introspect_token",
    };
    
    String[] PUBLIC_ENDPOINTS_GET = {
        // Product
        "/products/search_product_by_name",
        "/products/get_products_pending_auction",
        "/products/get_products_active",
        "/products/products_paged",
        "/products/products_pending_auction_paged",
        "/products/products_active_paged",
        "/products/products_sold_paged",
        "/products/products_by_id_paged",
        // Auction
        "/auctions/search_auction_by_name",
        "/auctions/get_auctions_pending",
        "/auctions/get_auctions_ongoing",
        "/auctions/get_auctions_ended",
        "/auctions/auctions_paged",
        "/auctions/auctions_pending_paged",
        "/auctions/auctions_ongoing_paged",
        "/auctions/auctions_ended_paged",
        //Category
        "/categories/get_all_categories",
        //Bid
        "/bids/ranking/**",
        //Regulation
        "/regulations/get_all_regulations",
        //Contact
        "/contacts/get_all_contacts",
    };
    
    String[] ANY_PUBLIC = {
        "/image_default/**",
        "/images_folder/**",
    };
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINTS_POST)
            .permitAll().requestMatchers(HttpMethod.GET, PUBLIC_ENDPOINTS_GET)
            .permitAll()
            .requestMatchers(HttpMethod.GET, ANY_PUBLIC)
            .permitAll()
            .anyRequest()
            .authenticated());
        
        httpSecurity.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer
                .decoder(customJwtDecoder)
                .jwtAuthenticationConverter(jwtAuthenticationConverter()))
            .authenticationEntryPoint(new JwTAuthenticationEntryPoint()));
        
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        return httpSecurity.build();
    }
    
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }
    
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}