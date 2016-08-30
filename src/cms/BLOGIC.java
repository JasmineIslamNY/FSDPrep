package cms;

import java.util.ArrayList;

public class BLOGIC {
	protected static String [] availableCommands = {"POST", "REVOKE", "CHECK", "LIST", "AGGRESS"};
	//Initialize DATA for the datastorage
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
		
		//Check for error and report back to user if needed
		if (resultArray[0].equals("INVALID_MESSAGE") || resultArray[0].equals("UNAUTHORIZED")) {
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
		String result = "";
		/*Steps to revoke order:
		 * 1. Retrieve order from dBase
		 * 2. Check if order exists, error message if it doesn't
		 * 3. Confirm dealer making the request is the dealer who created it
		 * 4. Delete from database (this includes the success message)
		 * Note: format of resultArray - resultArray[0] = Dealer_ID, resultArray[1] = REVOKE, resultArray[2] = OrderID
		 * Note: format of order - order[0] = Dealer_ID, order[2] = Buy|Sell, order[3] = Commodity, order[4] = Amount, order[5] = Price
		*/
		//Step 1: Retrieve order from dBase
		String[] order = (String[]) dStore.retrieveByOrderID(resultArray[2]);
		//Step 2: Check if order exists - error message if it doesn't
		if (order[0].equals("UNKNOWN_ORDER")) {
			result = "UNKNOWN_ORDER";
			return result;
			}
		else {
			//Step 3: Confirm dealer is authorized - error message if not
			if (!(resultArray[0].equals(order[0]))) {
				result = "UNAUTHORIZED: Only the dealer who created the order can Revoke";
				return result;
				}
			else {
				//Step 4: Delete from database
				result	= dStore.delete(resultArray[2], order[0], order[3]);
				}
		}	
		return result;
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
		
		if (modifyOrder[1].equals("BUY")) {
			String Action = "SOLD";
		}
		else if (modifyOrder[1].equals("SELL")) {
			String Action = "BOUGHT";
		}
		
		return tempString;
	}

}
