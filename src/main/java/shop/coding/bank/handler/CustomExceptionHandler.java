package shop.coding.bank.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shop.coding.bank.dto.ResponseDto;
import shop.coding.bank.handler.ex.CustomApiException;
import shop.coding.bank.handler.ex.CustomForbiddenException;
import shop.coding.bank.handler.ex.CustomValidationException;

@RestControllerAdvice
public class CustomExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> apiException(CustomApiException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ResponseDto<>(-1, e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomValidationException.class)
    public ResponseEntity<?> validationApiException(CustomValidationException e) {
        log.error(e.getMessage());
        return new ResponseEntity<>(new ResponseDto<>(-1, e.getMessage(), e.getErrorMap()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomForbiddenException.class)
    public ResponseEntity<?> forbiddenException(CustomForbiddenException e) {
        log.error(e.getMessage()); // 권한이 없습니다.
        return new ResponseEntity<>(new ResponseDto<>(-1, e.getMessage(), null), HttpStatus.FORBIDDEN);
    }
}
