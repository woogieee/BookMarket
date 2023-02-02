package com.springmvc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.springmvc.domain.Book;
import com.springmvc.repository.BookRepository;

@Service
public class BookServiceImp1 implements BookService {
	@Autowired
	private BookRepository bookRepository;

	public List<Book> getAllBookList() {
		// 저장된 도서 목록 정보 가져오기
		return bookRepository.getAllBookList();
	}

}
