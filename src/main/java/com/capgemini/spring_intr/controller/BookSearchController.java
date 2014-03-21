package com.capgemini.spring_intr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.capgemini.spring_intr.dao.BookDao;
import com.capgemini.spring_intr.dao.SearchCriteria;
import com.capgemini.spring_intr.model.Book;

@Controller
@SessionAttributes(BookSearchController.SEARCH_CRITERIA_MODEL_NAME)
public class BookSearchController {
	public static final String SEARCH_CRITERIA_MODEL_NAME = "searchCriteria";

	static final String MATCHING_BOOKS_MODEL_NAME = "matchingBooks";
	static final String SEARCH_CRITERIA_VIEW_NAME = "searchCriteria";

	static final String BOOK_LIST_VIEW_NAME = "bookList";

	@Autowired
	private BookDao bookDao;

	@RequestMapping(value = "showSearchCriteria", method = RequestMethod.GET)
	public String showSearchCriteria(Model model) {
		if (!model.containsAttribute(SEARCH_CRITERIA_MODEL_NAME))
			model.addAttribute(SEARCH_CRITERIA_VIEW_NAME, new SearchCriteria());
		return SEARCH_CRITERIA_VIEW_NAME;
	}
	
	@RequestMapping(value = "showMatchedBooks", method = RequestMethod.GET)
	public ModelAndView showMatchedBooks(@ModelAttribute(SEARCH_CRITERIA_MODEL_NAME) SearchCriteria criteria) {
		List<Book> matchingBooks = bookDao.searchForBooks(criteria);
		return new ModelAndView(BOOK_LIST_VIEW_NAME, MATCHING_BOOKS_MODEL_NAME,
				matchingBooks);
	}

	@RequestMapping(value = "searchForBooks", method = RequestMethod.POST)
	public String searchForBooks(@ModelAttribute(SEARCH_CRITERIA_MODEL_NAME) SearchCriteria criteria) {
		return "redirect:showMatchedBooks";
	}

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}
}
