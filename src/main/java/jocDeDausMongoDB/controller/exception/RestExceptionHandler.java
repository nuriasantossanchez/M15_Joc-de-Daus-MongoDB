package jocDeDausMongoDB.controller.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Clase de la capa Controller, dentro del paquete Exception
 *
 * Clase base conveniente para las clases @ControllerAdvice que desean proporcionar un manejo de
 * excepciones centralizado en todos los metodos @RequestMapping a traves de los metodos @ExceptionHandler.
 *
 * Esta clase base proporciona un metodo @ExceptionHandler para manejar las excepciones internas de Spring MVC.
 * Este metodo devuelve ResponseEntity para escribir en la respuesta con un conversor de mensajes.
 *
 * Lanza una excepción cuando falla la validacion de un argumento anotado con @Valid
 *
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    // error handle for @Valid
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);
    }
}
