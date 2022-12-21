package com.yang.simulator.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.Optional;
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    @ResponseBody
    public ResponseEntity<Result> handleException(Exception exception) {

        return ResponseEntity.of(Optional.ofNullable(ResultUtil.getResult(exception,log)));
    }

}
