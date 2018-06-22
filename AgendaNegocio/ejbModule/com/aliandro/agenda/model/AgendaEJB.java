package com.aliandro.agenda.model;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class AgendaEJB implements Agenda {

	@PersistenceContext
	private EntityManager em;
	
	@PostConstruct
	public void depoisDaCriacao(){
		System.out.println("[EJB] PostConstruct!");
	}
	
	@PreDestroy
	public void antesDaDestruicao(){
		System.out.println("[EJB] PreDestroy!");
	}
	
	public AgendaEJB() {
		System.out.println("[EJB] Fui criado!");
		
	}
	
	public Contato adicionarContato( Contato contato ){
		em.persist(contato);
		return contato;
	}
	
	public void removerContato( Contato contato ){
		Contato gerenciado = em.find(Contato.class, contato.getId());
		em.remove(gerenciado);
	}
	
	public void atualizarContato( Contato contato ){
		em.merge(contato);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Contato> obterContatos(){
		Query query = em.createQuery("Select c from Contato c");
		return query.getResultList();
	}

	@Override
	public Contato obterContato(Long id) {
		Contato contato = em.find(Contato.class, id);
		return contato;
	}
	
}
