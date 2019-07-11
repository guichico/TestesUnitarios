package com.br.guilherme.service;

import static com.br.guilherme.utils.DataUtils.verificarDiaSemana;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.br.guilherme.entities.Filme;
import com.br.guilherme.entities.Locacao;
import com.br.guilherme.entities.Usuario;
import com.br.guilherme.exceptions.FilmeSemEstoqueException;
import com.br.guilherme.exceptions.LocadoraException;
import com.br.guilherme.utils.DataUtils;

@RunWith(Parameterized.class)
public class LocacaoServiceTest {

	private static LocacaoService locacaoService;
	private static Usuario usuario;

	@Parameter(value = 0)
	public List<Filme> filmes;
	@Parameter(value = 1)
	public Double valorLocacao;
	@Parameter(value = 2)
	public String name;

	private static Filme filme1 = new Filme("Senhor dos Anéis", 2, 5.0), 
			filme2 = new Filme("Poderoso Chefão", 3, 4.5),
			filme3 = new Filme("Vingadores", 10, 9.0),
			filme4 = new Filme("Pocahontas", 1, 3.0);

	@Parameters(name="{2}")
	public static Collection<Object[]> getParameters() {
		return Arrays.asList(new Object[][] {
			{Arrays.asList(filme1), 5.0, "Alugando 1 filme"},
			{Arrays.asList(filme1, filme2), 9.5, "Alugando filme sem estoque"},
			{Arrays.asList(filme1, filme2, filme3), 16.25, "Alugando 3 filme com desconto"},
			{Arrays.asList(filme1, filme2, filme3, filme4), 17.75, "Alugando 4 filme com desconto"}
		});
	}

	@BeforeClass
	public static void init() {
		locacaoService = new LocacaoService();

		usuario  = new Usuario();
		usuario.setNome("Guilherme");
	}
	
	@Test
	public void calcularLocacaoConsiderandoDesconto() throws FilmeSemEstoqueException, LocadoraException {
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

		assertEquals(valorLocacao, locacao.getValor(), 0.01);
	}

	@Test(expected = FilmeSemEstoqueException.class)
	public void testLocacaoSemEstoque() throws FilmeSemEstoqueException, LocadoraException {
		List<Filme> filmes = Arrays.asList(new Filme("Senhor dos Anéis", 0, 5.0));
		
		locacaoService.alugarFilme(usuario, filmes);
	}

	@Test
	public void naoDeveDevolverFilmeDomingo() throws FilmeSemEstoqueException, LocadoraException {
		assumeTrue(verificarDiaSemana(new Date(), Calendar.SATURDAY));
		
		List<Filme> filmes = Arrays.asList(new Filme("Senhor dos Anéis", 2, 5.0));
		
		Locacao locacao = locacaoService.alugarFilme(usuario, filmes);

		boolean isMonday = verificarDiaSemana(locacao.getDataRetorno(), Calendar.MONDAY);

		assertTrue(isMonday);
	}
}