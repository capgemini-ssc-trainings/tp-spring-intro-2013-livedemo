package com.capgemini.spring_intr.dao;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.capgemini.spring_intr.model.Book;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@Transactional
public class BookDaoIntegrationTest {
	private static final String SOME_TITLE = "Some title";

	private static final String SOME_AUTHORS = "Some authors";

	@Autowired
	private BookDao bookDao;

	@Autowired
	private SessionFactory sessionFactory;

	@Test
	public void shouldInsertBookWhenAllPropertiesSet() {
		// given
		Book book = new Book(SOME_AUTHORS, SOME_TITLE);
		// when
		bookDao.saveBook(book);
		// then
		@SuppressWarnings("unchecked")
		List<Book> booksSaved = sessionFactory.getCurrentSession()
				.createCriteria(Book.class).list();
		assertEquals(1, booksSaved.size());
		Book bookSaved = booksSaved.get(0);
		assertEquals(SOME_AUTHORS, bookSaved.getAuthors());
		assertEquals(SOME_TITLE, bookSaved.getTitle());
	}
	
	@Test
	public void shouldGetBook() {
		// given
		Book bookOnHibernate = new Book("Gavin King", "Hibernate in Action");
		bookDao.saveBook(bookOnHibernate);
		long bookId = bookOnHibernate.getId();
		// when
		Book bookLoaded = bookDao.getBook(bookId);
		// then
		assertEquals(bookOnHibernate, bookLoaded);
	}
	
	@Test
	public void shouldUpdateBookWhenAllPropertiesSet() {
		// given
		Book bookOnHibernate = new Book("Gavin King", "Hibernate in Action");
		bookDao.saveBook(bookOnHibernate);
		// when
		bookOnHibernate.setTitle("Java Persistence with Hibernate");
		bookDao.saveBook(bookOnHibernate);
		// then
		Book updatedBook = bookDao.getBook(bookOnHibernate.getId());
		assertEquals("Java Persistence with Hibernate", updatedBook.getTitle());
	}

	@Test(expected=ConstraintViolationException.class)
	public void shouldRejectInsertingWhenAuthorsAreNull() {
		// given
		Book book = new Book(null, SOME_TITLE);
		// when
		bookDao.saveBook(book);
	}
	
	@Test(expected=ConstraintViolationException.class)
	public void shouldRejectInsertingWhenAuthorsAreEmpty() {
		// given
		Book book = new Book("", SOME_TITLE);
		// when
		bookDao.saveBook(book);
	}
	
	@Test(expected=ConstraintViolationException.class)
	public void shouldRejectInsertingWhenTitleIsNull() {
		// given
		Book book = new Book(SOME_AUTHORS, null);
		// when
		bookDao.saveBook(book);
	}
	
	@Test(expected=ConstraintViolationException.class)
	public void shouldRejectInsertingWhenTitleIsEmpty() {
		// given
		Book book = new Book(SOME_AUTHORS, "");
		// when
		bookDao.saveBook(book);
	}
	
	@Test
	public void shouldReturnMatchingBooksWhenFindingByAuthors() {
		// given
		Book bookOnHibernate = new Book("Gavin King", "Hibernate in Action");
		bookDao.saveBook(bookOnHibernate);
		Book bookOnSpring= new Book("Craig Walls", "Spring in Action");
		bookDao.saveBook(bookOnSpring);
		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.setAuthors("king");
		// when
		List<Book> matchingBooks = bookDao.searchForBooks(searchCriteria);
		// then
		assertEquals(1, matchingBooks.size());
		Book bookFound = matchingBooks.get(0);
		assertEquals(bookOnHibernate, bookFound);
		
	}
	
	@Test
	public void shouldReturnMatchingBooksWhenFindingByTitle() {
		// given
		Book bookOnHibernate = new Book("Gavin King", "Hibernate in Action");
		bookDao.saveBook(bookOnHibernate);
		Book bookOnSpring= new Book("Craig Walls", "Spring in Action");
		bookDao.saveBook(bookOnSpring);
		SearchCriteria searchCriteria = new SearchCriteria();
		searchCriteria.setTitle("Spring");
		// when
		List<Book> matchingBooks = bookDao.searchForBooks(searchCriteria);
		// then
		assertEquals(1, matchingBooks.size());
		Book bookFound = matchingBooks.get(0);
		assertEquals(bookOnSpring, bookFound);
	}
}
