package com.mine.manager.exception;

import com.mine.manager.exception.response.ErrorResponse;
import com.mine.manager.exception.response.FieldErrorModel;
import com.mine.manager.exception.response.ValidationErrorResponse;
import com.mine.manager.util.Messages;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Comparator;
import java.util.List;


@ControllerAdvice
public class ErrorHandler {


    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponse> handleMethodNotSupportedException(HttpServletRequest req, Exception ex) {
        HttpStatus status = HttpStatus.METHOD_NOT_ALLOWED;
        ErrorResponse response = new ErrorResponse(status.value(), status.name(), ex.getMessage(), req.getRequestURI());
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handlerMessageNotReadableException(HttpServletRequest req, HttpMessageNotReadableException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Throwable rootCause = ex.getMostSpecificCause();
        String errorMessage;

        if (rootCause instanceof com.mine.manager.exception.PropertyNotFoundException) {
            errorMessage = rootCause.getMessage();
        } else {
            errorMessage = "El formato de la petición es incorrecto o contiene valores inválidos.";
        }

        ErrorResponse response = new ErrorResponse(status.value(), status.name(), errorMessage, req.getRequestURI());
        return ResponseEntity.status(status).body(response);
    }

    /*
     *
     * */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerEntityNotFoundException(HttpServletRequest req, Exception ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponse response = new ErrorResponse(status.value(), status.name(), ex.getMessage(), req.getRequestURI());
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(DuplicateException.class)
    public ResponseEntity<ErrorResponse> handlerDuplicateException(HttpServletRequest req, Exception ex) {
        HttpStatus status = HttpStatus.CONFLICT;
        ErrorResponse response = new ErrorResponse(status.value(), status.name(), ex.getMessage(), req.getRequestURI());
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(AlreadyDeletedException.class)
    public ResponseEntity<ErrorResponse> handlerAlreadyDeletedException(HttpServletRequest req, Exception ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse response = new ErrorResponse(status.value(), status.name(), ex.getMessage(), req.getRequestURI());
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(ConnectionFailedException.class)
    public ResponseEntity<ErrorResponse> handleConnectionFailedException(HttpServletRequest req, Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse response = new ErrorResponse(status.value(), status.name(), ex.getMessage(), req.getRequestURI());
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<ErrorResponse> handleInvalidValueException(HttpServletRequest req, Exception ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse response = new ErrorResponse(status.value(), status.name(), ex.getMessage(), req.getRequestURI());
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(PropertyNotFoundException.class)
    public ResponseEntity<ErrorResponse> handlerPropertyNotFoundException(HttpServletRequest req, Exception ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponse response = new ErrorResponse(status.value(), status.name(), ex.getMessage(), req.getRequestURI());
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(RequestFailedException.class)
    public ResponseEntity<ErrorResponse> handleRequestFailedException(HttpServletRequest req, Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse response = new ErrorResponse(status.value(), status.name(), ex.getMessage(), req.getRequestURI());
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex, HttpServletRequest req) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String errorMessage = String.format(Messages.getProperty("exception.missingServletRequestParameterException.message"), ex.getParameterName());
        ErrorResponse response = new ErrorResponse(status.value(), status.getReasonPhrase(), errorMessage, req.getRequestURI());
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex, HttpServletRequest req) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String errorMessage = String.format(Messages.getProperty("exception.methodArgumentTypeMismatchException.message"), ex.getName(), ex.getRequiredType().getSimpleName());
        ErrorResponse response = new ErrorResponse(status.value(), status.getReasonPhrase(), errorMessage, req.getRequestURI());
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidException(
            HttpServletRequest req, MethodArgumentNotValidException ex) {

        List<FieldErrorModel> errors = ex.getBindingResult().getAllErrors().stream().map(fieldError -> {
                    FieldErrorModel fieldErrorModel = new FieldErrorModel();
                    fieldErrorModel.setCode(fieldError.getCode());
                    fieldErrorModel.setMessage(fieldError.getDefaultMessage());
                    fieldErrorModel.setField(((FieldError) fieldError).getField());
                    return fieldErrorModel;
                }).sorted(Comparator.comparing(FieldErrorModel::getField)
                        .thenComparing(FieldErrorModel::getCode)
                        .thenComparing(FieldErrorModel::getMessage))
                .toList();

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ValidationErrorResponse response = new ValidationErrorResponse();
        response.setCode(status.value());
        response.setStatus(status.name());
        response.setPath(req.getRequestURI());
        response.setErrors(errors);

        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(HasAsociatedEntityException.class)
    public ResponseEntity<ErrorResponse> handlerHasAsociatedEntity(HttpServletRequest req, Exception ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse response = new ErrorResponse(status.value(), status.name(), ex.getMessage(), req.getRequestURI());
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentials(BadCredentialsException ex, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse();
        error.setCode(HttpStatus.UNAUTHORIZED.value());
        error.setStatus(HttpStatus.UNAUTHORIZED.name());
        error.setMessage("Credenciales incorrectas. Verifique su usuario y contraseña.");
        error.setPath(request.getRequestURI());

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }
}