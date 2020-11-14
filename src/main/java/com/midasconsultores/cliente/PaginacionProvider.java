package com.midasconsultores.cliente;

import java.util.List;

public class PaginacionProvider {
	
	List<Provider> providers;
	int total;
	int page;
	int pages;	
	
	public PaginacionProvider() {	
	}
	
	public List<Provider> getProviders() {
		return providers;
	}
	public void setProviders(List<Provider> providers) {
		this.providers = providers;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
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
		return "WraperProvider [providers=" + providers + ", total=" + total + ", page=" + page + ", pages=" + pages
				+ "]";
	}

	
	
}
