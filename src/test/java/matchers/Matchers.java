package matchers;

import java.util.Calendar;

public class Matchers {

	public static DateMatcher isToday() {
		return new DateMatcher(0);
	}

	public static DateMatcher isTomorrow() {
		return new DateMatcher(1);
	}
	
	public static DateMatcher isMonday(){
		return new DateMatcher(Calendar.MONDAY);
	}
}