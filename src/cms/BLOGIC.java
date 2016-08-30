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
			result = resultArray[0] + ": " + resultArray[1];
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
		/*Steps to add order:
		 * 1. Create a new array from resultArray (which is the command user input) by removing the Command (POST) from it 
		 * 2. Send this to DATA as using the add method
		 * 3. DATA will respond with the POST_CONFIRMATION message (or error message if it's an unknown commodity or there is a save error)
		 * 4. Pass message to calling method
		 */
		String result = "";
		//resultArray[0] = Dealer_ID, resultArray[2] = Buy|Sell, resultArray[3] = Commodity, resultArray[4] = Amount, resultArray[5] = Price
		String [] addOrder = {resultArray[0], resultArray[2], resultArray[3], resultArray[4], resultArray[5]};
		result = dStore.add(addOrder);
		return result;
	}
	private String processRevoke(String [] resultArray) {
		String result = "";
		/*Steps to revoke order:
		 * 1. Retrieve order from dBase
		 * 2. Check if order exists, error message if it doesn't
		 * 3. Confirm dealer making the request is the dealer who created it - error message if not
		 * 4. Delete from database (this includes the success message)
		 * Note: format of resultArray - resultArray[0] = Dealer_ID, resultArray[1] = REVOKE, resultArray[2] = OrderID
		 * Note: format of order - order[0] = Dealer_ID, order[1] = Buy|Sell, order[2] = Commodity, order[3] = Amount, order[4] = Price
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
				result = "UNAUTHORIZED: Only the dealer who created the order can REVOKE";
				return result;
				}
			else {
				//Step 4: Delete from database
				result	= dStore.delete(resultArray[2], order[0], order[2]);
				}
		}	
		return result;
	}
	private String processCheck(String [] resultArray) {
		String result = "";
		/*Steps to check order:
		 * 1. Retrieve order from dBase
		 * 2. Check if order exists, error message if it doesn't
		 * 3. Confirm dealer making the request is the dealer who created it - error message if not
		 * 4. Check Amount
		 * 4a. If Amount > 0, send an ORDER_INFO Message
		 * 4b. If Amount = 0, send a FILLED message 
		 * Note: format of resultArray - resultArray[0] = Dealer_ID, resultArray[1] = CHECK, resultArray[2] = OrderID
		 * Note: format of order - order[0] = Dealer_ID, order[1] = Buy|Sell, order[2] = Commodity, order[3] = Amount, order[4] = Price
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
				result = "UNAUTHORIZED: Only the dealer who created the order can CHECK";
				return result;
				}
			else {
				//Step 4a: If Amount > 0, create an ORDER_INFO message
				if (Integer.parseInt(order[3]) > 0) {
					result = resultArray[2] + " " + resultArray[0] + " " + order[1] + " " + order[2] + " " + order[3] + " " + order[4]; 
					}
				//Step 4b: If Amount = 0, create a FILLED message
				else if (Integer.parseInt(order[3]) == 0) {
					result = resultArray[2] + " HAS BEEN FILLED";
					}
				else {
					result = "UNKNOWN_ERROR";
					}
				}
				}	
		return result;
		}	
	
	private String processList(String [] resultArray) {
		String result = "";
		/*Steps to check order:
		 * 1. Check Command to see if there are optional arguments
		 * 1a. If no arguments, call retrieveAllOrders() from DATA
		 * 1b. If Commodity, call retrieveByCommodity() from DATA
		 * 1c. If Dealer, call retrieveByDealerID() from DATA
		 * 2. Create a string called ORDER_INFO_LIST
		 * 3. All three return an integer array with Order IDs - loop through this array
		 * 3a. For each order id, call retrieveByOrderID()
		 * 3b. Check if "UNKNOWN_ORDER" message returned - append to ORDER_INFO_LIST as well
		 * 3c. Else create an ORDER_INFO message and append to ORDER_INFO_LIST message
		 * 3d. When loop finished, append " END OF LIST" to ORDER_INFO_LIST
		 * Note: format of resultArray - resultArray[0] = Dealer_ID, resultArray[1] = LIST, resultArray[2] = COMMODITY|DEALER (optional)
		 * Note: format of order - order[0] = Dealer_ID, order[1] = Buy|Sell, order[2] = Commodity, order[3] = Amount, order[4] = Price
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
				result = "UNAUTHORIZED: Only the dealer who created the order can CHECK";
				return result;
				}
			else {
				//Step 4a: If Amount > 0, send an ORDER_INFO message
				if (Integer.parseInt(order[3]) > 0) {
					result = resultArray[2] + " " + resultArray[0] + " " + order[1] + " " + order[2] + " " + order[3] + " " + order[4]; 
					}
				//Step 4b: If Amount = 0, send a FILLED message
				else if (Integer.parseInt(order[3]) == 0) {
					result = resultArray[2] + " HAS BEEN FILLED";
					}
				else {
					result = "UNKNOWN_ERROR";
					}
				}
				}	
		return result;
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
