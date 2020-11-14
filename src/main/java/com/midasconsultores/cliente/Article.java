package com.midasconsultores.cliente;

public class Article {

	String _id;
	Provider provider;
	String category;
	String title;
	String sourceUrl;
	String publishedAt;
	String imageUrl;

	public Article() {

	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public String getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(String publishedAt) {
		this.publishedAt = publishedAt;
	}
	
	
	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String toString() {
		return "Article [_id=" + _id + ", provider=" + provider + ", category=" + category + ", title=" + title
				+ ", sourceUrl=" + sourceUrl + ", publishedAt=" + publishedAt + ", imageUrl=" + imageUrl + "]";
	}



}
