package com.capgemini.spring_intr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Book {
	public static final String AUTHORS_PROP = "authors";

	public static final String TITLE_PROP = "title";

	@Id
	@GeneratedValue
	private long id;

	
	@Column(nullable = false)
	private String authors;
	
	
	@Column(nullable = false)
	private String title;

	public Book() {
	}

	public Book(String authors, String title) {
		this.authors = authors;
		this.title = title;
	}

	@NotEmpty
	public String getAuthors() {
		return authors;
	}

	@NotEmpty
	public String getTitle() {
		return title;
	}
	
	public long getId() {
		return id;
	}
	
	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((authors == null) ? 0 : authors.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		if (authors == null) {
			if (other.authors != null)
				return false;
		} else if (!authors.equals(other.authors))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	

}
