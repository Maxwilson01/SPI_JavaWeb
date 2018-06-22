package com.aliandro.agenda;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.aliandro.agenda.model.Cliente;
import com.aliandro.agenda.model.Pets;

//Classe de controller. Tem que ter dois Bean cliente e Pets

@ManagedBean
@RequestScoped
public class PetShopBean {
	

	private Pets pets;
	private Cliente cliente;
	
	public PetShopBean() {
		this.pets = new Pets();
		this.cliente = new Cliente();
	}
	
	
}
