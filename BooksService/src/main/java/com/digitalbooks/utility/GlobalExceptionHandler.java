//package com.digitalbooks.utility;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.ListIterator;
//import java.util.Map;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.http.HttpStatus;
//import org.springframework.validation.ObjectError;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//
//@RestControllerAdvice
//public class GlobalExceptionHandler extends Exception {
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	public static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
//
//	public GlobalExceptionHandler(String errorMessage, Exception e) {
//		super(errorMessage, e);
//		logger.error(errorMessage, e);
//	}
//
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(Exception.class)
//	public String handleException(Exception exception) {
//		System.out.println("Exception");
//		return "error";
//	}
//
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
//		List<ObjectError> errors = exception.getBindingResult().getAllErrors();
//		System.out.println("bad request");
//		Map<String, String> errorsMap = new HashMap<>();
//		ListIterator<ObjectError> iterator = errors.listIterator();
//		while (iterator.hasNext()) {
//			ObjectError error = iterator.next();
//			errorsMap.put(error.getCode(), error.getDefaultMessage());
//		}
//		return errorsMap;
//	}
//
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	@ExceptionHandler(IllegalArgumentException.class)
//	public String handleIllegalArgumentException(IllegalArgumentException illegalArgumentException) {
//		System.out.println("IllegalArgumentException");
//		return "error";
//	}
//
//}
