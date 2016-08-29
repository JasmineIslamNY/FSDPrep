package cms;

public class BLOGIC {
	protected static String [] availableCommands = {"POST", "REVOKE", "CHECK", "LIST", "AGGRESS"};

	public String processor(String order) {
		/**
		 * This method processes the command entered by a user/system.  
		 * It checks the command for size (255 or less) 
		 * Breaks it into component pieces
		 * Uses try/catch to convert price and amount 
		 * Invokes proper method as per message 
		 * Returns message/result to caller
		 */
		String result = "";
		
		//Convert command to an array and check for size/price/amount
		StringProcessor sp = new StringProcessor();
		String [] resultArray = sp.process(order);
		
		//Check for error and report back to user if needed
		if (resultArray[0].equals("INVALID_MESSAGE")) {
			result = resultArray[0] + ": " + resultArray[1] + "\n";
			return result;
		}
		
		//Process the command
		switch (resultArray[1]) {
		case "POST":
			result = processPost(resultArray);
			break;
		case "REVOKE":
			result = processRevoke(resultArray);
			break;
		case "CHECK":
			result = processCheck(resultArray);
			break;
		case "LIST":
			result = processList(resultArray);
			break;
		case "AGGRESS":
			result = processAggress(resultArray);
			break;
		}
	
		//Return result to method caller
		return result;
	}
	
	private String processPost(String [] tempArray) {
		String tempString = "";
		
		return tempString;
	}
	private String processRevoke(String [] tempArray) {
		String tempString = "";
		
		return tempString;
	}	
	private String processCheck(String [] tempArray) {
		String tempString = "";
		
		return tempString;
	}	
	private String processList(String [] tempArray) {
		String tempString = "";
		
		return tempString;
	}	
	private String processAggress(String [] tempArray) {
		String tempString = "";
		
		return tempString;
	}

}
