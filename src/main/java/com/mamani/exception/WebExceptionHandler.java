package com.mamani.exception;

import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.*;
import org.springframework.web.server.ServerWebInputException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Component
@Order(-1) // Prioridad alta para que se ejecute antes de los manejadores predeterminados de WebFlux
public class WebExceptionHandler extends AbstractErrorWebExceptionHandler {

    public WebExceptionHandler(ErrorAttributes errorAttributes, WebProperties.Resources resources,
                               ApplicationContext applicationContext, ServerCodecConfigurer configurer) {
        super(errorAttributes, resources, applicationContext);
        this.setMessageWriters(configurer.getWriters());
        this.setMessageReaders(configurer.getReaders());
    }

    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse);
    }

    private Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
        Throwable error = getError(request);
        HttpStatus status = determineHttpStatus(error);
        String message = determineMessage(error);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("error", message);
        responseBody.put("status", status.value());
        responseBody.put("timestamp", LocalDateTime.now().toString());

        return ServerResponse.status(status)
                .body(BodyInserters.fromValue(responseBody));
    }

    private HttpStatus determineHttpStatus(Throwable error) {
        if (error instanceof CustomException) {
            return ((CustomException) error).getStatus();
        } else if (error instanceof WebExchangeBindException || error instanceof ServerWebInputException) {
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    private String determineMessage(Throwable error) {
        if (error instanceof CustomException){
            return error.getMessage();

        }else if (error instanceof WebExchangeBindException) {
            BindingResult result = ((WebExchangeBindException) error).getBindingResult();
            StringBuilder fieldErrorsBuilder = new StringBuilder();

            result.getFieldErrors().forEach(fieldError -> {
                fieldErrorsBuilder.append(fieldError.getDefaultMessage());
                fieldErrorsBuilder.append("; ");
            });

            if (fieldErrorsBuilder.length() > 0) {
                fieldErrorsBuilder.setLength(fieldErrorsBuilder.length() - 2);
            }

            return fieldErrorsBuilder.toString();

        }else if (error instanceof ServerWebInputException){
            return "Verify that the data types are correct and that the JSON format is correct.";
        }
        return "Internal Server Error";
    }
}
