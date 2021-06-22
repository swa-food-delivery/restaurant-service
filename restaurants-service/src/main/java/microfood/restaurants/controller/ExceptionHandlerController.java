package microfood.restaurants.controller;

import microfood.orders.dtos.InternalErrorDTO;
import microfood.restaurants.exceptions.RestaurantNotFoundException;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ExceptionHandlerController {

    @ExceptionHandler({RestaurantNotFoundException.class})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public void handleNotFoundException() {
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<InternalErrorDTO> handleInternalError(Exception e) {
        return new ResponseEntity<>(new InternalErrorDTO(e.getMessage(), ExceptionUtils.getStackTrace(e)), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
