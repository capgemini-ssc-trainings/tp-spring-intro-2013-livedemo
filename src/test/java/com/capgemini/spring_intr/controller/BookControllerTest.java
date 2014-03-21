package com.capgemini.spring_intr.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.support.SessionStatus;

import com.capgemini.spring_intr.dao.BookDao;
import com.capgemini.spring_intr.model.Book;

public class BookControllerTest {
	private static final long BOOK_ID = 122345L;

	@Test
	public void shouldGetBookAndAddItToModelWhenEditingBook() {
		// given
		BookController controller = new BookController();
		BookDao bookDao = mock(BookDao.class);
		Book book = new Book("Gavin King", "Hibernate in Action");
		when(bookDao.getBook(BOOK_ID)).thenReturn(book);
		controller.setBookDao(bookDao);
		Model model = mock(Model.class);
		// when
		String viewName = controller.showBookForEdit(BOOK_ID, model);
		// then
		verify(bookDao).getBook(BOOK_ID);
		verify(model).addAttribute(BookController.BOOK_MODEL_NAME, book);
		assertEquals(BookController.BOOK_EDIT_VIEW_NAME, viewName);
	}

	@Test
	public void shouldDelegateToBookDaoAndMarkSessionAsCompleteWhenSavePressedAndValidationSuccessful() {
		// given
		BookController controller = new BookController();
		BookDao bookDao = mock(BookDao.class);
		controller.setBookDao(bookDao);
		Book book = new Book("Gavin King", "Hibernate in Action");
		ReflectionTestUtils.setField(book, "id", BOOK_ID);
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(false);
		SessionStatus sessionStatus = mock(SessionStatus.class);
		// when
		String viewName = controller.saveBook(book, bindingResult, sessionStatus);
		// then
		verify(bookDao).saveBook(book);
		verify(sessionStatus).setComplete();
		assertEquals("redirect:showBookForEdit?bookId=" + BOOK_ID, viewName);
	}
	
	@Test
	public void shouldGoBackToEditBookViewWhenSavePressedAndValidationFailed() {
		// given
		BookController controller = new BookController();
		BookDao bookDao = mock(BookDao.class);
		controller.setBookDao(bookDao);
		Book book = new Book("Gavin King", "Hibernate in Action");
		ReflectionTestUtils.setField(book, "id", BOOK_ID);
		BindingResult bindingResult = mock(BindingResult.class);
		when(bindingResult.hasErrors()).thenReturn(true);
		SessionStatus sessionStatus = mock(SessionStatus.class);
		// when
		String viewName = controller.saveBook(book, bindingResult, sessionStatus);
		// then
		verifyZeroInteractions(bookDao);
		verifyZeroInteractions(sessionStatus);
		assertEquals(BookController.BOOK_EDIT_VIEW_NAME, viewName);
	}
	
	@Test
	public void shouldCreateNewBookInitiallyAndAddItToModelWhenAddingNewBook() {
		// given
		BookController controller = new BookController();
		Model model = mock(Model.class);
		// when
		String viewName = controller.addNewBook(model);
		// then
		verify(model).addAttribute(BookController.BOOK_MODEL_NAME, new Book());
		assertEquals(BookController.BOOK_EDIT_VIEW_NAME, viewName);
	}
}
