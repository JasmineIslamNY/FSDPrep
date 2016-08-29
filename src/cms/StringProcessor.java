package cms;
import  java.util.StringTokenizer;

public class StringProcessor {
	
	public String[] process (String order) {
		String [] result;

		/**
		 * This class processes the string command entered by a user/system.  
		 * It checks the command for size (255 or less) 
		 * Breaks it into component pieces
		 * Checks that tokens are between 3 and 6
		 * Uses try/catch to test convert price and amount 
		 * Returns String array to caller
		 */
		
		//check length of command user input is 255 or less
		if (order.length() > 255) {
			result = new String[]{"Error", "Command length is more than 255 characters"};
			return result;
		}
		
		// take the string that user input and break it up into an array 
		result = tokenize(order);
		
		//check that Price can be converted to Double
		if (result[1].equals("POST")) {
			try {
				Double d = Double.parseDouble(result[5]);
			}
			catch (NumberFormatException e){
				result[0] = "INVALID_MESSAGE";
				result[1] = "Price was not input correctly";
				
			}
		}
		
		//check that Amount can be converted to Integer for POST
		if (result[1].equals("POST")) {
			try {
				Integer i = Integer.parseInt(result[4]);
			}
			catch (NumberFormatException e){
				result[0] = "INVALID_MESSAGE";
				result[1] = "Amount was not input correctly";
				
			}
		}
		//check that Amount can be converted to Integer for AGGRESS
		if (result[1].equals("AGGRESS")) {
			try {
				Integer i = Integer.parseInt(result[3]);
			}
			catch (NumberFormatException e){
				result[0] = "INVALID_MESSAGE";
				result[1] = "Amount was not input correctly";
				
			}
		}
		
		return result;
		
	}
	
	private String[] tokenize(String order) {
		String [] tempResult;
		StringTokenizer tok = new StringTokenizer(order);
		int numberOfTokens = tok.countTokens();
		//check in the case that tokens are between 3 and 6
		if (numberOfTokens < 3 || numberOfTokens > 6) {
			tempResult = new String[] {"INVALID_MESSAGE", "Command not formatted correctly"};
		}
		else {
			tempResult = new String[numberOfTokens];
			for (int i=0; i < numberOfTokens; i++) {
				tempResult[i] = tok.nextToken();
			}
		}
		return tempResult;
	}


}
