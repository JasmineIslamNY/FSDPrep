package cms;

import java.util.HashMap;

public class DATA {
	static Integer orderCount = 0;
	String [] dealerID = new String[] {"DB", "JPM", "UBS", "RBC", "BARX", "MS", "CITI", "BOFA", "RBS", "HSBC"};
	String [] commodity = new String[] {"GOLD", "SILV", "PORK", "OIL", "RICE"};
	static HashMap dBase = new HashMap(100);
	
	public String add(String [] addOrder) {
		String result = "";
		orderCount += 1;
		//addOrder[0] = Dealer_ID, addOrder[1] = Buy|Sell, addOrder[2] = Commodity, addOrder[3] = Amount, addOrder[4] = Price
		
		
		return result;
	}
	
	public static boolean checkDealerID (String dealer) {
		boolean result = false;
		for (i=0; i < dealerID.length(); i++) {
			if (dealerID[i].equals(dealer)) {
				result = true;
			}
			return result;
		}
	
	}
	
	public static boolean checkCommodity (String cmdty) {
		boolean result = false;
		for (i=0; i < commodity.length(); i++) {
			if (commodity[i].equals(cmdty)) {
				result = true;
			}
			return result;
		}
	
	}
	
	

}
