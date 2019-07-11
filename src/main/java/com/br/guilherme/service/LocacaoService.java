package com.br.guilherme.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.br.guilherme.daos.LocacaoDAO;
import com.br.guilherme.entities.Filme;
import com.br.guilherme.entities.Locacao;
import com.br.guilherme.entities.Usuario;
import com.br.guilherme.exceptions.FilmeSemEstoqueException;
import com.br.guilherme.exceptions.LocadoraException;
import com.br.guilherme.utils.DataUtils;

public class LocacaoService {

	private LocacaoDAO locacaoDAO;
	private SPCService spcService;
	private EmailService emailService;

	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) 
			throws FilmeSemEstoqueException, LocadoraException {
		if(usuario == null || usuario.getNome().isEmpty())
			throw new LocadoraException();

		if(spcService.consultaSPC(usuario))
			throw new LocadoraException();

		if(filmes == null || filmes.isEmpty())
			throw new LocadoraException();

		int i = 0; 
		double precoLocacao = 0;
		for (Filme f : filmes) {
			if(f.getEstoque() == 0) {
				throw new FilmeSemEstoqueException();
			}

			double valorFilme = 0;
			switch (i) {
			case 2: valorFilme = f.getPrecoLocacao() * 0.75; break;
			case 3:	valorFilme = f.getPrecoLocacao() * 0.50; break;
			default: valorFilme = f.getPrecoLocacao(); break;
			}

			precoLocacao += valorFilme;

			i++;
		}

		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(precoLocacao);

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = DataUtils.adicionarDias(dataEntrega, 1);

		if(DataUtils.verificarDiaSemana(dataEntrega, Calendar.SUNDAY))
			dataEntrega = DataUtils.adicionarDias(dataEntrega, 1);

		locacao.setDataRetorno(dataEntrega);

		locacaoDAO.salvar(locacao);

		return locacao;
	}

	public void notificarAtrasos() {
		List<Locacao> locacoes = locacaoDAO.obterLocacoesComAtraso();
		for (Locacao locacao : locacoes) {
			emailService.notificarUsuarioComAtraso(locacao.getUsuario());
		}
	}

	public void setLocacaoDAO(LocacaoDAO locacaoDAO) {
		this.locacaoDAO = locacaoDAO;
	}

	public void setSpcService(SPCService spcService) {
		this.spcService = spcService;
	}

	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}
}