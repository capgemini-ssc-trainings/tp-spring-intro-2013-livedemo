package com.capgemini.spring_intr.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.capgemini.spring_intr.dao.BookDao;
import com.capgemini.spring_intr.model.Book;

@Controller
@SessionAttributes(BookController.BOOK_MODEL_NAME)
public class BookController {

	public static final String BOOK_MODEL_NAME = "book";

	static final String BOOK_EDIT_VIEW_NAME = "bookEdit";

	@Autowired
	private BookDao bookDao;

	public void setBookDao(BookDao bookDao) {
		this.bookDao = bookDao;
	}

	@RequestMapping(value = "showBookForEdit", method = RequestMethod.GET)
	public String showBookForEdit(@RequestParam("bookId") long bookId,
			Model model) {
		Book book = bookDao.getBook(bookId);
		model.addAttribute(BOOK_MODEL_NAME, book);
		return BOOK_EDIT_VIEW_NAME;
	}

	@RequestMapping(value = "addNewBook", method = RequestMethod.GET)
	public String addNewBook(Model model) {
		model.addAttribute(BOOK_MODEL_NAME, new Book());
		return BOOK_EDIT_VIEW_NAME;
	}

	@RequestMapping(value = "saveBook", method = RequestMethod.POST)
	public String saveBook(@ModelAttribute(BOOK_MODEL_NAME) @Valid Book book,
			BindingResult bindingResult, SessionStatus status) {
		if (bindingResult.hasErrors())
			return BOOK_EDIT_VIEW_NAME;
		else {
			bookDao.saveBook(book);
			status.setComplete();
			return "redirect:showBookForEdit?bookId=" + book.getId();
		}
	}
}
