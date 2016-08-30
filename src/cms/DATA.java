package cms;

import java.util.ArrayList;
import java.util.HashMap;

public class DATA {
	static Integer orderCount = 0;
	static String [] dealerID = new String[] {"DB", "JPM", "UBS", "RBC", "BARX", "MS", "CITI", "BOFA", "RBS", "HSBC"};
	static String [] commodity = new String[] {"GOLD", "SILV", "PORK", "OIL", "RICE"};
	private static HashMap<Integer, String[]> dBase = new HashMap(100);
	private static HashMap<String, ArrayList<Integer>> dBaseByDealerID = new HashMap(100);
	private static HashMap<String, ArrayList<Integer>> dBaseByCommodity = new HashMap(100);
	
	public String add(String [] addOrder) {
		//addOrder[0] = Dealer_ID, addOrder[1] = Buy|Sell, addOrder[2] = Commodity, addOrder[3] = Amount, addOrder[4] = Price
		String result = "";
		orderCount += 1;

		//check Commodity to make sure it's in the list
		if (!(checkCommodity(addOrder[2]))) {
			result = "UNKNOWN_COMMODITY \n";
			return result;
		}
		
		//add entry to database of outstanding orders
		//Key is the orderCount and value is the array of the order
		dBase.put(orderCount, addOrder);
		
		/*Add an index to keep track of dealer orders.  It's a hashmap where the key is the DealerID and the  value is a ArrayList of the keys of dBase 
		 * 1. First check if it already exists
		 * 2a. If exists, retrieve ArrayList, add the new orderID to it, and then replace the existing key/value pair
		 * 2b. If it does not exist, create a new ArrayList, add the new orderID to it, and then add a key/value pair to the HashMap
		 */
		if (dBaseByDealerID.containsKey(addOrder[0])) {
			ArrayList<Integer> arrayList = (ArrayList<Integer>) dBaseByDealerID.get(addOrder[0]);
			arrayList.add(orderCount);
			dBaseByDealerID.replace(addOrder[0], arrayList);
		}
		else {
			ArrayList<Integer> arrayList = new ArrayList<Integer>();
			arrayList.add(orderCount);
			dBaseByDealerID.put(addOrder[0], arrayList);
		}
		
		/*Add an index to keep track of orders by commodity.  It's a hashmap where the key is the commodity and the  value is a ArrayList of the keys of dBase 
		 * 1. First check if it already exists
		 * 2a. If exists, retrieve ArrayList, add the new orderID to it, and then replace the existing key/value pair
		 * 2b. If it does not exist, create a new ArrayList, add the new orderID to it, and then add a key/value pair to the HashMap
		 */
		if (dBaseByCommodity.containsKey(addOrder[2])) {
			ArrayList<Integer> arrayList = (ArrayList<Integer>) dBaseByCommodity.get(addOrder[2]);
			arrayList.add(orderCount);
			dBaseByCommodity.replace(addOrder[2], arrayList);
		}
		else {
			ArrayList<Integer> arrayList = new ArrayList<Integer>();
			arrayList.add(orderCount);
			dBaseByCommodity.put(addOrder[2], arrayList);
		}
		
		
		
		return result;
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
	
	public boolean checkCommodity (String cmdty) {
		boolean result = false;
		for (int i=0; i < commodity.length; i++) {
			if (commodity[i].equals(cmdty)) {
				result = true;
			}
		}
		return result;
	}
	
	

}
