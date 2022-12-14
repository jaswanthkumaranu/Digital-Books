package com.digitalbooks.service;

import static com.digitalbooks.utility.BooksConstant.ACCESS_DENIED;
import static com.digitalbooks.utility.BooksConstant.AUTHOR_ID_IS_NOT_MATCHING_WITH_THIS_BOOK;
import static com.digitalbooks.utility.BooksConstant.BOOK_IS_ALREADY_SUBSCRIBED;
import static com.digitalbooks.utility.BooksConstant.CAN_NOT_CANCEL_SUBSCRIPTION_AFTER_24_HOURS;
import static com.digitalbooks.utility.BooksConstant.CAN_NOT_FIND_BOOK_WITH_ID;
import static com.digitalbooks.utility.BooksConstant.CAN_NOT_FIND_THE_BOOK;
import static com.digitalbooks.utility.BooksConstant.CAN_NOT_FIND_THE_SUBSCRIPTION_FOR_THE_BOOK_WITH_ID;
import static com.digitalbooks.utility.BooksConstant.CAN_NOT_FIND_THE_SUBSCRIPTION_ID;
import static com.digitalbooks.utility.BooksConstant.DATA_MISSING;
import static com.digitalbooks.utility.BooksConstant.SUBSCRIPTION_CANCLE_SUCESSFULLY;
import static com.digitalbooks.utility.BooksConstant.SUCCESSFULLY_BLOCKED;
import static com.digitalbooks.utility.BooksConstant.SUCCESSFULLY_SUBSCRIBED;
import static com.digitalbooks.utility.BooksConstant.SUCCESSFULLY_UNBLOCKED;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.digitalbooks.dto.ReaderVo;
import com.digitalbooks.model.BookVo;
import com.digitalbooks.model.SubscribeBookVo;
import com.digitalbooks.repository.BookRepository;
import com.digitalbooks.repository.SubscribeBookRepository;
import com.digitalbooks.response.MessageResponse;
import com.digitalbooks.utility.BookServiceExceptionHandler;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;

	@Autowired
	private SubscribeBookRepository subscribeBookRepository;

	@Override
	public MessageResponse createBook(BookVo book, String authorId) {
		if (bookRepository.existsByAuthorIdAndBookTitle(authorId, book.getBookTitle())) {
			return new MessageResponse("Book with same title exists!");
		}
		try {
			book.setCreatedDt(new Date());
			if (book.getBlock() == null || book.getBlock() > 1)
				book.setBlock(0L);
			book.setSubscriptionCount(0L);
			if (book.getPrice() == null || book.getPrice() < 0)
				book.setPrice(0.0);
			book.setBookId(0L);
			book.setCrtUsr(Long.parseLong(authorId));
			book.setAuthorId(authorId);
			book.setCreatedDt(new Date());
			if (book.getBookCode() == null || book.getBookCode().equalsIgnoreCase(""))
				book.setBookCode(book.getBookTitle().substring(0, 3));
			bookRepository.save(book);
			System.out.println("calling book service" + book.getBookTitle());
		} catch (Exception e) {
			return new MessageResponse("Error: " + e.getMessage());
		}
		return new MessageResponse("Book added successfully!");
	}

	@Override
	public BookVo updateBook(BookVo book, String authorId, String bookId) throws BookServiceExceptionHandler {
		if (bookId != null && !bookId.equalsIgnoreCase("")) {
			Optional<BookVo> oBook = bookRepository.findById(Long.parseLong(bookId));
			if (!oBook.isEmpty() && oBook.get().getAuthorId().equalsIgnoreCase(authorId)) {
				if (book.getLogo() != null)
					oBook.get().setLogo(book.getLogo());
				if (book.getBookTitle() != null && !book.getBookTitle().equalsIgnoreCase(""))
					oBook.get().setBookTitle(book.getBookTitle());
				if (book.getCategory() != null && !book.getCategory().equalsIgnoreCase(""))
					oBook.get().setCategory(book.getCategory());
				if (book.getPrice() != null && book.getPrice() >= 0)
					oBook.get().setPrice(book.getPrice());
				if (book.getAuthorId() != null && !book.getAuthorId().equalsIgnoreCase(""))
					oBook.get().setAuthorId(book.getAuthorId());
				if (book.getPublisher() != null && !book.getPublisher().equalsIgnoreCase(""))
					oBook.get().setPublisher(book.getPublisher());
				if (book.getPublisher() != null && !book.getPublisher().equalsIgnoreCase(""))
					oBook.get().setPublisher(book.getPublisher());
				if (book.getBookContent() != null && !book.getBookContent().equalsIgnoreCase(""))
					oBook.get().setBookContent(book.getBookContent());
				if (book.getIsActive() != null && book.getIsActive() > 2 && book.getIsActive() <= 0)
					oBook.get().setIsActive(book.getIsActive());
				oBook.get().setUpdatedDt(new Date());

				return bookRepository.save(oBook.get());
			} else {
				throw new BookServiceExceptionHandler(AUTHOR_ID_IS_NOT_MATCHING_WITH_THIS_BOOK);
			}

		} else {
			throw new BookServiceExceptionHandler(CAN_NOT_FIND_BOOK_WITH_ID + bookId);
		}
	}

	@Override
	public List<BookVo> searchBook(String category, String title, String author, String price, String publisher) {
		List<BookVo> books = null;
		if (!category.isEmpty() && !title.isEmpty() && !author.isEmpty() && !price.isEmpty() && !publisher.isEmpty()) {
			books = bookRepository.searchBook(category, title, author, price, publisher);
		}
		if (!category.isEmpty() || !title.isEmpty() || !author.isEmpty() || !price.isEmpty() || !publisher.isEmpty()) {
			// for single value search
			books = bookRepository.findByCategoryOrBookTitleOrAuthorIdOrPriceOrPublisher(category, title, author,
					Double.parseDouble(price), publisher);
		}
		if (category.isEmpty() && title.isEmpty() && author.isEmpty() && price.isEmpty() && publisher.isEmpty()) {
			books = bookRepository.findAll();
		}
		if (!category.isEmpty() && !title.isEmpty() && author.isEmpty() && price.isEmpty() && publisher.isEmpty()) {
			books = bookRepository.findByCategoryAndBookTitle(category, title);
		}

		if (!category.isEmpty() && !title.isEmpty() && !author.isEmpty() && price.isEmpty() && publisher.isEmpty()) {
//			books = bookRepository.findByCategoryAndBookTitleAndAuthorId(category, title, author);
		}

		if (!category.isEmpty() && !title.isEmpty() && !author.isEmpty() && !price.isEmpty() && publisher.isEmpty()) {
//			books = bookRepository.findByCategoryAndBookTitleAndAuthorIdAndPrice(category, title, author,
//					Double.parseDouble(price));
		}
		for (int i = 0; i < books.size(); i++) {
			if (books.get(i)!=null&&books.get(i).getBlock()!=null&&books.get(i).getBlock().equals(1L)) {
				books.remove(i);
			}
		}
		return books;

	}

	@Override
	public MessageResponse subscribeBook(String bookId, ReaderVo reader) throws BookServiceExceptionHandler {
		if (bookId != null && !bookId.equalsIgnoreCase("")) {
			Optional<BookVo> book = bookRepository.findById(Long.parseLong(bookId));
			if (!book.isEmpty()) {
				List<SubscribeBookVo> books = subscribeBookRepository.findByReaderId(reader.getReaderId());
				boolean notsubscribe = true;
				for (SubscribeBookVo subbook : books) {
					if (subbook.getBookId() == Long.parseLong(bookId) && subbook.getIsActive() == 1L) {
						notsubscribe = false;
					}
				}
				if (notsubscribe) {
					SubscribeBookVo sBook = new SubscribeBookVo();

					sBook.setBookId(Long.parseLong(bookId));
					sBook.setReaderId(reader.getReaderId());
					sBook.setEmailId(reader.getReaderEmail());
					sBook.setSubscriptionDt(new Date());
					sBook.setIsActive(1L);
					subscribeBookRepository.save(sBook);

					Long count = book.get().getSubscriptionCount();
					book.get().setSubscriptionCount(count + 1);
					bookRepository.save(book.get());

					return new MessageResponse(SUCCESSFULLY_SUBSCRIBED);
				} else {
					return new MessageResponse(BOOK_IS_ALREADY_SUBSCRIBED + bookId);
				}
			} else {
				return new MessageResponse(CAN_NOT_FIND_BOOK_WITH_ID + bookId);
			}

		} else {
			return new MessageResponse(CAN_NOT_FIND_BOOK_WITH_ID + bookId);
		}

	}

	@Override
	public MessageResponse blockOrUnBlockBookByAuthor(String authorId, String bookId, String block)
			throws BookServiceExceptionHandler {
		if (bookId != null && !bookId.equalsIgnoreCase("")) {
			Optional<BookVo> book = bookRepository.findById(Long.parseLong(bookId));
			if (!book.isEmpty() && book.get().getAuthorId().equalsIgnoreCase(authorId)) {
				if (block != null && (block.equalsIgnoreCase("Yes") || block.equalsIgnoreCase("0")))
					book.get().setBlock(1L);
				else
					book.get().setBlock(0L);
				bookRepository.save(book.get());
				if (block.equalsIgnoreCase("Yes") || block.equalsIgnoreCase("0"))
					return new MessageResponse(SUCCESSFULLY_BLOCKED);
				else
					return new MessageResponse(SUCCESSFULLY_UNBLOCKED);
			} else {
				throw new BookServiceExceptionHandler(CAN_NOT_FIND_BOOK_WITH_ID + bookId);
			}

		} else {
			throw new BookServiceExceptionHandler(CAN_NOT_FIND_BOOK_WITH_ID + bookId);
		}
	}

	@Override
	public MessageResponse cancleSubscriptionWithIn24Hours(String readerId, String subscriptionId)
			throws BookServiceExceptionHandler {

		if (subscriptionId != null && !subscriptionId.equalsIgnoreCase("")) {
			Optional<SubscribeBookVo> subscribeBook = subscribeBookRepository.findById(Long.parseLong(subscriptionId));

			if (!subscribeBook.isEmpty() && subscribeBook.get().getReaderId().equalsIgnoreCase(readerId)) {
				Date presentDate = new Date();
				;
				Date subscribDt = subscribeBook.get().getSubscriptionDt();
				long diffInHours = getDateDiff(subscribDt, presentDate, TimeUnit.HOURS);
				if (diffInHours < 24) {
					subscribeBook.get().setIsActive(0L);
					subscribeBookRepository.save(subscribeBook.get());
					Optional<BookVo> book = bookRepository.findById(subscribeBook.get().getBookId());
					Long count = !book.isEmpty() ? book.get().getSubscriptionCount() : 0;
					if (count > 0) {
						book.get().setSubscriptionCount(count - 1);
						bookRepository.save(book.get());
					}

					return new MessageResponse(SUBSCRIPTION_CANCLE_SUCESSFULLY);
				} else {
					return new MessageResponse(CAN_NOT_CANCEL_SUBSCRIPTION_AFTER_24_HOURS + subscriptionId);
				}
			} else {
				return new MessageResponse(ACCESS_DENIED + subscriptionId);
			}

		} else {
			return new MessageResponse(CAN_NOT_FIND_THE_SUBSCRIPTION_FOR_THE_BOOK_WITH_ID + subscriptionId);
		}

	}

	@Override
	public List<BookVo> getAllSubscribeBooksByReader(String emailId) {
		List<BookVo> books = null;
		List<SubscribeBookVo> subscribeBooksDtls = subscribeBookRepository.findByEmailId(emailId);
		if (subscribeBooksDtls != null && !subscribeBooksDtls.isEmpty()) {
			List<Long> bookIds = new ArrayList<>();
			for (SubscribeBookVo subscribeBook : subscribeBooksDtls) {
				if (subscribeBook.getIsActive() == 1L)
					bookIds.add(subscribeBook.getBookId());

			}

			books = bookRepository.findAllById(bookIds);

			for (int i = 0; i < books.size(); i++) {
				for (int j = 0; j < subscribeBooksDtls.size(); j++)
					if (books.get(i).getBookId().equals(subscribeBooksDtls.get(j).getBookId())) {
						books.get(i).setSubscriptionId(subscribeBooksDtls.get(j).getSubscribeId());
					}
			}
		}

		return books;
	}

	@Override
	public BookVo getSubscribeBookByReader(String emailId, String subscriptionId) throws BookServiceExceptionHandler {

		if (subscriptionId != null && !subscriptionId.equalsIgnoreCase("")) {
			Optional<SubscribeBookVo> subcribeBook = subscribeBookRepository.findById(Long.parseLong(subscriptionId));
			if (!subcribeBook.isEmpty() && subcribeBook.get() != null
					&& subcribeBook.get().getEmailId().equalsIgnoreCase(emailId)) {
				Optional<BookVo> optionalBook = bookRepository.findById(subcribeBook.get().getBookId());
				if (!optionalBook.isEmpty()) {
					return optionalBook.get();

				} else {
					throw new BookServiceExceptionHandler(CAN_NOT_FIND_THE_BOOK + subscriptionId);
				}
			} else {
				Optional<BookVo> opBook = bookRepository.findById(Long.parseLong(subscriptionId));
				if (!opBook.isEmpty()) {
					return opBook.get();

				} else {
					throw new BookServiceExceptionHandler(CAN_NOT_FIND_THE_BOOK + subscriptionId);
				}
			}

		} else {
			throw new BookServiceExceptionHandler(DATA_MISSING + subscriptionId);
		}

	}

	public static long getDateDiff(final Date date1, final Date date2, final TimeUnit timeUnit) {
		long diffInMillies = date2.getTime() - date1.getTime();

		return timeUnit.convert(diffInMillies, timeUnit.MILLISECONDS);
	}

	@Override
	public List<BookVo> getAuthorCreatedBooks(String authorId) throws BookServiceExceptionHandler {
		List<BookVo> books = null;
		if (bookRepository.existsByAuthorId(authorId)) {
			books = bookRepository.findByAuthorId(authorId);
		}

		return books;
	}
}
