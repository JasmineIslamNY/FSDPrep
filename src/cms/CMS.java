/**
 * @author jislam
 * @description This is the starting point for the application.  main method will have three options, base (no extensions), ext1 (run on single TCP connection), ext2 (create multiple TCP connections). 
 */
package cms;
import java.io.*;

public class CMS {

	public static void main(String[] args) 
	{
		if (args.length < 1)
		{
			System.out.println("Please retry with one of these three arguments: \n "
					+ "\t base - Communication may be done using system.in/system.out \n"
					+ "\t ext1 - Communication may occur over a single TCP connection \n"
					+ "\t ext2 - Communication may occur over multiple TCP connections  \n");
			System.exit(-1);
		}
		
		if (args[0].equals("base")) {
			System.out.println("Selected base \n");
			processKeyboardInput();
		}
		else if (args[0].equals("ext1")) {
			System.out.println("ext1 has not been implemented yet \n");
			System.exit(1);
		}
		else if (args[0].equals("ext2")) {
			System.out.println("ext2 has not been implemented yet \n");
			System.exit(2);
		}
		else {
			System.out.println("Please retry with one of these three arguments: \n "
					+ "\t base - Communication may be done using system.in/system.out \n"
					+ "\t ext1 - Communication may occur over a single TCP connection \n"
					+ "\t ext2 - Communication may occur over multiple TCP connections  \n");
			System.exit(-1);
		}
		
		
										
}
	
	public static void processKeyboardInput() {
		try 
		{
			InputStreamReader	kbd = new InputStreamReader(System.in);	 
			BufferedReader kbdBuf = new BufferedReader(kbd);	
			BLOGIC bLogic = new BLOGIC();

	      		while(true)
	      		{
				System.out.println
					("Enter command please: (PUT A TIMER HERE - enter for now) \n");

				String input = kbdBuf.readLine();				

				if (input.equals(""))						
		      			System.exit(0);
													
				System.out.println("Processing: " + input + "\n");
				System.out.println(bLogic.processor(input) + "\n");

			
		      	}									
		}
		catch (IOException e)							
		{
	      		System.err.println(e);						
		}	
	}

}
