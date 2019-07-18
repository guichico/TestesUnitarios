package matchers;

import static com.br.guilherme.utils.DataUtils.verificarDiaSemana;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class DateMatcher extends TypeSafeMatcher<Date> {

	private Integer diaSemana;
	
	public DateMatcher(Integer diaSemana) {
		this.diaSemana = diaSemana;
	}
	
	public void describeTo(Description description) {
		Calendar data = Calendar.getInstance();
		data.set(Calendar.DAY_OF_WEEK, diaSemana);
		String dataExtenso = data.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("pt", "BR"));
		description.appendText(dataExtenso);
	}

	@Override
	protected boolean matchesSafely(Date data) {
		return verificarDiaSemana(data, diaSemana);
	}
}