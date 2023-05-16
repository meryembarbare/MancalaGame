package com.mancala.game.resource.error;

import com.mancala.game.exception.BadTurnException;
import com.mancala.game.exception.PitException;
import com.mancala.game.exception.TechnicalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
@Slf4j
public class CustomizedResponseEntityExceptionHandler {

	@ExceptionHandler(TechnicalException.class)
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public Problem onTechnicalException(TechnicalException e) {
		return buildProblem(e, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public List<Problem> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		List<Problem> errors = new ArrayList<>(e.getBindingResult().getFieldErrors().size());
		e.getBindingResult().getFieldErrors()
				.forEach(fieldError -> errors.add(
						Problem.create()
								.withTitle(fieldError.getField())
								.withStatus(HttpStatus.BAD_REQUEST)
								.withDetail(Objects.requireNonNull(fieldError.getDefaultMessage()))
				));
		return errors;
	}


	@ExceptionHandler(BadTurnException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Problem onBadTurnException(BadTurnException e) {
		return buildProblem(e, HttpStatus.BAD_REQUEST);
	}


	@ExceptionHandler(PitException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Problem onPitException(PitException e) {
		return buildProblem(e, HttpStatus.BAD_REQUEST);
	}


	private Problem buildProblem(Exception e, HttpStatus status) {
		String title = e.getClass().getSimpleName();
		String detail = e.getMessage();
		return Problem.create()
				.withTitle(title)
				.withStatus(status)
				.withDetail(detail);

	}

}
