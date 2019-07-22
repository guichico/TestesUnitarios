package matchers;

import static com.br.guilherme.utils.DataUtils.verifyDayOfWeek;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class DateMatcher extends TypeSafeMatcher<Date> {

	private Integer dayOfWeek;
	
	public DateMatcher(Integer dayOfWeek) {
		this.dayOfWeek = dayOfWeek;
	}
	
	public void describeTo(Description description) {
		Calendar data = Calendar.getInstance();
		data.set(Calendar.DAY_OF_WEEK, dayOfWeek);
		String dateStr = data.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, new Locale("pt", "BR"));
		description.appendText(dateStr);
	}

	@Override
	protected boolean matchesSafely(Date date) {
		return verifyDayOfWeek(date, dayOfWeek);
	}
}