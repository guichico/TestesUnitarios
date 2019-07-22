package com.br.guilherme.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.br.guilherme.daos.RentDAO;
import com.br.guilherme.entities.Movie;
import com.br.guilherme.entities.Rent;
import com.br.guilherme.entities.User;
import com.br.guilherme.exceptions.MovieOutOfStockException;
import com.br.guilherme.exceptions.RentException;
import com.br.guilherme.utils.DataUtils;
import com.br.guilherme.utils.DateService;

public class RentService {
	private RentDAO rentDao;
	private SPCService spcService;
	private EmailService emailService;
	private DateService dateService;

	public Rent rentMovie(User user, List<Movie> movies) 
			throws MovieOutOfStockException, RentException {

		if(user == null || user.getName().isEmpty())
			throw new RentException();

		if(movies == null || movies.isEmpty())
			throw new RentException();

		boolean negativated;
		try {
			negativated = spcService.isNegativated(user);
		} catch (Exception e) {
			throw new RentException();
		}

		if(negativated)
			throw new RentException();

		Rent rent = Rent.builder()
				.withMovies(movies)
				.withUser(user)
				.withRentDate(dateService.getDate())
				.withReturnDate(calculateReturnDate())
				.withValue(calculateRentValue(movies))
				.build();

		rentDao.save(rent);

		return rent;
	}

	private double calculateRentValue(List<Movie> movies) throws MovieOutOfStockException {
		double rentValue = 0;

		int i = 0;
		for (Movie m : movies) {
			if(m.getStock() == 0) {
				throw new MovieOutOfStockException();
			}

			double moviePrice = 0;
			switch (i) {
			case 2: moviePrice = m.getPrice() * 0.75; break;
			case 3:	moviePrice = m.getPrice() * 0.50; break;
			default: moviePrice = m.getPrice(); break;
			}

			rentValue += moviePrice;

			i++;
		}

		return rentValue;
	}

	private Date calculateReturnDate() {
		Date returnDate = dateService.getDate();
		returnDate = DataUtils.addDays(returnDate, 1);

		if(DataUtils.verifyDayOfWeek(returnDate, Calendar.SUNDAY))
			returnDate = DataUtils.addDays(returnDate, 1);

		return returnDate;
	}

	public void notifyDelayeds() {
		List<Rent> rentals = rentDao.getDelayedRentals();
		for (Rent rent : rentals) {
			if(rent.getReturnDate().before(dateService.getDate()))
				emailService.notifyDelayedUsers(rent.getUser());
		}
	}
}