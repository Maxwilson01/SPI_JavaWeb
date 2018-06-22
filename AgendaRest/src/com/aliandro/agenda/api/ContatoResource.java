package com.aliandro.agenda.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.aliandro.agenda.model.Agenda;
import com.aliandro.agenda.model.Contato;


/**
 * Esta classe está anotada com @Path para definir um recurso, associado ao nosso contato.
 * Sendo assim, os métodos desta classe serão chamados a partir do caminho-base "contatos".
 * 
 * Por exemplo:
 * 
 * http://localhost:8080/AgendaRest/apis/contatos/
 *
 * A anotação @Singleton é apenas uma forma de configurar uma única instância desta classe
 * que será criada pelo container;
 * 
 * A anotação @Startup indica que está classe será iniciada assim que a aplicação for 
 * implantada
 * 
 */
@Path("contatos")
@Singleton
@Startup
public class ContatoResource {

	@EJB
	private Agenda agenda; //injetando a lógica de negócio
	
	/**
	 * Este método será chamado quando o usuário fizer um get para a URI
	 * http://localhost:8080/AgendaRest/apis/contatos/
	 * 
	 * Preferencialmente, irá retornar um JSON. Se o client não aceitar JSON,
	 * deve retornar um XML.
	 * 
	 * A conversão dos contatos em JSON acontece automaticamente, pois a classe
	 * contato está usando o JAXB para fazer esta conversão. O uso do JAXB se dá
	 * pela anotação @XMLRootElement, usada na classe Contato.
	 * 
	 * @return todos os contatos a partir da lógica de negócio
	 */
	@GET
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response obterContatos(){
		List<Contato> contatos = agenda.obterContatos();
		return Response.ok().entity(contatos).build();
	}
	
	/**
	 * Este método será chamado quando o usuário fizer um get para a URI
	 * http://localhost:8080/AgendaRest/apis/contatos/{id}, onde {id}
	 * é um inteiro que representa o ID do contato.
	 * 
	 * Foi criado para retornar um contato específico, a partir do seu ID.
	 * 
	 * A anotação @Path indica o complemento da URI a partir da URI base, ou seja,
	 * além do caminho todo definido para a API, também deverá ser usado o ID
	 * para identificar o contato que se quer retornar.
	 * 
	 * A anotação @PathParam faz com que o {id} passado na URI seja associado 
	 * ao parâmetro do método, automaticamente, sem nenhuma intervenção do programador.
	 * 
	 * @param id o ID do contato que será retornado
	 * @return o contato identificado pelo ID passado como parâmetro.
	 */
	@GET
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response obterContato( @PathParam("id") Long id ){
		Contato contato = agenda.obterContato(id);
		return Response.ok().entity(contato).build();
	}
	
	
	/**
	 * Este método será chamado quando o usuário fizer um POST para a URI
	 * http://localhost:8080/AgendaRest/apis/contatos/
	 * 
	 * Os dados usados para cadastrar o contato devem ser extraídos de um formulário,
	 * ou seja, a partir dos parâmetros de um form.
	 * 
	 * Para isso, este POST deve usar o Content-Type: application/x-www-form-urlencoded
	 * 
	 * Cada parâmetro do POST, que seguirá no formulário, será associado a um parâmetro
	 * no método a partir da anotação @FormParam.
	 * 
	 * Estamos usando uma injeção de dependências no método para receber uma UriInfo, 
	 * que contém os detalhes da URI que está sendo chamada. Através destes detalhes,
	 * seremos capazes de montar a URI para fazer o redirecionamento.
	 * 
	 * Uma vez cadastrado um contato, será gerado um ID para ele. Com base neste ID,
	 * este método faz um REDIRECT para a URI do contato que acabou de ser criado, 
	 * para mostrá-lo.
	 * 
	 * A URI será http://localhost:8080/AgendaRest/apis/contatos/{id_do_contato_criado}
	 * 
	 */
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response cadastrarContato( @FormParam("nome") String nome,
										@FormParam("telefone") String telefone,
										@Context UriInfo uriInfo) throws URISyntaxException{
		
		Contato c = new Contato();
		c.setNome(nome);
		c.setTelefone(telefone);
		
		Contato contato = agenda.adicionarContato(c);
		
		/* seeOther representa o redirect, ou seja, estamos instruindo o browser a
		 * fazer um novo request GET para a URI que montamos. 
		 */
		return Response.seeOther( new URI( uriInfo.getAbsolutePath().toString() + contato.getId() )).build();
	}
	
	
	/**
	 * Remove um contato com base no seu ID. A representação do contato é a mesma
	 * usada no método que o retorna: http://localhost:8080/AgendaRest/apis/contatos/{id} 
	 * 
	 */
	@DELETE
	@Path("/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response removerContato( @PathParam("id") Long id ){
		
		Contato contato = new Contato();
		contato.setId(id);
		
		agenda.removerContato(contato);;
		return Response.noContent().build();
	}
	
	
}
