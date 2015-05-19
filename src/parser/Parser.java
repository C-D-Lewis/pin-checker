package parser;

import java.io.File;
import java.io.FileReader;

import model.Layout;
import model.Pin;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * Read input file, validate required fields, read data into Java model (model)
 * @author Chris Lewis
 */
public class Parser {
	
	private JSONObject pinJson;
	private Pin pin = new Pin();
	
	public Parser(String filePath) {
		try {
			parse(filePath);
			if(check()) {
				System.out.println("Pin is valid!");
			} else {
				System.out.println("Pin is NOT valid! See reasons above");
			}
		} catch (Exception e) {
			System.err.println("Error checking pin: " + e.getLocalizedMessage());
		}
	}

	private void parse(String filePath) throws Exception {
		// Parse JSON file
		File input = new File(filePath);
		JSONParser parser = new JSONParser();
		Object json = parser.parse(new FileReader(input));
		pinJson = (JSONObject)json;
		System.out.println("Parsed: " + pinJson.toString());
	}
	
	private boolean check() {
		return checkRoot() && checkLayout();
	}

	private boolean checkRoot() {
		// Check essential fields
		if(pinJson.get("id") == null) return err("Pin does not have 'id'!");
		pin.id = (String)pinJson.get("id");
		
		if(pinJson.get("layout") == null) return err("Pin does not have 'layout'!");
		pin.layout = new Layout();
		
		// Check time format
		if(pinJson.get("time") == null) return err("Pin does not have 'time'!");
		String time = (String)pinJson.get("time");
		if(!isISOTime(time)) return err("'time' is not in ISO format. e.g.; YYYY-MM-DDTHH:MM:SSZ");
		pin.time = (String)pinJson.get("time");

		// All passed!
		return true;
	}
	
	private boolean checkLayout() {
		JSONObject layout = (JSONObject)pinJson.get("layout");
		if(layout == null) return err("Pin does not have a 'layout!'");
		
		// Check pin type
		if(layout.get("type") == null) return err("Pin does not have a layout 'type'!");
		String type = (String)layout.get("type");
		if(!Layout.typeIsValid(type)) {
			System.err.println("Pin layout 'type' is not one of the valid types.");
			Layout.printValidTypes();
			return false;
		}
		pin.layout.type = type;
		
		// Title is required
		if(layout.get("title") == null) return err("Pin layout has no 'title'!");
		pin.layout.title = (String)layout.get("title");

		
		if(!type.equals(Layout.TYPE_CALENDAR_PIN)) {
			// Only one that doesn't require tinyIcon
			String tinyIcon = (String)layout.get("tinyIcon");
			if(tinyIcon == null) return err("Pin layout does not have a 'tinyIcon!'");
			pin.layout.tinyIcon = tinyIcon;
		}
		
		// Both sportsPin and weatherPin require largeIcon
		if(type.equals(Layout.TYPE_SPORTS_PIN) || type.equals(Layout.TYPE_WEATHER_PIN)) {
			String largeIcon = (String)layout.get("largeIcon");
			if(largeIcon == null) return err("Pin layout does not have a 'largeIcon!'");
			pin.layout.largeIcon = largeIcon;
		}
		
		// calendarPin, weatherPin, and genericReminder have optional locationName
		if(type.equals(Layout.TYPE_CALENDAR_PIN)
		|| type.equals(Layout.TYPE_WEATHER_PIN)
		|| type.equals(Layout.TYPE_GENERIC_REMINDER)) {
			if(layout.get("locationName") != null) {
				pin.layout.locationName = (String)layout.get("locationName");
			}
		}
		
		// Read optional fields if they're there, without validation
		if(layout.get("subtitle") != null) {
			pin.layout.subtitle = (String)layout.get("subtitle");
		}
		if(layout.get("body") != null) {
			pin.layout.body = (String)layout.get("body");
		}
		if(layout.get("primaryColor") != null) {
			pin.layout.primaryColor = (String)layout.get("primaryColor");
		}
		if(layout.get("secondaryColor") != null) {
			pin.layout.secondaryColor = (String)layout.get("secondaryColor");
		}
		if(layout.get("backgroundColor") != null) {
			pin.layout.backgroundColor = (String)layout.get("backgroundColor");
		}
		
		// All passed!
		return true;
	}
	
	private boolean isISOTime(String input) {
		// YYYY-MM-DDTHH:MM:SSZ
		try {
			int year = Integer.parseInt(input.substring(0, 4));
			if(year < 1970) return err("Year is before 1970");
			
			int month = Integer.parseInt(input.substring(5, 7));
			if(month < 0 || month > 12) return err("Month is < 0 or > 12!");
			
			int day = Integer.parseInt(input.substring(8, 10));
			if(day < 0 || day > 31) return err("Day is < 0 or > 31!");
			
			if(input.charAt(10) != 'T') return err("'time' does not use 't' to separate date and time.");
			
			int hour = Integer.parseInt(input.substring(11, 13));
			if(hour < 0 || hour > 24) return err("Hour is < 0 or > 12!");
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return err("'time' is not ISO format!");
		}
	}
	
	private boolean err(String reason) {
		System.err.println("ERROR: " + reason);
		return false;
	}
}
