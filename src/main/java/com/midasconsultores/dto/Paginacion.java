package com.midasconsultores.dto;

import java.util.List;

public class Paginacion<T> {
	
	int pagina;
	long total;	
	int tamanioPagina;
	List<T> listado;
			
	public Paginacion(int pagina, long total2, List<T> listado, int tamanioPagina) {
		super();
		this.pagina = pagina;
		this.total = total2;
		this.listado = listado;
		this.tamanioPagina = tamanioPagina;
	}
	
	public int getPagina() {
		return pagina;
	}
	public void setPagina(int pagina) {
		this.pagina = pagina;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<T> getListado() {
		return listado;
	}
	public void setListado(List<T> listado) {
		this.listado = listado;
	}

	public int getTamanioPagina() {
		return tamanioPagina;
	}

	public void setTamanioPagina(int tamanioPagina) {
		this.tamanioPagina = tamanioPagina;
	}

	@Override
	public String toString() {
		return "Paginacion [pagina=" + pagina + ", total=" + total + ", tamanioPagina=" + tamanioPagina + ", listado="
				+ listado + "]";
	}

}
