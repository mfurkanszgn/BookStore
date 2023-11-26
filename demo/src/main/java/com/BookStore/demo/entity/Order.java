package com.BookStore.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "order_date")
	private LocalDate orderDate;

	@Column(name = "total_price")
	private Double totalPrice;

	@ManyToMany
	@JoinTable(
			name = "order_books",
			joinColumns = @JoinColumn(name = "order_id"),
			inverseJoinColumns = @JoinColumn(name = "book_id")
	)
	private List<Book> books;

	@Column(name = "created_at")
	private LocalDate createdAt;

	@Column(name = "updated_at")
	private LocalDate updatedAt;

	public Order(User user, LocalDate now, double totalPrice, List<Book> books) {
		this.user = user;
		this.orderDate = now;
		this.totalPrice = totalPrice;
		this.books = books;
		this.createdAt = now;
		this.updatedAt = now;
	}
}