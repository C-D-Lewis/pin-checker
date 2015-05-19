package model;

public class Layout {
	
	private static final String[] VALID_TYPES = {
		"genericPin",
		"calendarPin",
		"weatherPin",
		"sportsPin",
		"genericNotification",
		"genericReminder"
	};
	
	public static final int 
		TYPE_GENERIC_PIN = 0,
		TYPE_CALENDAR_PIN = 1,
		TYPE_WEATHER_PIN = 2,
		TYPE_SPORTS_PIN = 3,
		TYPE_GENERIC_NOTIFICATION = 4,
		TYPE_GENERIC_REMINDER = 5;
	
	public String type, title, subtitle, body, 
		tinyIcon, smallIcon, largeIcon, 
		primaryColor, secondaryColor, backgroundColor,
		locationName;
	
	public static boolean typeIsValid(String query) {
		for(String t : VALID_TYPES) {
			if(t.equals(query)) {
				return true;
			}
		}
		return false;
	}
	
	public static void printValidTypes() {
		for(String t : VALID_TYPES) {
			System.out.println(t);
		}
	}

}
