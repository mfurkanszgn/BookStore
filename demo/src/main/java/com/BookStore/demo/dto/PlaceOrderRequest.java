package com.BookStore.demo.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlaceOrderRequest {
	private Long userId;
	private List<String> bookIsbns;

}