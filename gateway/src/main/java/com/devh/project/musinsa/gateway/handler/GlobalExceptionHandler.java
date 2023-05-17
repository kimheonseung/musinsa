package com.devh.project.musinsa.gateway.handler;

import com.devh.project.musinsa.gateway.dto.ErrorResponse;
import com.devh.project.musinsa.gateway.exception.ErrorCode;
import com.devh.project.musinsa.gateway.exception.GatewayException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.ConnectException;
import java.nio.charset.StandardCharsets;

@Order(-1)
public class GlobalExceptionHandler implements ErrorWebExceptionHandler {

    private final ObjectMapper objectMapper;

    public GlobalExceptionHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ex.printStackTrace();
        ServerHttpResponse response = exchange.getResponse();
        final String message = ex instanceof ConnectException ? "관련 서비스에 연결되지 않았습니다. 잠시 후 다시 시도해주세요." : ex.getMessage();
        ErrorResponse<GatewayException> errorResponse = ErrorResponse.create(new GatewayException(ErrorCode.GATEWAY_ERROR, message));
        try {
            String res = objectMapper.writeValueAsString(errorResponse);
            DataBuffer dataBuffer = response.bufferFactory().wrap(res.getBytes(StandardCharsets.UTF_8));
            response.setStatusCode(HttpStatus.OK);
            response.getHeaders().add(HttpHeaders.CONTENT_TYPE, String.valueOf(MediaType.APPLICATION_JSON));
            return response.writeWith(Mono.just(dataBuffer));
        } catch (JsonProcessingException e) {
            throw new GatewayException(ErrorCode.UNKNOWN_ERROR, e.getMessage());
        }
    }
}
