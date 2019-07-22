package com.br.guilherme.utils;

import static com.br.guilherme.utils.DataUtils.addDays;
import java.util.Date;

public class DateImpl implements DateService {

	public Date getDate() {
		return new Date();
	}

	public Date getDate(int days) {
		return addDays(getDate(), days);
	}
}