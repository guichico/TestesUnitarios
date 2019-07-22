package com.br.guilherme.daos;

import java.util.List;

import com.br.guilherme.entities.Rent;

public interface RentDAO {

	public void save(Rent rent);
	
	public List<Rent> getDelayedRentals();
}
