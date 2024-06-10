package org.delivery.api.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.error.TokenErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.business.TokenBusiness;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthorizationInterceptor implements HandlerInterceptor {

    private final TokenBusiness tokenBusiness;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("AuthorizationInterceptor preHandle url: {}", request.getRequestURI());

        // Web, Chrome의 경우 GET POST Options == pass
        if(HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        // Resource를 요청하는 경우 (js, html, png, ..)
        if(handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        // TODO header authorization
        var accessToken = request.getHeader("authorization-token");

        if(accessToken == null) {
            throw new ApiException(TokenErrorCode.AUTORIZATION_TOKEN_NOT_FOUND);
        }

        var userId = tokenBusiness.validationAccessToken(accessToken);

        if (userId != null) {
            var requestContext = Objects.requireNonNull(RequestContextHolder.getRequestAttributes()); // thread local에 저장
            requestContext.setAttribute("userId", userId, RequestAttributes.SCOPE_REQUEST); // request 단위

            return true;
        }


        throw new ApiException(ErrorCode.BAD_REQUEST, "Validation failed");
    }
}
