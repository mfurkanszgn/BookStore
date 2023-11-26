package com.BookStore.demo.service;

import com.BookStore.demo.entity.Book;
import com.BookStore.demo.entity.Order;
import com.BookStore.demo.entity.User;
import com.BookStore.demo.exception.BookstoreException;
import com.BookStore.demo.repository.BookRepository;
import com.BookStore.demo.repository.OrderRepository;
import com.BookStore.demo.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

	private OrderRepository orderRepository;

	private UserRepository userRepository;

	private BookRepository bookRepository;

	public OrderService(OrderRepository orderRepository, UserRepository userRepository, BookRepository bookRepository) {
		this.orderRepository = orderRepository;
		this.userRepository = userRepository;
		this.bookRepository = bookRepository;
	}

	public Order placeOrder(Long userId, List<String> bookIsbns) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new BookstoreException(HttpStatus.BAD_REQUEST, "User not found with ID: " + userId));

		List<Book> books = bookRepository.findAllByIsbnIn(bookIsbns);
		if (books.isEmpty()) {
			throw new BookstoreException(HttpStatus.BAD_REQUEST, "No books found with the provided ISBN numbers.");
		}

		List<Book> availableBooks = new ArrayList<>();
		for (Book book : books) {
			if (book.getStockQuantity() != null && book.getStockQuantity() > 0) {
				availableBooks.add(book);
				book.setStockQuantity(book.getStockQuantity() - 1);
			} else {
				throw new BookstoreException(HttpStatus.BAD_REQUEST,
						"Book with ISBN " + book.getIsbn() + " Title:" + book.getTitle() + "is out of stock.");
			}
		}
		bookRepository.saveAll(availableBooks);

		double totalPrice = availableBooks.stream().mapToDouble(Book::getPrice).sum();
		if (totalPrice < 25) {
			throw new BookstoreException(HttpStatus.BAD_REQUEST, "Total price should be at least 25$.");
		}

		Order order = new Order(user, LocalDate.now(), totalPrice, books);
		return orderRepository.save(order);
	}

	public List<Order> getAllOrdersByUserId(Long userId) {
		return orderRepository.findAllByUserId(userId);
	}

	public Order getOrderDetailsById(Long orderId) {
		return orderRepository.findOrderDetailsById(orderId);
	}
}
