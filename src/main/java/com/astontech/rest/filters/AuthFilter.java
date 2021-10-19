package com.astontech.rest.filters;

import com.astontech.rest.exceptions.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // create hard coded token (this would come from a token store irl)
        final String serverToken = "aerhsrbsrthnbszrtnsrftukjrdtyndrtyumd";
        // just going to secure '/test' endpoint
        if(request.getRequestURI().equals("/test")) {
            // check for auth header in request
            if(request.getHeader("Authorization") == null) {
                resolver.resolveException(request, response,
                        null, new UnauthorizedException("Auth Header Missing."));
            } else {
                // check that token from request matches serverToken(hard coded)
                String token = request.getHeader("Authorization")
                        .replace("Bearer", "")
                        .trim();
                if(token.equals(serverToken)) {
                    log.info("Authorized request to /test");
                } else {
                    resolver.resolveException(request, response, null, new UnauthorizedException("Invalid Token."));
                }
            }
        }

        response.setHeader("Custom-Header", "Bipin");
        filterChain.doFilter(request, response);
    }
}
