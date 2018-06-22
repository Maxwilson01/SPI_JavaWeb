package com.aliandro.agenda;

import java.util.List;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import com.aliandro.agenda.model.Agenda;
import com.aliandro.agenda.model.Contato;

@ManagedBean
@RequestScoped
public class AgendaBean {

	private Contato contato;
	
	@EJB
	private Agenda agenda;
	
	public AgendaBean() {
		this.contato = new Contato();
	}
	
	public Contato getContato() {
		return contato;
	}

	public String cadastrarContato(){
		agenda.adicionarContato(this.contato);
		return "listarContatos";
	}
	
	public List<Contato> getContatos(){
		return agenda.obterContatos();
	}
	
	public String excluirContato(Contato contato){
		agenda.removerContato(contato);
		return "qualquercoisapraficarnamesmapagina";
	}
}





