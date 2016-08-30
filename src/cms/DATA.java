package cms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DATA {
	static Integer orderCounter = 0;
	static String [] dealerID = new String[] {"DB", "JPM", "UBS", "RBC", "BARX", "MS", "CITI", "BOFA", "RBS", "HSBC"};
	static String [] commodity = new String[] {"GOLD", "SILV", "PORK", "OIL", "RICE"};
	private static HashMap<String, String[]> dBase = new HashMap(100);
	private static HashMap<String, ArrayList<String>> dBaseByDealerID = new HashMap(100);
	private static HashMap<String, ArrayList<String>> dBaseByCommodity = new HashMap(100);
	
	public String add(String [] addOrder) {
		/*Steps to add order:
		 * 1. Increment orderCounter
		 * 2. Check commodity to make sure it's in the list
		 * 3. Add to database HashMap, key is the orderCounterString and value is the addOrder Array
		 * 4. Add orderCounterString to the index for DealerID searches - this has three steps of it's own
		 * 5. Add orderCounterString to the index for Commodity searches - this has three steps of it's own
		 * 6. Craft success message
		 * 7. If add fails, display stack trace for debugging
		 * Note - this is the format for the addOrder Array: //addOrder[0] = Dealer_ID, addOrder[1] = Buy|Sell, addOrder[2] = Commodity, addOrder[3] = Amount, addOrder[4] = Price
		*/
		String result = "";
		//Step 1: Increment orderCounter
		orderCounter += 1;
		String orderCounterString = String.valueOf(orderCounter);

		//Step 2: Check Commodity to make sure it's in the list
		if (!(checkCommodity(addOrder[2]))) {
			result = "UNKNOWN_COMMODITY";
			return result;
		}
		
		try {
			//Step 3: Add to database HashMap, key is the orderCounterString and value is the addOrder Array
			dBase.put(orderCounterString, addOrder);
			
			//Step 4: Add to the DealerID Index
			/*Add an index to keep track of dealer orders.  It's a hashmap where the key is the DealerID (addOrder[0]) and the  value is a ArrayList of the keys of dBase 
			 * 4a. First check if it already exists
			 * 4b. If exists, retrieve ArrayList, add the new orderID to it, and then replace the existing key/value pair
			 * 4c. If it does not exist, create a new ArrayList, add the new orderID to it, and then add a key/value pair to the HashMap
			 */
			if (dBaseByDealerID.containsKey(addOrder[0])) {
				ArrayList<String> arrayList = (ArrayList<String>) dBaseByDealerID.get(addOrder[0]);
				arrayList.add(orderCounterString);
				dBaseByDealerID.replace(addOrder[0], arrayList);
			}
			else {
				ArrayList<String> arrayList = new ArrayList<String>();
				arrayList.add(orderCounterString);
				dBaseByDealerID.put(addOrder[0], arrayList);
			}
			
			//Step 5. Add to the Commodity Index
			/*Add an index to keep track of orders by commodity.  It's a hashmap where the key is the commodity (addOrder[2]) and the  value is a ArrayList of the keys of dBase 
			 * 5a. First check if it already exists
			 * 5b. If exists, retrieve ArrayList, add the new orderID to it, and then replace the existing key/value pair
			 * 5c. If it does not exist, create a new ArrayList, add the new orderID to it, and then add a key/value pair to the HashMap
			 */
			if (dBaseByCommodity.containsKey(addOrder[2])) {
				ArrayList<String> arrayList = (ArrayList<String>) dBaseByCommodity.get(addOrder[2]);
				arrayList.add(orderCounterString);
				dBaseByCommodity.replace(addOrder[2], arrayList);
			}
			else {
				ArrayList<String> arrayList = new ArrayList<String>();
				arrayList.add(orderCounterString);
				dBaseByCommodity.put(addOrder[2], arrayList);
			}
			
			//Step 6. Craft Success message
			result = orderCounterString + " " + addOrder[0] + " " + addOrder[1] + " " + addOrder[2] + " " + addOrder[3] + " " + addOrder[4] + " HAS BEEN POSTED";
		} catch (Exception e) {
			//Step 7. If add fails, display stacktrace for debug
			e.printStackTrace();
		}
		return result;
	}
	
	public String delete(String orderNumber, String dealerID, String commodity) {
		String result = "";
		try {
			//Delete from dealerid index
			ArrayList<String> arrayListDealer = (ArrayList<String>) dBaseByDealerID.get(dealerID);
			arrayListDealer.remove(orderNumber);
			dBaseByDealerID.replace(dealerID, arrayListDealer);
				
			//Delete from commodity index
			ArrayList<String> arrayListCommodity = (ArrayList<String>) dBaseByCommodity.get(commodity);
			arrayListCommodity.remove(orderNumber);
			dBaseByCommodity.replace(commodity, arrayListCommodity);
				
			//Delete from Database
			dBase.remove(orderNumber);
				
			//Craft revoked message
			result = orderNumber + " HAS BEEN REVOKED";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public String modify(String orderNumber, String [] modifyOrder) {
		//Format for the modifyOrder Array: modifyOrder[0] = Dealer_ID, modifyOrder[1] = Buy|Sell, modifyOrder[2] = Commodity, modifyOrder[3] = Amount, modifyOrder[4] = Price
		String result = "SUCCESS";
		try {
			dBase.replace(orderNumber, modifyOrder);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result = "UNKNOWN_ERROR";
		}
		return result;
	}
	
	public String[] retrieveAllOrders () {
		String[] tempAllOrdersArray = (String[]) dBase.keySet().toArray();
		if (tempAllOrdersArray != null){
			return tempAllOrdersArray;
		}
		else {
			String[] resultArray = new String[] {"0"};
			return resultArray;
		} 
	}
	
	public String[] retrieveByOrderID (String orderID) {
		String[] tempOrderArray = (String[]) dBase.get(orderID);
		if (tempOrderArray != null){
			return tempOrderArray;
		}
		else {
			String[] result = {"UNKNOWN_ORDER"};
			return result;
		}
	}
	
	public String [] retrieveByDealerID (String dealerID) {
		if (dBaseByDealerID.containsKey(dealerID)) {
			ArrayList<String> resultList = (ArrayList<String>) dBaseByDealerID.get(dealerID);
			String[] resultArray = Arrays.copyOf(resultList.toArray(), resultList.toArray().length, String[].class);
			return resultArray;
		}
		else {
			String[] resultArray = new String[] {"0"};
			return resultArray;
		}
	}
	
	public String [] retrieveByCommodity (String commodity) {
		if (dBaseByCommodity.containsKey(commodity)) {
			ArrayList<String> resultList = (ArrayList<String>) dBaseByCommodity.get(commodity);
			String[] resultArray = Arrays.copyOf(resultList.toArray(), resultList.toArray().length, String[].class);
			return resultArray;
		}
		else {
			String[] resultArray = new String[] {"0"};
			return resultArray;
		}
	}
	
	public String [] retriveByCmdtyAndDlr (String commodity, String dealerID) {
		//Could not find a more efficient way of doing this :( - good thing is this is a sorted array
		String[] tempArraySmall;
		String[] tempArrayLarge;
		
		String[] byCommodityArray = retrieveByCommodity(commodity);
		String[] byDealerArray = retrieveByDealerID(dealerID);
		
		//Check for empty array from either commodity/dealer
		if (byCommodityArray[0].equals("0") || byDealerArray[0].equals("0")) {
				String[] resultArray = new String[] {"0"};
				return resultArray;
			}
		//Check which array is smaller, that's the one we'll loop
		if (byCommodityArray.length < byDealerArray.length) {
			tempArraySmall = byCommodityArray;
			tempArrayLarge = byDealerArray;
		}
		else {
			tempArraySmall = byDealerArray;
			tempArrayLarge = byCommodityArray;
		}
		
		//construct arraylist with initial size of smaller temparray - to add orderIDs that exist in both arrays
		ArrayList<String> tempResultList = new ArrayList<String>(tempArraySmall.length);  
		
		//do a binary search on the larger array 
		for (int i=0; i < tempArraySmall.length; i++) {
			int match = Arrays.binarySearch(tempArrayLarge, tempArraySmall[i]);
			if (match >= 0) {
				tempResultList.add(tempArraySmall[i]);
			}
		}
		
		//check for empty result set
		if (tempResultList.size() == 0) {
			String[] resultArray = new String[] {"0"};
			return resultArray;
		}
		//create an String array from temp arraylist
		String[] resultArray = (String[]) tempResultList.toArray();
		return resultArray;
	}
	
	public static boolean checkDealerID (String dealer) {
		boolean result = false;
		for (int i=0; i < dealerID.length; i++) {
			if (dealerID[i].equals(dealer)) {
				result = true;
			}
		}
		return result;
	}
	
	public static boolean checkCommodity (String cmdty) {
		boolean result = false;
		for (int i=0; i < commodity.length; i++) {
			if (commodity[i].equals(cmdty)) {
				result = true;
			}
		}
		return result;
	}
	
	

}
