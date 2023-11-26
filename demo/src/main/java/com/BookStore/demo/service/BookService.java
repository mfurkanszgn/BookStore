package com.BookStore.demo.service;

import com.BookStore.demo.entity.Book;
import com.BookStore.demo.exception.BookNotFoundException;
import com.BookStore.demo.repository.BookRepository;
import com.mysql.cj.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BookService {

	private BookRepository bookRepository;

	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public Page<Book> getAllBooks(Pageable pageable) {
		return bookRepository.findAll(pageable);
	}

	public Book getBookByIsbn(String isbn) {
		return bookRepository.findByIsbn(isbn);
	}

	public Book addBook(Book book) {
		return bookRepository.save(book);
	}

	public Book updateBook(String isbn, Book book) {
		Book existingBook = bookRepository.findByIsbn(isbn);
		if (existingBook == null) {
			throw new BookNotFoundException("Book not found with ISBN: " + isbn);
		}

		if (!StringUtils.isNullOrEmpty(book.getTitle())) {
			existingBook.setTitle(book.getTitle());
		}
		if (!StringUtils.isNullOrEmpty(book.getAuthor())) {
			existingBook.setTitle(book.getAuthor());
		}
		if (!StringUtils.isNullOrEmpty(String.valueOf(book.getPublicationDate()))) {
			existingBook.setPublicationDate(book.getPublicationDate());
		}
		if (!StringUtils.isNullOrEmpty(String.valueOf(book.getPrice()))) {
			existingBook.setPrice(book.getPrice());
		}
		if (book.getStockQuantity() != null) {
			existingBook.setStockQuantity(Integer.valueOf(book.getStockQuantity()));
		}

		return bookRepository.save(existingBook);
	}


	public void deleteBook(String isbn) {
		bookRepository.deleteByIsbn(isbn);
	}
}