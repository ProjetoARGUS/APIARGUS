package com.argus.api.exception;

import com.argus.api.dto.ExecoesDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UsuariosException {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ExecoesDTO> handleDuplicateEntry(DataIntegrityViolationException ex) {
        ExecoesDTO error = new ExecoesDTO("Usuário já cadastrado", "400");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationErrors(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body("Dados inválidos.");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity handleMissingParameter(MissingServletRequestParameterException ex) {
        return ResponseEntity.badRequest().body("Faltou um parâmetro obrigatório.");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleUnreadableMessage(HttpMessageNotReadableException ex) {
        return ResponseEntity.badRequest().body("Erro no formato dos dados.");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body("Argumento inválido.");
    }
}
