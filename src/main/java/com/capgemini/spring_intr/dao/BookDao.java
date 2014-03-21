package com.capgemini.spring_intr.dao;

import java.util.List;

import com.capgemini.spring_intr.model.Book;

public interface BookDao {

	void saveBook(Book book);

	List<Book> searchForBooks(SearchCriteria searchCriteria);

	Book getBook(long bookId);
}
