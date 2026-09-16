package com.carsale.filter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;


@Component
public class CustomAuthenticationFilter extends AbstractGatewayFilterFactory<CustomAuthenticationFilter.Config> {

    private final WebClient webClient;

    public CustomAuthenticationFilter(WebClient.Builder webClientBuilder,
            @Value("${auth.service.url:http://localhost:8081}") String authServiceUrl) {
        super(Config.class);
        this.webClient = webClientBuilder.baseUrl(authServiceUrl).build();
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getURI().getPath();

            // Log the request path	
            System.out.println("Incoming request path: " + path);

            // Allow unauthenticated paths: /login and /register
            if (path.equalsIgnoreCase("/auth/api/auth/login") || path.equalsIgnoreCase("/auth/api/auth/signup")) {
                System.out.println("Allowing unauthenticated access to: " + path);
                return chain.filter(exchange);
            }

            // Forward other requests to /auth
            System.out.println("Forwarding request to /auth for authentication");

            return webClient
                    .get()
                    .uri("/api/auth/auth") // Endpoint to call for authentication
//                    .uri(serviceUrl)
                    .headers(headers -> headers.addAll(request.getHeaders())) // Forward original headers
                    .retrieve()
                    .bodyToMono(String.class)
                    .flatMap(username -> {
                        if (username != null && !username.trim().isEmpty()) {
                            System.out.println("Authentication successful for path: " + path + ", Username: " + username);
                            System.out.println(username);
                            // Forward the username by adding it to the request headers
                            String[] arr=username.split(" ");
                            ServerHttpRequest modifiedRequest = exchange.getRequest()
                                    .mutate()
                                    .header("Authenticated-Username",arr[0])
                                    .header("Authenticated-UserRole",arr[1])
                                    .build();

                            System.out.println("modified resst----------------------------------------");

                            ServerWebExchange modifiedExchange = exchange.mutate()
                                    .request(modifiedRequest)
                                    .build();

                            return chain.filter(modifiedExchange);
                        } else {
                            System.out.println("Authentication failed for: " + path);
                            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                            return exchange.getResponse().setComplete();
                        }
                    })
                    .onErrorResume(error -> {
                        // Log the error
                        System.err.println("Error during authentication: " + error.getMessage());
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    });
        };
    }

    public static class Config {
        // Empty configuration class
    }
}
