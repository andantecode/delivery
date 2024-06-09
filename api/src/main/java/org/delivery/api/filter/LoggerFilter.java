package org.delivery.api.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class LoggerFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // Wrapping
        var request = new ContentCachingRequestWrapper((HttpServletRequest) servletRequest);
        var response = new ContentCachingResponseWrapper((HttpServletResponse) servletResponse);

        // Filtering 전
        filterChain.doFilter(request, response);

        // Filtering 후

        // Request information

        var headerNames = request.getHeaderNames();
        var headerValues = new StringBuilder();

        headerNames.asIterator().forEachRemaining(headerKey -> {
            var headerValue = request.getHeader(headerKey);

            headerValues.append("[").append(headerKey).append(": ").append(headerValue).append(", ").append("]");
        });

        var requestBody = new String(request.getContentAsByteArray());
        var uri = request.getRequestURI();
        var method = request.getMethod();

        log.info(">>> uri: {}, method: {}, header: {}, body: {}", uri, method, headerValues, requestBody);


        // Response information
        var responseHeaderValues = new StringBuilder();

        response.getHeaderNames().forEach(headerKey -> {
            var headerValue = request.getHeader(headerKey);

            responseHeaderValues.append("[").append(headerKey).append(": ").append(headerValue).append(", ").append("]");
        });

        var responseBody = new String(response.getContentAsByteArray());

        log.info("<<< uri: {}, method: {}, header: {}, body: {}", uri, method, responseHeaderValues, responseBody);

        // responseBody 내용을 읽었으므로 다시 copy
        response.copyBodyToResponse();
    }
}
