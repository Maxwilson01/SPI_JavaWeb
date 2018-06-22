package com.aliandro.agenda.model;

import java.util.List;


//Chama interface de AgendaEJB onde tem os metodos
public interface Agenda {

	public Contato adicionarContato( Contato contato );
	
	public void removerContato( Contato contato );
	
	public void atualizarContato( Contato contato );

	public List<Contato> obterContatos();
	
	public Contato obterContato( Long id );
}





