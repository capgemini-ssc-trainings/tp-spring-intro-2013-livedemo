package com.capgemini.spring_intr.controller;

import static com.capgemini.spring_intr.controller.BookSearchController.SEARCH_CRITERIA_MODEL_NAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.capgemini.spring_intr.dao.BookDao;
import com.capgemini.spring_intr.dao.SearchCriteria;
import com.capgemini.spring_intr.model.Book;

public class BookSearchControllerTest {
	@Test
	public void shouldDirectToShowSearchCriteriaWhenSearchSelected() {
		// given
		BookSearchController controller = new BookSearchController();
		Model model = mock(Model.class);
		when(model.containsAttribute(SEARCH_CRITERIA_MODEL_NAME)) //
				.thenReturn(false);
		// when
		String viewName = controller.showSearchCriteria(model);
		// then
		assertEquals(BookSearchController.SEARCH_CRITERIA_VIEW_NAME, viewName);
		verify(model).addAttribute(SEARCH_CRITERIA_MODEL_NAME, new SearchCriteria());
	}

	@Test
	public void shouldRedirectToShowMatchedBooksWhenSearchButtonPressed() {
		// given
		BookSearchController controller = new BookSearchController();
		SearchCriteria criteria = new SearchCriteria();
		// when
		String viewName = controller.searchForBooks(criteria);
		// then
		assertEquals("redirect:showMatchedBooks", viewName);
	}

	@Test
	public void shouldDelegateToBookDaoWhenShowingMatchedBooksRequested() {
		// given
		BookSearchController controller = new BookSearchController();
		SearchCriteria criteria = new SearchCriteria();
		BookDao bookDao = mock(BookDao.class);
		List<Book> matchingList = Collections.<Book> emptyList();
		org.mockito.Mockito.when(bookDao.searchForBooks(criteria)).thenReturn(
				matchingList);
		controller.setBookDao(bookDao);
		// when
		ModelAndView modelAndView = controller.showMatchedBooks(criteria);
		// then
		verify(bookDao).searchForBooks(criteria);
		assertEquals(BookSearchController.BOOK_LIST_VIEW_NAME,
				modelAndView.getViewName());
		ModelMap model = modelAndView.getModelMap();
		assertTrue(model
				.containsAttribute(BookSearchController.MATCHING_BOOKS_MODEL_NAME));
		assertTrue(model.containsValue(matchingList));
	}
}
