//******************************************************************************
//
//  Developer:     Michael Franklin
//
//  Project #:     Project 8
//
//  File Name:     FileReader.java
//
//  Course:        COSC 4301 - Modern Programming
//
//  Due Date:      04/20/2022
//
//  Instructor:    Fred Kumi 
//
//  Description:   reads in data from a file as a string
//
//
//******************************************************************************

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileReader {
	
	private Scanner fileStream;
	
	
	//***************************************************************
	//
	//  Method:       OpenFileToRead
	// 
	//  Description:  opens a file at passed filename for reading
	//
	//  Parameters:   String: filename to input file
	//
	//  Returns:      boolean: true if open was successful, false
	//				  otherwise
	//
	//**************************************************************
	private boolean OpenFileToRead(String filename)
	{
		boolean success = false;
		
		// close an open file if one is open already
		if (fileStream != null)
		{
			CloseFile();
		}
		
		File file = new File(filename);
		try
		{
			this.fileStream = new Scanner(file);
			success = true;
		}
		catch (Exception e)
		{
			if (e instanceof IOException)
			{
				System.err.println("File IO Error while opening input file.");
			}
			else
			{
				System.err.println("Unhandled Error while input output file.");
			}
			e.printStackTrace();
			System.exit(0);
		}
		finally
		{
			if (!success)
			{
				// there was an error close the file
				CloseFile();
				//System.exit(0);
			}
		}
		
		return success;
	}
	
	
	//***************************************************************
	//
	//  Method:       readFromFile
	// 
	//  Description:  reads data from the open file
	//
	//  Parameters:   String: filename to input file
	//
	//  Returns:      String: data read from file
	//
	//**************************************************************
	public String readFromFile(String filename)
	{
		String data = "";
		boolean success = false;
		
		// open file
		OpenFileToRead(filename);
		
		// if there is a file open
		if (fileStream != null)
		{
			try
			{
				while (fileStream.hasNextLine()) 
				{
					// append line to data string
					data += fileStream.nextLine();

					// append a new line if there is a next line
					data += fileStream.hasNextLine() ? "\n" : "";
				}
				success = true;
			}
			catch (Exception e)
			{
				if (e instanceof IOException)
				{
					System.err.println("File IO Error while reading from input file.");
				}
				else
				{
					System.err.println("Unhandled Error while reading from input file.");
				}
				e.printStackTrace();
			}
			finally
			{
				// reading done, close file
				CloseFile();
				if (!success)
				{
					// there was an error, exit runtime
					System.exit(0);
				}
			}
		}
		
		return data;
	}

	
	//***************************************************************
	//
	//  Method:       CloseFile
	// 
	//  Description:  closes the open input file
	//
	//  Parameters:   None
	//
	//  Returns:      N/A
	//
	//**************************************************************
	private void CloseFile()
	{
		try
		{
			fileStream.close();
			
			// reset fileSteam as we no longer need it for this file
			fileStream = null;
		}
		catch (Exception e)
		{
			if (e instanceof IOException)
			{
				System.err.println("File IO Error while closing output file.");
			}
			else
			{
				System.err.println("Unhandled Error while closing output file.");
			}
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	
}
