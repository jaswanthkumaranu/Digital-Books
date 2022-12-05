package com.digitalbooks.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BookServiceExceptionHandler extends Exception {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	public static final Logger logger = LoggerFactory.getLogger(BookServiceExceptionHandler.class);
	
	 public BookServiceExceptionHandler(String errorMessage, Throwable err) {
		 super(errorMessage, err);
		 logger.error(errorMessage,err);
		 
	    }

	public BookServiceExceptionHandler(String errorMessage) {
		 logger.error(errorMessage);
	}

}
