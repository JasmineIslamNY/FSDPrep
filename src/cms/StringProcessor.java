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
		 * Checks that the dealer sending the request is valid
		 * Uses try/catch to test convert price and amount 
		 * Returns String array to caller
		 */
		
		//check length of command user input is 255 or less
		if (order.length() > 255) {
			result = new String[]{"INVALID_MESSAGE", "Command length limited to 255 characters"};
			return result;
		}
		
		// take the string that user input and break it up into an array 
		result = tokenize(order);
		
		//check that the message is not Invalid
		if (result[0].equals("INVALID_MESSAGE")) {
			return result;
		}
		
		//check that the dealer sending the request is valid
		if (!(DATA.checkDealerID(result[0]))) {
			result[0] = "UNAUTHORIZED";
			result[1] = "You are not authorized to use this system";
			return result;
		}
		
		//check POST command
		if (result[1].equals("POST")) {
			//check that Price can be converted to Double
			try {
				Double d = Double.parseDouble(result[5]);
			}
			catch (NumberFormatException e){
				result[0] = "INVALID_MESSAGE";
				result[1] = "Price was not input correctly";	
			}
			//check that Amount can be converted to Integer for POST
			try {
				Integer i = Integer.parseInt(result[4]);
			}
			catch (NumberFormatException e){
				if (result[0].equals("INVALID_MESSAGE")) {
					result[1] = "Amount was not input correctly and " + result[1];
				}
				else {
				result[0] = "INVALID_MESSAGE";
				result[1] = "Amount was not input correctly";
				}	
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