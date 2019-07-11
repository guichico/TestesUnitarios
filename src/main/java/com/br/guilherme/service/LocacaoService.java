package com.br.guilherme.service;

import java.util.Date;
import java.util.List;
import com.br.guilherme.entities.Filme;
import com.br.guilherme.entities.Locacao;
import com.br.guilherme.entities.Usuario;
import com.br.guilherme.exceptions.FilmeSemEstoqueException;
import com.br.guilherme.exceptions.LocadoraException;
import com.br.guilherme.utils.DataUtils;

public class LocacaoService {

	public Locacao alugarFilme(Usuario usuario, List<Filme> filmes) 
			throws FilmeSemEstoqueException, LocadoraException {
		if(usuario == null || usuario.getNome().isEmpty())
			throw new LocadoraException();

		if(filmes == null || filmes.isEmpty())
			throw new LocadoraException();

		double precoLocacao = 0;
		for (Filme f : filmes) {
			if(f.getEstoque() == 0) {
				throw new FilmeSemEstoqueException();
			}
			
			precoLocacao += f.getPrecoLocacao();
		}

		Locacao locacao = new Locacao();
		locacao.setFilmes(filmes);
		locacao.setUsuario(usuario);
		locacao.setDataLocacao(new Date());
		locacao.setValor(precoLocacao);

		//Entrega no dia seguinte
		Date dataEntrega = new Date();
		dataEntrega = DataUtils.adicionarDias(dataEntrega, 1);
		locacao.setDataRetorno(dataEntrega);

		return locacao;
	}
}