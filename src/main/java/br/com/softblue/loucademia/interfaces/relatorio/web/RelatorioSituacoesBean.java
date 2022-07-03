package br.com.softblue.loucademia.interfaces.relatorio.web;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import br.com.softblue.loucademia.application.service.AlunoService;
import br.com.softblue.loucademia.domain.aluno.Aluno;
import br.com.softblue.loucademia.domain.aluno.Aluno.Situacao;

@Named
@SessionScoped
public class RelatorioSituacoesBean implements Serializable {
	
	@EJB
	private AlunoService alunoService;
	
	private Situacao situacao;
	
	private List<Aluno> alunos;
	
	public String gerarRelatorio() {
		alunos = alunoService.listSituacoesAlunos(situacao);
		return null;
	}

	public Situacao getSituacao() {
		return situacao;
	}

	public void setSituacao(Situacao situacao) {
		this.situacao = situacao;
	}

	public List<Aluno> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
	}
	
	
}
