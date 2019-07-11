package com.br.guilherme.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.br.guilherme.entities.Filme;
import com.br.guilherme.entities.Locacao;
import com.br.guilherme.entities.Usuario;
import com.br.guilherme.exceptions.FilmeSemEstoqueException;
import com.br.guilherme.exceptions.LocadoraException;

public class LocacaoServiceTest {

	private static LocacaoService locacaoService;
	
	private static Usuario usuario;
	private static List<Filme> filmes;

	@BeforeClass
	public static void init() {
		locacaoService = new LocacaoService();
		
		usuario  = new Usuario();
		usuario.setNome("Guilherme");

		filmes = new ArrayList<Filme>();
		
		Filme filme = new Filme();
		filme.setNome("Senhor dos Anéis");
		filme.setEstoque(2);
		filme.setPrecoLocacao(5.0);
		
		filmes.add(filme);
		
		filme = new Filme();
		filme.setNome("Poderoso Chefão");
		filme.setEstoque(2);
		filme.setPrecoLocacao(4.5);
		
		filmes.add(filme);
	}

	@Test
	public void testLocacaoOK() throws Exception {
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
		assertEquals(9.5, locacao.getValor(), 0.01);
	}

	@Test
	public void testLocacaoNotOK() throws Exception {
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);
		assertNotEquals(4.0, locacao.getValor(), 0.01);
	}

	@Test(expected = FilmeSemEstoqueException.class)
	public void testLocacaoSemEstoque() throws FilmeSemEstoqueException, LocadoraException {
		filmes = new ArrayList<Filme>();

		Filme filme = new Filme();
		filme.setNome("Senhor dos Anéis");
		filme.setEstoque(1);
		filme.setPrecoLocacao(5.0);
		filmes.add(filme);
		
		filme = new Filme();
		filme.setNome("Poderoso Chefão");
		filme.setEstoque(0);
		filme.setPrecoLocacao(4.5);
		
		filmes.add(filme);

		locacaoService.alugarFilme(usuario, filmes);
	}
}