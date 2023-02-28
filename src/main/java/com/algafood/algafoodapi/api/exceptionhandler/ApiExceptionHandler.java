package com.algafood.algafoodapi.api.exceptionhandler;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.algafood.algafoodapi.core.validation.ValidationException;
import com.algafood.algafoodapi.domain.exceptions.EntityInUseException;
import com.algafood.algafoodapi.domain.exceptions.EntityNotfoundException;
import com.algafood.algafoodapi.domain.exceptions.NegocioException;
import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     *
     */
    private static final String ERROR_MSG_GENERIC = "Ocorreu um erro interno inesperado no sistema. Tente novamente e se o problema persistir, entre em contato com o administrador do sistema.";

    @Autowired
    private MessageSource messageSource;

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity.status(status).headers(headers).build();
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatusCode status,
            WebRequest request) {

        return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex, WebRequest req) {
        ErrorApiType errorApiType = ErrorApiType.ACCESS_DENIED;
        HttpStatusCode status = HttpStatusCode.valueOf(403);
        String detail = "Você não possui permissão para realizar esta operação!";
        ErrorApi errorApi = instanceErrorApi(status, errorApiType, ex.getLocalizedMessage())
                .userMessage(detail)
                .build();

        return handleExceptionInternal(ex, errorApi, new HttpHeaders(), status, req);
    }

    @ExceptionHandler(EntityNotfoundException.class)
    public ResponseEntity<?> handleEntityNotFound(EntityNotfoundException ex, WebRequest req) {

        ErrorApiType errorApiType = ErrorApiType.RESOURCE_NOT_FOUND;
        HttpStatusCode status = HttpStatusCode.valueOf(404);
        ErrorApi errorApi = instanceErrorApi(status, errorApiType, ex.getMessage())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, errorApi, new HttpHeaders(), status, req);

    }

    @ExceptionHandler(NegocioException.class)
    public ResponseEntity<?> handleNegocio(NegocioException ex, WebRequest req) {

        ErrorApiType errorApiType = ErrorApiType.NEGOCIO;
        HttpStatusCode status = HttpStatusCode.valueOf(400);
        ErrorApi errorApi = instanceErrorApi(status, errorApiType, ex.getMessage())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, errorApi, new HttpHeaders(), status, req);

    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> handleEntityInUse(EntityInUseException ex, WebRequest req) {

        ErrorApiType errorApiType = ErrorApiType.ENTITY_IN_USE;
        HttpStatusCode status = HttpStatusCode.valueOf(409);
        ErrorApi errorApi = instanceErrorApi(status, errorApiType, ex.getMessage())
                .userMessage(ex.getMessage())
                .build();

        return handleExceptionInternal(ex, errorApi, new HttpHeaders(), status, req);

    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormat((InvalidFormatException) rootCause, headers, status, request);
        } else if (rootCause instanceof PropertyBindingException) {
            return handlePropertyBinding((PropertyBindingException) rootCause, headers, status, request);
        }

        ErrorApiType errorApiType = ErrorApiType.UNREADABLE_BODY;
        ErrorApi errorApi = instanceErrorApi(status, errorApiType, "O corpo da requisição está inválido!")
                .userMessage(ERROR_MSG_GENERIC)
                .build();

        return super.handleExceptionInternal(ex, errorApi, headers, status, request);
    }

    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        ErrorApiType errorApiType = ErrorApiType.UNREADABLE_BODY;

        String path = joinPath(ex.getPath());

        String detail = String.format(
                "A propriedade '%s' recebeu o valor '%s' que é de um tipo inválido. Infome um valor compatível com o tipo %s.",
                path, ex.getValue(), ex.getTargetType().getSimpleName());

        ErrorApi errorApi = instanceErrorApi(status, errorApiType, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(ex, errorApi, headers, status, request);
    }

    private ResponseEntity<Object> handlePropertyBinding(PropertyBindingException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        ErrorApiType errorApiType = ErrorApiType.PROPERTY_NON_EXISTENT;

        String path = joinPath(ex.getPath());

        String detail = String.format(
                "Propriedade '%s' inexistente. Por favor, corrija ou remova a(s) propriedade(s) para continuar.", path);
        ErrorApi errorApi = instanceErrorApi(status, errorApiType, detail)
                .userMessage(ERROR_MSG_GENERIC)
                .build();

        return handleExceptionInternal(ex, errorApi, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers, status, request);
        }

        return super.handleTypeMismatch(ex, headers, status, request);

    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ErrorApiType errorApiType = ErrorApiType.METHOD_ARGUMENT_TYPE_MISMATCH;

        String detail = String.format(
                "O parâmetrod da URL '%s' recebeu o valor '%s', que é de um tipo inválido. Informe um valor compatível com o tipo %s",
                ex.getName(), ex.getValue(), ex.getRequiredType().getName());

        ErrorApi errorApi = instanceErrorApi(status, errorApiType, detail)
                .userMessage(detail)
                .build();
        return handleExceptionInternal(ex, errorApi, headers, status, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        ErrorApiType errorApiType = ErrorApiType.RESOURCE_NOT_FOUND;

        String detail = String.format("Recurso '%s' inexistente.", ex.getRequestURL());

        ErrorApi errorApi = instanceErrorApi(status, errorApiType, detail)
                .userMessage(detail)
                .build();

        return handleExceptionInternal(ex, errorApi, headers, status, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {

        HttpStatusCode status = HttpStatusCode.valueOf(500);
        ErrorApiType errorApiType = ErrorApiType.SYSTEM_ERROR;

        String detail = ERROR_MSG_GENERIC;

        // ex.printStackTrace();
        log.error(ex.getMessage(), ex);

        ErrorApi errorApi = instanceErrorApi(status, errorApiType, detail).build();

        return handleExceptionInternal(ex, errorApi, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        return handleValidationInternal(ex, ex.getBindingResult(), headers, status, request);
    }

    private ResponseEntity<Object> handleValidationInternal(Exception ex, BindingResult bindingResult,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        ErrorApiType errorApiType = ErrorApiType.INVALID_DATA;

        String detail = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.";

        List<ErrorApi.Object> errorObjects = bindingResult.getAllErrors().stream()
                .map(objError -> {
                    String msg = messageSource.getMessage(objError, LocaleContextHolder.getLocale());

                    String name = objError.getObjectName();

                    if (objError instanceof FieldError) {
                        name = ((FieldError) objError).getField();
                    }

                    return ErrorApi.Object.builder()
                            .name(name)
                            .userMessage(msg)
                            .build();
                })
                .collect(Collectors.toList());

        ErrorApi errorApi = instanceErrorApi(status, errorApiType, detail)
                .userMessage(detail)
                .objects(errorObjects)
                .build();

        return handleExceptionInternal(ex, errorApi, headers, status, request);

    }

    public ResponseEntity<Object> handleValidationException(ValidationException ex, WebRequest request) {

        return handleValidationInternal(ex, ex.getBindingResult(), new HttpHeaders(), HttpStatusCode.valueOf(400), request);

    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
            HttpStatusCode status, WebRequest request) {

        if (body == null) {
            body = ErrorApi.builder()
                    .title(HttpStatus.valueOf(status.value()).getReasonPhrase())
                    .status(status.value())
                    .timestamp(OffsetDateTime.now())
                    .userMessage(ERROR_MSG_GENERIC)
                    .build();
        } else if (body instanceof String) {
            body = ErrorApi.builder()
                    .title((String) body)
                    .status(status.value())
                    .timestamp(OffsetDateTime.now())
                    .userMessage(ERROR_MSG_GENERIC)
                    .build();
        }

        return super.handleExceptionInternal(ex, body, headers, status, request);
    }

    public ErrorApi.ErrorApiBuilder instanceErrorApi(HttpStatusCode status, ErrorApiType errorApiType, String detail) {
        return ErrorApi.builder()
                .status(status.value())
                .type(errorApiType.getUri())
                .title(errorApiType.getTitle())
                .detail(detail)
                .timestamp(OffsetDateTime.now());
    }

    public String joinPath(List<Reference> refs) {
        return refs.stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));
    }

}
