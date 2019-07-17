package com.br.guilherme.service;

import static builders.FilmeBuilder.umFilme;
import static builders.LocacaoBuilder.umLocacao;
import static builders.UsuarioBuilder.umUsuario;
import static com.br.guilherme.utils.DataUtils.verificarDiaSemana;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.any;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.br.guilherme.daos.LocacaoDAO;
import com.br.guilherme.entities.Filme;
import com.br.guilherme.entities.Locacao;
import com.br.guilherme.entities.Usuario;
import com.br.guilherme.exceptions.FilmeSemEstoqueException;
import com.br.guilherme.exceptions.LocadoraException;

import matchers.Matchers;

@RunWith(Parameterized.class)
public class LocacaoServiceTest {

	private ErrorCollector error = new ErrorCollector(); 

	@InjectMocks
	private LocacaoService locacaoService;

	@Mock
	private LocacaoDAO locacaoDAO;
	@Mock
	private SPCService spcService;
	@Mock
	private EmailService emailService;

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

	@Before
	public void init() {
		initMocks(this);
	}

	@Test
	public void alugarFilmeTest() throws FilmeSemEstoqueException, LocadoraException {
		Locacao locacao = locacaoService.alugarFilme(umUsuario().agora(), filmes);

		error.checkThat(locacao.getDataLocacao(), Matchers.isToday());
		error.checkThat(locacao.getDataRetorno(), Matchers.isTomorrow());
	}

	@Test
	public void calcularLocacaoConsiderandoDesconto() throws FilmeSemEstoqueException, LocadoraException {
		Locacao locacao = locacaoService.alugarFilme(umUsuario().agora(), filmes);

		assertEquals(valorLocacao, locacao.getValor(), 0.01);
	}

	@Test(expected = FilmeSemEstoqueException.class)
	public void testLocacaoSemEstoque() throws FilmeSemEstoqueException, LocadoraException {
		List<Filme> filmes = Arrays.asList(umFilme().semEstoque().agora());

		locacaoService.alugarFilme(umUsuario().agora(), filmes);
	}

	@Test
	public void naoDeveDevolverFilmeDomingo() throws FilmeSemEstoqueException, LocadoraException {
		assumeTrue(verificarDiaSemana(new Date(), Calendar.SATURDAY));

		List<Filme> filmes = Arrays.asList(umFilme().agora());

		Locacao locacao = locacaoService.alugarFilme(umUsuario().agora(), filmes);

		boolean isMonday = verificarDiaSemana(locacao.getDataRetorno(), Calendar.MONDAY);

		assertTrue(isMonday);
	}

	@Test(expected = LocadoraException.class)
	public void naoDeveAlugarParaUsuarioNegativado() throws Exception {
		List<Filme> filmes = Arrays.asList(umFilme().agora());

		Usuario usuario = umUsuario().agora();

		when(spcService.possuiNegativacao(usuario)).thenReturn(true);

		locacaoService.alugarFilme(usuario, filmes);

		verify(spcService).possuiNegativacao(usuario);
	}

	@Test(expected = LocadoraException.class)
	public void deveTratarErroServicoSPC() throws Exception {
		List<Filme> filmes = Arrays.asList(umFilme().agora());

		Usuario usuario = umUsuario().agora();
		
		when(spcService.possuiNegativacao(usuario)).thenThrow(new Exception("Serviço de consulta ao SPC indisponível"));
		
		locacaoService.alugarFilme(usuario, filmes);
	}

	@Test
	public void deveNotificarLocacoesAtrasadas() {
		Usuario usuario = umUsuario().agora();
		Usuario usuario2 = umUsuario().comNome("Usuário em dia").agora();
		Usuario usuario3 = umUsuario().comNome("Outro atrasado").agora();

		List<Locacao> locacoes = Arrays.asList(
				umLocacao().atrasada().comUsuario(usuario).agora(),
				umLocacao().comUsuario(usuario2).agora(),
				umLocacao().atrasada().comUsuario(usuario3).agora(),
				umLocacao().atrasada().comUsuario(usuario3).agora());

		when(locacaoDAO.obterLocacoesComAtraso()).thenReturn(locacoes);

		locacaoService.notificarAtrasos();

		verify(emailService, times(3)).notificarUsuarioComAtraso(any(Usuario.class));
		verify(emailService).notificarUsuarioComAtraso(usuario);
		verify(emailService, never()).notificarUsuarioComAtraso(usuario2);
		verify(emailService, times(2)).notificarUsuarioComAtraso(usuario3);
		verifyNoMoreInteractions(emailService);
	}
}