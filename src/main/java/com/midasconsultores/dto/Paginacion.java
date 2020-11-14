package com.midasconsultores.dto;

import java.util.List;

public class Paginacion<T> {

	int page;
	int pages;
	long total;

	List<T> contenido;

	public Paginacion(int page, int pages, long total2, List<T> contenido, int tamanioPagina) {
		super();
		this.page = page;
		this.pages = pages;
		this.total = total2;
		this.contenido = contenido;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getContenido() {
		return contenido;
	}

	public void setContenido(List<T> contenido) {
		this.contenido = contenido;
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
		return "Paginacion [page=" + page + ", pages=" + pages + ", total=" + total + ", contenido=" + contenido + "]";
	}

}
