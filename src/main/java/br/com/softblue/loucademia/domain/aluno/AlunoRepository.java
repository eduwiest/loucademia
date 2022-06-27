package br.com.softblue.loucademia.domain.aluno;

import java.time.Year;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.softblue.loucademia.application.uitl.StringUtils;

@Stateless
public class AlunoRepository {

	@PersistenceContext
	private EntityManager em;
	
	public void store(Aluno aluno) {
		em.persist(aluno);		
	}
	
	public void update(Aluno aluno) {
		em.merge(aluno);
	}
	
	public Aluno findByMatricula(String matricula) {
		return em.find(Aluno.class, matricula);
	}
	
	public Aluno findByRG(Integer rg) {
		try {
			return em.createQuery("SELECT a FROM Aluno a WHERE a.rg = :rg", Aluno.class)
					.setParameter("rg", rg)
					.getSingleResult();		
		}catch (NoResultException e) {
			return null;
		}
	}
	
	public void delete(String matricula) {
		Aluno aluno = findByMatricula(matricula);
		
		if(aluno != null) {
			em.remove(aluno);
		}
	}
	
	public String getMaxMatriculaAno() {
		return em.createQuery("SELECT MAX(a.matricula) FROM Aluno a WHERE a.matricula LIKE :ano", String.class)
				.setParameter("ano", Year.now() + "%")
				.getSingleResult();
	}
	
	public List<Aluno> listAlunos(String matricula, String nome, Integer rg, Integer telefone){
		StringBuilder jpql = new StringBuilder("SELECT a FROM Aluno a WHERE ");
		
		if(!StringUtils.isEmpty(matricula)){
			jpql.append("a.matricula = :matricula AND ");
		}
		if(!StringUtils.isEmpty(nome)){
			jpql.append("a.nome LIKE :nome AND ");
		}
		if(rg != null){
			jpql.append("a.rg = :rg AND ");
		}
		if(telefone != null){
			jpql.append("a.telefone.numeroCelular LIKE :celular OR a.telefone.numeroFixo LIKE :fixo) AND ");
		}
		
		jpql.append("1 = 1");
		
		TypedQuery<Aluno> q = em.createQuery(jpql.toString(), Aluno.class);
		
		if(!StringUtils.isEmpty(matricula)){
			q.setParameter("matricula", matricula);
		}
		if(!StringUtils.isEmpty(nome)){
			q.setParameter("nome", "%" + nome + "%");
		}
		if(rg != null){
			q.setParameter("rg", rg);
		}
		if(telefone != null){
			q.setParameter("celular", telefone);
			q.setParameter("fixo", telefone);
		}
		
		return q.getResultList();
		
	}
	
}
