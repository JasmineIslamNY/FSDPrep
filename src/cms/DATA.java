package cms;

import java.util.HashMap;

public class DATA {
	static Integer orderCount;
	static String [] dealerID;
	static String [] commodity;
	static HashMap dBase;
	
	public DATA () {
		orderCount = 0;
		dealerID = new String[] {"DBDB", "JPM", "UBS", "RBC", "BARX", "MS", "CITI", "BOFA", "RBS", "HSBC"};
		commodity = new String[] {"GOLD", "SILV", "PORK", "OIL", "RICE"};
		dBase = new HashMap(100);
	}
	
	
	

}
