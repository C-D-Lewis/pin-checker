package core;
import parser.Parser;

public class PVMain {

	public static void main(String[] args) {
		if(args.length < 1) {
			System.err.println("Please provide a pin JSON file!");
			System.exit(1);
		} else if(args[0].contains(".json")) {
			// Check other args
			for(String s : args) {
				if(s.contains("-v")) {
					Config.VERBOSE = true;
				}
			}
			
			// Launch
			new Parser(args[0]);
		} else {
			System.err.println("Please provide a .json file.");
		}
	}
	
}
