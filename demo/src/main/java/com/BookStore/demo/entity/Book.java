package com.BookStore.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "isbn", unique = true, nullable = false)
	@NotBlank(message = "ISBN number can not be blank")
	private String isbn;

	@Column(name = "title")
	private String title;

	@Column(name = "author")
	private String author;

	@Column(name = "price")
	private Double price;

	@Column(name = "publication_date")
	private LocalDate publicationDate;

	@Column(name = "stock_quantity")
	private Integer stockQuantity;
}
