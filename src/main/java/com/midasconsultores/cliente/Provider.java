package com.midasconsultores.cliente;

public class Provider {

	String name;
	String scope;

	public Provider() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	@Override
	public String toString() {
		return "Provider [name=" + name + ", scope=" + scope + "]";
	}
	
	

}
