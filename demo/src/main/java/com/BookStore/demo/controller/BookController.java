package com.BookStore.demo.controller;

import com.BookStore.demo.entity.Book;
import com.BookStore.demo.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/book")
public class BookController {

	private BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@GetMapping("/getBooks")
	public Page<Book> getAllBooks(@RequestParam Integer pageSize, @RequestParam Integer page) {
		Pageable pageable = PageRequest.of(page, pageSize);
		return bookService.getAllBooks(pageable);
	}

	@GetMapping("/getBook/{isbn}")
	public Book getBookByIsbn(@PathVariable String isbn) {
		return bookService.getBookByIsbn(isbn);
	}

	@PostMapping("/addBook")
	public Book addBook(@RequestBody Book book) {
		return bookService.addBook(book);

	}

	@PatchMapping("/updateBook/{isbn}")
	public Book updateBook(@PathVariable String isbn, @RequestBody Book book) {
		return bookService.updateBook(isbn, book);
	}

	@DeleteMapping("/deleteBook/{isbn}")
	public void deleteBook(@PathVariable String isbn) {
		bookService.deleteBook(isbn);
	}
}
