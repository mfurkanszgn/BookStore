package com.BookStore.demo.repository;

import com.BookStore.demo.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

	Book findByIsbn(String isbn);
	List<Book> findAllByIsbnIn(List<String> bookIsbns);
	@Transactional
	long deleteByIsbn(String isbn);

}