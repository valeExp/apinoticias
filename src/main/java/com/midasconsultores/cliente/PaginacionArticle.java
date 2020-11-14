package com.midasconsultores.cliente;

import java.util.List;

public class PaginacionArticle {
	
	List<Article> articles;
	long total;
	int page; 
	int pages;
		
	public PaginacionArticle() {
	
	}
	
	public List<Article> getArticles() {
		return articles;
	}
	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}

	@Override
	public String toString() {
		return "WraperArticle [articles=" + articles + ", total=" + total + ", page=" + page + ", pages=" + pages + "]";
	}
	
	
	
}
