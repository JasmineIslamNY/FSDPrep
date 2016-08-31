Name: Jasmine Islam
Title: Commodity Market System (CMS)
Description: Command line electronic trading platform for trading commodity contracts. Supports posting buy/sell orders, revoking orders, checking orders, listing orders, and aggressing on orders.
Extensions: None implemented
Design: 4 Classes
	CMS - contains the main method and is the UI for the app.  
		- passes user commands to BLOGIC and displays messages returned by BLOGIC to the user
	BLOGIC - controls the app
		- receives raw message from CMS
		- sends message to StringProcessor for command validation (character limit, correct number of codes, etc.)
		- contains the methods that process each of the available commands of the app
		- depending on the command, calls add, modify, delete, retrieve methods from the data storage class DATA
		- sends confirmation or error message back to CMS
	StringProcessor - performs initial validation of the command
		- receives raw message from BLOGIC
		- checks the command for size (255 or less) 
		- Breaks it into component pieces
		- Checks that tokens are between 2 and 6
		- Checks that the dealer sending the request is valid (uses class variable from DATA for this)
		- Uses try/catch to test convert price and amount 
		- Returns String array to caller
	DATA - provides the data store for the app
		- holds available commodities and dealer info
		- holds available/filled contracts
		- has indexes available to search by dealer/commodity
		- has methods to add, modify, delete, retrieve orders
Build/Run: download jar file and expand
		- open CMS folder in a terminal/cmd session
		- confirm cms folder is visible on ls/dir
		- java cms.CMS
	