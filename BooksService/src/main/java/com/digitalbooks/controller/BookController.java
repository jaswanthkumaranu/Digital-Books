package com.digitalbooks.controller;

import static com.digitalbooks.utility.BooksConstant.DATA_MISSING;
import static com.digitalbooks.utility.BooksConstant.NOT_SUSBSCRIBED;
import static com.digitalbooks.utility.BooksConstant.SOMETHING_WENT_WRONG_PLESE_TRY_AFTER_SOME_TIME;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.digitalbooks.dto.ReaderVo;
import com.digitalbooks.model.BookVo;
import com.digitalbooks.response.MessageResponse;
import com.digitalbooks.service.BookService;
import com.digitalbooks.utility.BookServiceExceptionHandler;

@RestController
@RequestMapping("/book")
@CrossOrigin
public class BookController {
	@Autowired
	private BookService bookService;

	@PostMapping(value = "/author/{authorId}/books")
	public ResponseEntity<?> createBook(@RequestBody BookVo book, @PathVariable String authorId)
			throws BookServiceExceptionHandler {
		return ResponseEntity.ok(bookService.createBook(book, authorId));

	}

	@PostMapping(value = "/author/{authorId}/books/{bookId}")
	public ResponseEntity<?> updateBook(@RequestBody BookVo book, @PathVariable String authorId,
			@PathVariable String bookId) throws BookServiceExceptionHandler {
		return ResponseEntity.ok(bookService.updateBook(book, authorId, bookId));
	}

	@GetMapping("/search")
	public ResponseEntity<?> searchBooksNew(@RequestParam(name = "category", defaultValue = "") String category,
			@RequestParam(name = "title", defaultValue = "") String title,
			@RequestParam(name = "author", defaultValue = "") String author,
			@RequestParam(name = "price", defaultValue = "0") String price,
			@RequestParam(name = "publisher", defaultValue = "") String publisher) throws BookServiceExceptionHandler {
		List<BookVo> books = null;
		try {
			books = bookService.searchBook(category, title, author, price, publisher);
//			ObjectMapper mapper = new ObjectMapper();
//	        mapper.enable(SerializationFeature.INDENT_OUTPUT);
//			 String json = mapper.writeValueAsString(books);
			return ResponseEntity.status(200).body(books);

		} catch (Exception e) {
			throw new BookServiceExceptionHandler("Search ERROR()--->", e);
		}

	}

	@PostMapping("/{bookId}/subscribe")
	public ResponseEntity<?> subscribeBook(@RequestBody ReaderVo reader, @PathVariable String bookId)
			throws BookServiceExceptionHandler {
		try {
			return ResponseEntity.ok(bookService.subscribeBook(bookId, reader));

		} catch (Exception e) {
			throw new BookServiceExceptionHandler(NOT_SUSBSCRIBED, e);
		}
	}

	@GetMapping("/readers/{emailId}/books")
	public ResponseEntity<?> getAllSubscribeBooksByReader(@PathVariable String emailId)
			throws BookServiceExceptionHandler {
		if (emailId != null && !emailId.equalsIgnoreCase("")) {
			try {
				List<BookVo> subscribBooksByReader = bookService.getAllSubscribeBooksByReader(emailId);
				return ResponseEntity.status(200).body(subscribBooksByReader);
			} catch (Exception e) {
				throw new BookServiceExceptionHandler(SOMETHING_WENT_WRONG_PLESE_TRY_AFTER_SOME_TIME);
			}
		} else {
			throw new BookServiceExceptionHandler(DATA_MISSING);
		}
	}

	@GetMapping("/readers/{emailId}/books/{subscriptionId}")
	public ResponseEntity<?> getSubscribeBookByReaderEmailId(@PathVariable String emailId,
			@PathVariable String subscriptionId) throws BookServiceExceptionHandler {
		if (emailId != null && !emailId.equalsIgnoreCase("")) {
			try {
				BookVo subscribBookByReader = bookService.getSubscribeBookByReader(emailId, subscriptionId);
				List<BookVo> books = new ArrayList<>();
				books.add(subscribBookByReader);
				return ResponseEntity.status(200).body(books);
			} catch (Exception e) {
				throw new BookServiceExceptionHandler(SOMETHING_WENT_WRONG_PLESE_TRY_AFTER_SOME_TIME, e);
			}
		} else {
			throw new BookServiceExceptionHandler(DATA_MISSING);
		}
	}

	@GetMapping("/readers/{emailId}/books/{subscriptionId}/read")
	public ResponseEntity<?> getSubscribeBookContentByReaderEmailId(@PathVariable String emailId,
			@PathVariable String subscriptionId) throws BookServiceExceptionHandler {
		if (emailId != null && !emailId.equalsIgnoreCase("")) {
			try {
				BookVo subscribBookByReader = bookService.getSubscribeBookByReader(emailId, subscriptionId);
				return ResponseEntity.ok(new MessageResponse(subscribBookByReader.getBookContent()));
			} catch (Exception e) {
				throw new BookServiceExceptionHandler(SOMETHING_WENT_WRONG_PLESE_TRY_AFTER_SOME_TIME);
			}
		} else {
			throw new BookServiceExceptionHandler(DATA_MISSING);
		}
	}

	@PostMapping("/readers/{readerId}/books/{subscriptionId}/cancel-subscription")
	public ResponseEntity<?> cancleSubscriptionWithIn24Hours(@PathVariable String readerId,
			@PathVariable String subscriptionId) throws BookServiceExceptionHandler {
		if (readerId != null && !readerId.equalsIgnoreCase("") && !subscriptionId.equalsIgnoreCase("")
				&& subscriptionId != null) {
			return ResponseEntity.ok(bookService.cancleSubscriptionWithIn24Hours(readerId, subscriptionId));

		} else {
			return ResponseEntity.ok(DATA_MISSING);
		}
	}

	@PostMapping("author/{authorId}/books/{bookId}/block={block}")
	public ResponseEntity<?> blockOrUnBlockBookByAuthor(@PathVariable(value = "authorId") String authorId,
			@PathVariable(value = "bookId") String bookId, @PathVariable(value = "block") String block)
			throws BookServiceExceptionHandler {
		if (block != null && !block.equalsIgnoreCase("") && authorId != null && bookId != null) {
			try {
				return ResponseEntity.ok(bookService.blockOrUnBlockBookByAuthor(authorId, bookId, block));
			} catch (Exception e) {
				throw new BookServiceExceptionHandler(SOMETHING_WENT_WRONG_PLESE_TRY_AFTER_SOME_TIME, e);
			}
		} else {
			return ResponseEntity.status(400)
					.body("Data Missing!.. authorId:" + authorId + "bookId:" + bookId + "block:" + block);
		}
	}

	@GetMapping("/author/{authorId}/books")
	public ResponseEntity<?> getAuthorCreatedBooks(@PathVariable String authorId) throws BookServiceExceptionHandler {
		if (authorId != null && !authorId.equalsIgnoreCase("")) {
			try {
				List<BookVo> subscribBooksByReader = bookService.getAuthorCreatedBooks(authorId);
				return ResponseEntity.status(200).body(subscribBooksByReader);
			} catch (Exception e) {
				throw new BookServiceExceptionHandler(SOMETHING_WENT_WRONG_PLESE_TRY_AFTER_SOME_TIME);
			}
		} else {
			throw new BookServiceExceptionHandler(DATA_MISSING);
		}
	}

	@GetMapping("/author/{authorId}/books/{bookId}")
	public ResponseEntity<?> getAuthorCreatedBook(@PathVariable String authorId, @PathVariable String bookId)
			throws BookServiceExceptionHandler {
		if (authorId != null && !authorId.equalsIgnoreCase("")) {
			try {
				List<BookVo> subscribBooksByReader = bookService.getAuthorCreatedBook(authorId, bookId);
				return ResponseEntity.status(200).body(subscribBooksByReader);
			} catch (Exception e) {
				throw new BookServiceExceptionHandler(SOMETHING_WENT_WRONG_PLESE_TRY_AFTER_SOME_TIME);
			}
		} else {
			throw new BookServiceExceptionHandler(DATA_MISSING);
		}
	}

}
