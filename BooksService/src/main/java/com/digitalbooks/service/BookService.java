package com.digitalbooks.service;

import java.util.List;

import com.digitalbooks.dto.ReaderVo;
import com.digitalbooks.model.BookVo;
import com.digitalbooks.response.MessageResponse;
import com.digitalbooks.utility.BookServiceExceptionHandler;

public interface BookService {

	MessageResponse createBook(BookVo book, String authorId);

	BookVo updateBook(BookVo book, String authorId,String bookId) throws BookServiceExceptionHandler;

	List<BookVo> searchBook(String category, String title, String author, String price, String publisher);

	boolean subscribeBook(String bookId, ReaderVo reader) throws BookServiceExceptionHandler;

	boolean blockOrUnBlockBookByAuthor(String authorId, String bookId, String block) throws BookServiceExceptionHandler;

	boolean cancleSubscriptionWithIn24Hours(String readerId, String subscriptionId) throws BookServiceExceptionHandler;

	List<BookVo> getAllSubscribeBooksByReader(String emailId);

	BookVo getSubscribeBookByReader(String emailId, String subscriptionId) throws BookServiceExceptionHandler;

}
