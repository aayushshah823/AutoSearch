
/*
 * testing.java
 */
import java.util.*;
import java.io.*;

/**
 * client class
 * @author Aayush Shah
 * @version 3/11/17
 */
public class testing{

	/**
	 * Application methods
	 * @param args The command line arguments
	 * @throws FileNotFoundException throws exception if file not found
	 */
	public static void main(String[] args) throws FileNotFoundException  {  

		// Asking user for details
		Scanner ip = new Scanner(System.in);
		System.out.print("Enter the name of file: ");
		String filename = ip.nextLine();
		File inputFile = new File(filename);
		while(!inputFile.exists()){
			System.out.print("File Not Found. Enter the name of file again: ");
			filename = ip.nextLine();
			inputFile = new File(filename);
		}
		Scanner reader = new Scanner(inputFile);



		int N = reader.nextInt();   
		Term[] terms = new Term[N];      
		for (int i = 0; i < N; i++) {      
			long weight = reader.nextLong();         
			// read the next weight       
			String query = reader.nextLine();       
			// read the next query        
			terms[i] = new Term(query.replaceFirst("\t",""), weight);   
			// construct the term     
		}  



		int k = 0;
		do{
			System.out.println("Enter how many matching terms do you want to see:"); 
			k = ip.nextInt();
			if(k<0){
				System.out.println("Number should be >= 0: ");
			}
		}while(!(k>0));


		Autocomplete autocomplete = new Autocomplete(terms);        

		// Read in queries from standard input and print out the top k matching terms     
		System.out.println("Enter the term you are searching for. Enter * to exit");    
		String prefix = ip.next();   

		while(!(prefix.equals("*"))){
			// Autocomplete autocomplete = new Autocomplete(terms);        
			Term[] results = autocomplete.allMatches(prefix);    
			System.out.println(results.length);     
			for(int i = 0; i < Math.min(k,results.length); i++)     
				System.out.println(results[i].toString()); 

			System.out.println("\n\nEnter the term you are searching for. Enter * to exit");    
			prefix = ip.next();
		}

		System.out.println("Done!");   
	}
}
