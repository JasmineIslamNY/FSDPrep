package cms;

public class BLOGIC {
	protected static String [] availableCommands = {"POST", "REVOKE", "CHECK", "LIST", "AGGRESS"};
	DATA dStore = new DATA();

	public String processor(String order) {
		/**
		 * This method processes the command entered by a user/system.  
		 * It checks the command for size (255 or less) 
		 * Breaks it into component pieces
		 * Checks that tokens are between 3 and 6
		 * Uses try/catch to convert price and amount 
		 * Invokes proper method as per message 
		 * Returns message/result to caller
		 */
		String result = "";
		
		//Convert command to an array and check for size/price/amount
		StringProcessor sp = new StringProcessor();
		String [] resultArray = sp.process(order);
		
		//Initialize DATA for the datastorage
		
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
		default:
			result = "INVALID_MESSAGE: Invalid Command";
			break;
		}
	
		//Return result to method caller
		return result;
	}
	
	private String processPost(String [] resultArray) {
		String tempString = "";
		//resultArray[0] = Dealer_ID, resultArray[2] = Buy|Sell, resultArray[3] = Commodity, resultArray[4] = Amount, resultArray[5] = Price
		String [] addOrder = {resultArray[0], resultArray[2], resultArray[3], resultArray[4], resultArray[5]};
		tempString = dStore.add(addOrder);
		return tempString;
	}
	private String processRevoke(String [] resultArray) {
		String tempString = "";
		
		return tempString;
	}	
	private String processCheck(String [] resultArray) {
		String tempString = "";
		
		return tempString;
	}	
	private String processList(String [] resultArray) {
		String tempString = "";
		
		return tempString;
	}	
	private String processAggress(String [] resultArray) {
		String tempString = "";
		
		return tempString;
	}

}
