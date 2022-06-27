package br.com.softblue.loucademia.application.service;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import br.com.softblue.loucademia.application.uitl.StringUtils;
import br.com.softblue.loucademia.application.uitl.Validation;
import br.com.softblue.loucademia.application.uitl.ValidationException;
import br.com.softblue.loucademia.domain.aluno.Aluno;
import br.com.softblue.loucademia.domain.aluno.AlunoRepository;

@Stateless
public class AlunoService {

	@EJB
	private AlunoRepository alunoRepository;
	
	public void createOrUpdate(Aluno aluno) {
		if(StringUtils.isEmpty(aluno.getMatricula())) {
			create(aluno);
		}else {
			update(aluno);
		}
	}
	
	private void create(Aluno aluno) {
		Validation.assertNotEmpty(aluno);
		
		String maxMatricula = alunoRepository.getMaxMatriculaAno();
		aluno.gerarMatricula(maxMatricula);
		alunoRepository.store(aluno);
	}
	
	public void delete(String matricula) {
		alunoRepository.delete(matricula);
	}
	
	private void update(Aluno aluno) {
		Validation.assertNotEmpty(aluno);
		Validation.assertNotEmpty(aluno.getMatricula());		
		alunoRepository.update(aluno);		
	}
	
	public Aluno findByMatricula(String matricula) {
		return alunoRepository.findByMatricula(matricula);
	}
	
	public List<Aluno> listAlunos(String matricula, String nome, Integer rg, Integer telefone){
		if(StringUtils.isEmpty(matricula) && StringUtils.isEmpty(nome) && rg == null & telefone == null) {
			throw new ValidationException("Pelo menos um critério de pesquisa deve ser preenchido!");
		}
		
		return alunoRepository.listAlunos(matricula, nome, rg, telefone);
	}
}
