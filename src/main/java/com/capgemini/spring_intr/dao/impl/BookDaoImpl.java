package com.capgemini.spring_intr.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.spring_intr.dao.BookDao;
import com.capgemini.spring_intr.dao.SearchCriteria;
import com.capgemini.spring_intr.model.Book;

@Repository
@Transactional
public class BookDaoImpl implements BookDao {
	@Autowired
	private SessionFactory sessionFactory;

	public void saveBook(Book book) {
		sessionFactory.getCurrentSession().saveOrUpdate(book);
	}

	@SuppressWarnings("unchecked")
	public List<Book> searchForBooks(SearchCriteria searchCriteria) {
		Criteria searchQuery = sessionFactory.getCurrentSession()
				.createCriteria(Book.class);

		if (searchCriteria.hasAuthors())
			searchQuery.add(Restrictions.ilike(Book.AUTHORS_PROP,
					searchCriteria.getAuthors(), MatchMode.ANYWHERE));

		if (searchCriteria.hasTitle())
			searchQuery.add(Restrictions.ilike(Book.TITLE_PROP,
					searchCriteria.getTitle(), MatchMode.ANYWHERE));

		return searchQuery.list();
	}

	public Book getBook(long bookId) {
		Book book = (Book) sessionFactory.getCurrentSession().load(Book.class,
				bookId);
		Hibernate.initialize(book);
		return book;
	}
}
