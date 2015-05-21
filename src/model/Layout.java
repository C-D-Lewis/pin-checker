package model;

public class Layout {
	
	public static final String 
		TYPE_GENERIC_PIN = "genericPin",
		TYPE_CALENDAR_PIN = "calendarPin",
		TYPE_WEATHER_PIN = "weatherPin",
		TYPE_SPORTS_PIN = "sportsPin",
		TYPE_GENERIC_NOTIFICATION = "genericNotification",
		TYPE_GENERIC_REMINDER = "genericReminder";
	
	private static final String[] VALID_TYPES = {
		TYPE_GENERIC_PIN,
		TYPE_CALENDAR_PIN,
		TYPE_WEATHER_PIN,
		TYPE_SPORTS_PIN,
		TYPE_GENERIC_NOTIFICATION,
		TYPE_GENERIC_REMINDER
	};
	
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
