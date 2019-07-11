package matchers;

public class Matchers {

	public static DateMatcher isToday() {
		return new DateMatcher(0);
	}

	public static DateMatcher isTomorrow() {
		return new DateMatcher(1);
	}
}