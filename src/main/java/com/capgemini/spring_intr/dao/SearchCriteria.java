package com.capgemini.spring_intr.dao;

import org.springframework.util.StringUtils;

public class SearchCriteria {
	private String authors;

	private String title;

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public boolean hasAuthors() {
		return StringUtils.hasText(authors);
	}

	public String getAuthors() {
		return authors;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean hasTitle() {
		return StringUtils.hasText(title);
	}

	public String getTitle() {
		return title;
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
		SearchCriteria other = (SearchCriteria) obj;
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
