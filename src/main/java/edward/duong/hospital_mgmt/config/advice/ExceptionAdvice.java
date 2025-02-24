package edward.duong.hospital_mgmt.config.advice;

import edward.duong.hospital_mgmt.controller.models.BaseResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionAdvice {
    public static final String DEFAULT_ERROR_MESSAGE = "Something went wrong";

    @ExceptionHandler(value = {RuntimeException.class})
    public ResponseEntity<BaseResponse> handleRuntimeException(RuntimeException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.builder().error(DEFAULT_ERROR_MESSAGE).build());
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ResponseEntity<BaseResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(BaseResponse.builder().error(exception.getMessage()).build());
    }
}
