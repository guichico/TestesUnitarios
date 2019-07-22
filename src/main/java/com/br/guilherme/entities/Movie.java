package com.br.guilherme.entities;

public class Movie {
	private String title;
	private Integer stock;
	private Double price;
	private String coverImg;

	public String getTitle() {
		return title;
	}

	public Integer getStock() {
		return stock;
	}

	public Double getPrice() {
		return price;
	}

	public String getCoverImg() {
		return coverImg;
	}

	private Movie(Builder builder) {
		this.title = builder.title;
		this.stock = builder.stock;
		this.price = builder.price;
		this.coverImg = builder.coverImg;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private String title;
		private Integer stock;
		private Double price;
		private String coverImg;

		private Builder() {
		}

		public Builder withTitle(String title) {
			this.title = title;
			return this;
		}

		public Builder withStock(Integer stock) {
			this.stock = stock;
			return this;
		}

		public Builder withPrice(Double price) {
			this.price = price;
			return this;
		}

		public Builder withCoverImg(String coverImg) {
			this.coverImg = coverImg;
			return this;
		}

		public Movie build() {
			return new Movie(this);
		}
	}
}