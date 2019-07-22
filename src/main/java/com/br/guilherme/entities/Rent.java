package com.br.guilherme.entities;

import java.util.Date;
import java.util.List;
import static com.br.guilherme.utils.DataUtils.addDays;
import java.util.Collections;

public class Rent {
	private User user;
	private List<Movie> movies;
	private Date rentDate;
	private Date returnDate;	
	private Double value;

	public User getUser() {
		return user;
	}

	public List<Movie> getMovies() {
		return movies;
	}

	public Date getRentDate() {
		return rentDate;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public Double getValue() {
		return value;
	}

	private Rent(Builder builder) {
		this.user = builder.user;
		this.movies = builder.movies;
		this.rentDate = builder.rentDate;
		this.returnDate = builder.returnDate;
		this.value = builder.value;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private User user;
		private List<Movie> movies = Collections.emptyList();
		private Date rentDate;
		private Date returnDate;
		private Double value;

		private Builder() {
		}

		public Builder withUser(User user) {
			this.user = user;
			return this;
		}

		public Builder withMovies(List<Movie> movies) {
			this.movies = movies;
			return this;
		}

		public Builder withRentDate(Date rentDate) {
			this.rentDate = rentDate;
			return this;
		}

		public Builder withReturnDate(Date returnDate) {
			this.returnDate = returnDate;
			return this;
		}

		public Builder withValue(Double value) {
			this.value = value;
			return this;
		}

		public Builder delayed(Date date) {
			this.rentDate = addDays(date, -4);
			this.returnDate = addDays(date, -2);
			return this;
		}

		public Rent build() {
			return new Rent(this);
		}
	}
}