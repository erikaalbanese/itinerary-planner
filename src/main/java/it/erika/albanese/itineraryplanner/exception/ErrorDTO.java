package it.erika.albanese.itineraryplanner.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class ErrorDTO {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String trace;
    private String message;
    private String path;
}
