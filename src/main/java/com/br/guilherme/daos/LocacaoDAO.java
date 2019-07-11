package com.br.guilherme.daos;

import java.util.List;

import com.br.guilherme.entities.Locacao;

public interface LocacaoDAO {

	public void salvar(Locacao locacao);
	
	public List<Locacao> obterLocacoesComAtraso();
}
