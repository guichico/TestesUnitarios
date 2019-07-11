package matchers;

import java.util.Date;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import com.br.guilherme.utils.DataUtils;

public class DateMatcher extends TypeSafeMatcher<Date> {

	private Integer days;
	
	public DateMatcher(Integer days) {
		this.days = days;
	}
	
	public void describeTo(Description description) {
		// TODO Auto-generated method stub
	}

	@Override
	protected boolean matchesSafely(Date data) {
		return DataUtils.isMesmaData(data, DataUtils.obterDataComDiferencaDias(days));
	}
}