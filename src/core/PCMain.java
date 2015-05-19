package core;
import parser.Parser;

public class PCMain {

	public static void main(String[] args) {
		if(args.length < 1) {
			System.err.println("Please provide a pin JSON file!");
			System.exit(1);
		} else if(args[0].contains(".json")) {
			new Parser(args[0]);
		} else {
			System.err.println("Please provide a .json file.");
		}
	}
	
}
