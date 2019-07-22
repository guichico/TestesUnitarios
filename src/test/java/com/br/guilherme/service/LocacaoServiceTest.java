package com.br.guilherme.service;

import static com.br.guilherme.utils.DataUtils.addDays;
import static com.br.guilherme.utils.DataUtils.getDate;
import static matchers.Matchers.isMonday;
import static matchers.Matchers.isToday;
import static matchers.Matchers.isTomorrow;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;

import java.util.Arrays;
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

import com.br.guilherme.daos.RentDAO;
import com.br.guilherme.entities.Movie;
import com.br.guilherme.entities.Rent;
import com.br.guilherme.entities.User;
import com.br.guilherme.exceptions.MovieOutOfStockException;
import com.br.guilherme.exceptions.RentException;
import com.br.guilherme.utils.DateService;

@RunWith(Parameterized.class)
public class LocacaoServiceTest {

	private ErrorCollector error = new ErrorCollector(); 

	@InjectMocks
	private RentService rentService;

	@Mock
	private DateService dateService;
	@Mock
	private RentDAO rentDAO;
	@Mock
	private SPCService spcService;
	@Mock
	private EmailService emailService;

	@Parameter(value = 0)
	public List<Movie> movies;
	@Parameter(value = 1)
	public Double rentValue;
	@Parameter(value = 2)
	public String name;
	
	private User user;

	private static Movie movie1 = Movie.builder().withTitle("Senhor dos Anéis").withStock(2).withPrice(5.0).build(), 
			movie2 =  Movie.builder().withTitle("Poderoso Chefão").withStock(3).withPrice(4.5).build(),
			movie3 =  Movie.builder().withTitle("Vingadores").withStock(10).withPrice(9.0).build(),
			movie4 =  Movie.builder().withTitle("Pocahontas").withStock(1).withPrice(3.0).build();

	@Parameters(name="{2}")
	public static Collection<Object[]> getParameters() {
		return Arrays.asList(new Object[][] {
			{Arrays.asList(movie1), 5.0, "Alugando 1 filme"},
			{Arrays.asList(movie1, movie2), 9.5, "Alugando filme sem estoque"},
			{Arrays.asList(movie1, movie2, movie3), 16.25, "Alugando 3 filme com desconto"},
			{Arrays.asList(movie1, movie2, movie3, movie4), 17.75, "Alugando 4 filme com desconto"}
		});
	}

	@Before
	public void init() {
		initMocks(this);

		doReturn(new Date()).when(dateService).getDate();
		
		user = User.builder().withName("Guilherme").build();
	}

	@Test
	public void rentMovieTest() throws MovieOutOfStockException, RentException {
		Rent rent = rentService.rentMovie(user, movies);

		error.checkThat(rent.getRentDate(), isToday());
		error.checkThat(rent.getReturnDate(), isTomorrow());
	}

	@Test
	public void calculateRentValueWithDiscount() throws MovieOutOfStockException, RentException {
		Rent rent = rentService.rentMovie(user, movies);

		assertEquals(rentValue, rent.getValue(), 0.01);
	}

	@Test(expected = MovieOutOfStockException.class)
	public void shouldntRentMovieOutOfStock() throws MovieOutOfStockException, RentException {
		List<Movie> movies = Arrays.asList(Movie.builder().withStock(0).build());

		rentService.rentMovie(user, movies);
	}

	@Test
	public void shouldntReturnRentOnSunday() throws Exception {
		List<Movie> movies = Arrays.asList(movie1);

		doReturn(getDate(13, 07, 2019)).when(dateService).getDate();

		Rent rent = rentService.rentMovie(user, movies);

		assertThat(rent.getReturnDate(), isMonday());
	}

	@Test(expected = RentException.class)
	public void shouldntRentToNegativatedUser() throws Exception {
		List<Movie> movies = Arrays.asList(movie1);

		when(spcService.isNegativated(user)).thenReturn(true);

		rentService.rentMovie(user, movies);

		verify(spcService).isNegativated(user);
	}

	@Test(expected = RentException.class)
	public void shouldHandleErroOnSPCService() throws Exception {
		List<Movie> movies = Arrays.asList(movie1);

		when(spcService.isNegativated(user)).thenThrow(new Exception("Serviço de consulta ao SPC indisponível"));

		rentService.rentMovie(user, movies);
	}

	@Test
	public void shouldNotifyDelayedRentals() {
		User user2 = User.builder().withName("Usuário em dia").build();
		User user3 = User.builder().withName("Outro atrasado").build();

		doReturn(addDays(new Date(), 1)).when(dateService).getDate(1);
		
		List<Rent> rentals = Arrays.asList(
				Rent.builder().withUser(user).withMovies(Arrays.asList(movie1)).delayed(dateService.getDate()).withValue(4.0).build(),
				Rent.builder().withUser(user2).withMovies(Arrays.asList(movie1)).withRentDate(dateService.getDate()).withReturnDate(dateService.getDate(1)).withValue(4.0).build(),
				Rent.builder().withUser(user3).withMovies(Arrays.asList(movie1)).delayed(dateService.getDate()).withValue(4.0).build(),
				Rent.builder().withUser(user3).withMovies(Arrays.asList(movie1)).delayed(dateService.getDate()).withValue(4.0).build());

		when(rentDAO.getDelayedRentals()).thenReturn(rentals);

		rentService.notifyDelayeds();

		/*verify(emailService).notifyDelayedUsers(user);
		verify(emailService, never()).notifyDelayedUsers(user2);
		verify(emailService, times(2)).notifyDelayedUsers(user3);*/
		verify(emailService, times(3)).notifyDelayedUsers(any(User.class));
		verifyNoMoreInteractions(emailService);
	}
}