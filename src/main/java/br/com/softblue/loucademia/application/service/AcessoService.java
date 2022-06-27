package br.com.softblue.loucademia.application.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.swing.JOptionPane;

import br.com.softblue.loucademia.application.uitl.StringUtils;
import br.com.softblue.loucademia.application.uitl.ValidationException;
import br.com.softblue.loucademia.domain.acesso.Acesso;
import br.com.softblue.loucademia.domain.acesso.AcessoRepository;
import br.com.softblue.loucademia.domain.acesso.TipoAcesso;
import br.com.softblue.loucademia.domain.aluno.Aluno;
import br.com.softblue.loucademia.domain.aluno.AlunoRepository;

@Stateless
public class AcessoService {

	@EJB
	private AcessoRepository acessoRepository;
	
	@EJB
	private AlunoRepository alunoRepository;
	
	public TipoAcesso registrarAcesso(String matricula, Integer rg) {
		if(StringUtils.isEmpty(matricula) && rg == null) {
			throw new ValidationException("É preciso fornecer a matricula ou o RG do Aluno!");
		}
		
		Aluno aluno;
		if (StringUtils.isEmpty(matricula)) {
			aluno = alunoRepository.findByRG(rg);
		} else {
			aluno = alunoRepository.findByMatricula(matricula);
		}
		
		if (aluno == null) {
			throw new ValidationException("O aluno não foi encontrado");
		}
		
		Acesso ultimoAcesso = acessoRepository.findUltimoAcesso(aluno);
		TipoAcesso tipoAcesso;
		
		if (ultimoAcesso == null || ultimoAcesso.isEntradaSaidaPreenchidas()) {
			ultimoAcesso = new Acesso();
			ultimoAcesso.setAluno(aluno);
			tipoAcesso = ultimoAcesso.registrarAcesso();
			acessoRepository.store(ultimoAcesso);
		
		} else {
			tipoAcesso = ultimoAcesso.registrarAcesso();
		}
		
		return tipoAcesso;
	}
	

}
