package com.digitalbooks.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.digitalbooks.model.BookVo;

public interface BookRepository extends JpaRepository<BookVo, Long> {

	List<BookVo> findAll();

	List<BookVo> findByCategoryOrBookTitleOrAuthorIdOrPriceOrPublisher(String category, String title, String author,
			Double price, String publisher);

	List<BookVo> findByCategoryAndBookTitle(String category, String title);

	List<BookVo> findByCategoryAndAuthorId(String category, String author);

	List<BookVo> findByCategoryAndPrice(String category, Double price);

	List<BookVo> findByCategoryAndPublisher(String category, String publisher);

//	List<BookVo> findByCategoryAndBookTitleAndAuthorId(String category, String title, String author);
//
//	List<BookVo> findByCategoryAndBookTitleAndPrice(String category, String title, Double price);
//
//	List<BookVo> findByCategoryAndBookTitleAndPublisher(String category, String title, String publisher);
//
//	List<BookVo> findByCategoryAndAuthorIdAndPrice(String category, String author, Double price);
//
//	List<BookVo> findByCategoryAndAuthorIdAndPublisher(String category, String author, String publisher);
//
//	List<BookVo> findByCategoryAndPriceAndPublisher(String category, Double price, String publisher);
//
//	List<BookVo> findByBookTitleAndAuthorIdAndPrice(String title, String author, Double price);
//
//	List<BookVo> findByBookTitleAndPriceAndPublisher(String title, String author, Double price, String publisher);
//
//	List<BookVo> findByCategoryAndBookTitleAndAuthorIdAndPrice(String category, String title, String author,
//			Double price);
//
//	List<BookVo> findByCategoryAndBookTitleAndAuthorIdAndPublisher(String category, String title, String author,
//			String publisher);

	@Query(value = "select * from tbl_book b where block=0 and b.category=?1 and b.book_title =?2 and b.author_id=?3 and b.price=?4 and b.publisher=?5", nativeQuery = true)
	List<BookVo> searchBook(String category, String title, String author, String price, String publisher);

	@Override
	default List<BookVo> findAllById(Iterable<Long> ids) {
		List<BookVo> books = new ArrayList<>();
		for (Long id : ids) {
			Optional<BookVo> optionalBooks = findById(id);
			if (!optionalBooks.isEmpty())
				books.add(optionalBooks.get());
		}

		return books;
	}

	boolean existsByAuthorIdAndBookTitle(String authorId, String bookTitle);

}
