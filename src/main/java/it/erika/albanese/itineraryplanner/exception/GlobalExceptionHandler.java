package it.erika.albanese.itineraryplanner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Arrays;

import static it.erika.albanese.itineraryplanner.utils.ErrorMessages.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidItineraryException.class)
    public ResponseEntity<ErrorDTO> handleInvalidItineraryException(InvalidItineraryException ex) {
        ErrorDTO errorDTO = ErrorDTO.builder().timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value()).error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .trace(Arrays.toString(ex.getStackTrace())).message(ITINERARY_NOT_FOUND).path("").build();

        return new ResponseEntity<>(errorDTO, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidLegException.class)
    public ResponseEntity<ErrorDTO> handleInvalidLegException(InvalidLegException ex) {
        ErrorDTO errorDTO = ErrorDTO.builder().timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value()).error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .trace(Arrays.toString(ex.getStackTrace())).message(LEG_NOT_FOUND).path("").build();

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MaximumItinerariesReachedException.class)
    public ResponseEntity<ErrorDTO> handleMaximumItinerariesReachedException(MaximumItinerariesReachedException ex) {
        ErrorDTO errorDTO = ErrorDTO.builder().timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value()).error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .trace(Arrays.toString(ex.getStackTrace())).message(MAXIMUM_ITINERARIES).path("").build();

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidSortException.class)
    public ResponseEntity<ErrorDTO> handleInvalidSortException(InvalidSortException ex) {
        ErrorDTO errorDTO = ErrorDTO.builder().timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value()).error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .trace(Arrays.toString(ex.getStackTrace())).message(INVALID_SORT).path("").build();

        return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleException(Exception ex) {
        ErrorDTO errorDTO = ErrorDTO.builder().timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value()).error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .trace(Arrays.toString(ex.getStackTrace())).message("").path("").build();

        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
