package com.midasconsultores.dto;

import java.util.List;

public class Paginacion<T> {

	int page;
	int pages;
	long total;
	String ordenarByFuente;

	List<T> contenido;

	

	public Paginacion(int page, int pages, long total, String ordenarByFuente, List<T> contenido) {
		super();
		this.page = page;
		this.pages = pages;
		this.total = total;
		this.ordenarByFuente = ordenarByFuente;
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

	public String getOrdenarByFuente() {
		return ordenarByFuente;
	}

	public void setOrdenarByFuente(String ordenarByFuente) {
		this.ordenarByFuente = ordenarByFuente;
	}

	@Override
	public String toString() {
		return "Paginacion [page=" + page + ", pages=" + pages + ", total=" + total + ", ordenarByFuente="
				+ ordenarByFuente + ", contenido=" + contenido + "]";
	}

	

}
