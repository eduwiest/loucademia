package br.com.softblue.loucademia.domain.acesso;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class AcessoRepository {

	@PersistenceContext
	private EntityManager em;
}
