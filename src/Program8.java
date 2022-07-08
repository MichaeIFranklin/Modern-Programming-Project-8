//******************************************************************************
//
//  Developer:     Michael Franklin
//
//  Project #:     Project 8
//
//  File Name:     Program8.java
//
//  Course:        COSC 4301 - Modern Programming
//
//  Due Date:      04/20/2022
//
//  Instructor:    Fred Kumi 
//
//  Description:   Test class to run and test the Project 8 classes.
//
//
//******************************************************************************

public class Program8
{
	private final String InputFilename = "GasPrices.txt";
	private final String DscOutputFilename = "Prices-Hi-Low.txt";
	private final String AscOutputFilename = "Prices-Low-Hi.txt";
	private Logger logger;
	private GasCalculator calculator;
	
	//***************************************************************
	//
	//  Method:       main
	// 
	//  Description:  The main method of the program
	//
	//  Parameters:   String array
	//
	//  Returns:      N/A 
	//
	//**************************************************************
	public static void main(String[] args)
	{	       
		// Create an object of the main class and use it to call
		// the non-static developerInfo method
		Program8 obj = new Program8();
		obj.developerInfo();

		// run setup
		obj.Setup();
		
		// begin calculations
		obj.CalculateData();
		
		// cleanup and close streams
		obj.CloseStreams();
	}

	
	//***************************************************************
	//
	//  Method:       Setup (Non Static)
	// 
	//  Description:  sets up needed systems for the program
	//
	//  Parameters:   None
	//
	//  Returns:      N/A 
	//
	//**************************************************************
	public void Setup()
	{
		// setup Logger instance, for writing to files later
		logger = new Logger();
		
		// read data from input file
		FileReader fileReader = new FileReader();		
		String gasDataString = fileReader.readFromFile(InputFilename);

		// initialize the GasCalculator sending in data for initial parsing
		calculator = new GasCalculator(gasDataString);
		
		// print that setup is complete
		System.out.println("Setup Complete");
	}

	
	//***************************************************************
	//
	//  Method:       CalculateData
	// 
	//  Description:  runs through all the required calculations and 
	//
	//  Parameters:   None
	//
	//  Returns:      N/A 
	//
	//**************************************************************
	public void CalculateData()
	{
		System.out.println("\nAverage Price Per Year:");
		calculator.AverageByYear();
		
		System.out.println("\nAverage Price Per Month:");
		calculator.AverageByMonth();
		
		System.out.println("\nHighest and Lowest Price Per Year:");
		calculator.MinMaxByYear();
		
		System.out.println("Prices Lowest to Highest:");
		logger.OpenFileForWriting(AscOutputFilename);
		logger.Log(calculator.SortByPrice(true));
		
		System.out.println("\nPrices Highest to Lowest:");
		logger.OpenFileForWriting(DscOutputFilename);
		logger.Log(calculator.SortByPrice(false));
	}
	
	
	//***************************************************************
	//
	//  Method:       CloseStreams
	// 
	//  Description:  closes all open streams
	//
	//  Parameters:   None
	//
	//  Returns:      N/A 
	//
	//**************************************************************
	public void CloseStreams()
	{
		logger.CloseFile();
	}
	
	
	//***************************************************************
	//
	//  Method:       developerInfo (Non Static)
	// 
	//  Description:  The developer information method of the program
	//
	//  Parameters:   None
	//
	//  Returns:      N/A 
	//
	//**************************************************************
	public void developerInfo()
	{
		System.out.println("Name:    Michael Franklin");
		System.out.println("Course:  COSC 4301 Modern Programming");
		System.out.println("Project: Eight\n");

	} // End of the developerInfo method

}

