//******************************************************************************
//
 //  Developer:     Michael Franklin
//
//  Project #:     Project 8
//
//  File Name:     GasCalculator.java
//
//  Course:        COSC 4301 - Modern Programming
//
//  Due Date:      04/20/2022
//
//  Instructor:    Fred Kumi 
//
//  Description:   holds gas price records and functions for various 
//				   calculations to use on gas price data
//
//
//******************************************************************************

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.util.Pair;

public class GasCalculator {

	private List<GasData> records;
	
	public GasCalculator(String gasData)
	{
		// validate gasData
		if (!gasData.isEmpty())
		{
			// parse data
			ParseData(gasData);
		}
		else
		{
			// no gas data, show error
			throw new IllegalArgumentException("No Gas Data to parse");
		}
	}
	
	
	//***************************************************************
	//
	//  Method:       ParseData
	// 
	//  Description:  parses a passed data string and stores records into a 
	//				  list array
	//
	//  Parameters:   String: string to parse
	//
	//  Returns:      N/A 
	//
	//**************************************************************
	private void ParseData(String gasData)
	{
		// setup list
		records = new ArrayList<GasData>();
				
		// while there is still data to parse
		// (final line has no newline delimiter)
		while (!gasData.isEmpty())
		{
			// get next line to parse
			String line;
			if(gasData.contains("\n"))
				line = gasData.substring(0,gasData.indexOf("\n"));
			else
				line = gasData;
			
			// create a new record to hold gas data
			GasData record = new GasData();
			
			// parse line
			record.parseRecord(line);
			
			// add record to record list
			records.add(record);
			
			// remove the parsed line from our data
			if(gasData.contains("\n"))
				gasData = gasData.substring(gasData.indexOf("\n")+1);
			else
				gasData = "";
		}
	}
	
	
	@Override
	public String toString()
	{
		String output = "";
		
		for(int i = 0; i < records.size();i++)
		{
			output += records.get(i).getDate() + ":"
					+ records.get(i).getPrice() + "\n";
		}
		return output;
	}
	
	//***************************************************************
	//
	//  Method:       AverageByYear
	// 
	//  Description:  calculates and prints out the average gas price per year
	//
	//  Parameters:   None
	//
	//  Returns:      N/A 
	//
	//**************************************************************
	public void AverageByYear()
	{		
		// group and average yearly prices
		Map<Integer, Double> averagesPerYear = records.stream()
				.collect(
						Collectors.groupingBy(GasData::getYear,
						Collectors.averagingDouble(GasData::getPrice)));
		
		// print out averages
		averagesPerYear.entrySet().stream()
				.forEach(x -> System.out.printf("%d: $%.3f\n",x.getKey(),x.getValue()));
	}
	
	
	//***************************************************************
	//
	//  Method:       AverageByMonth
	// 
	//  Description:  calculates and prints out the average gas price per 
	//				  month
	//
	//  Parameters:   None
	//
	//  Returns:      N/A 
	//
	//**************************************************************
	public void AverageByMonth()
	{		
		// group and average yearly prices
		Map<Integer, Map<Integer,Double>> averagesPerMonth = records.stream()
				.collect(
						Collectors.groupingBy(GasData::getYear,
							Collectors.groupingBy(GasData::getMonth,
							Collectors.averagingDouble(GasData::getPrice))));
		
		// print out averages
		averagesPerMonth.entrySet().stream()
				.forEach(x -> {
					final int year = x.getKey();
					
					// for each month of the year
					x.getValue().entrySet().stream()
						.forEach(y -> System.out.printf("%02d-%d: $%.3f\n",
								y.getKey(),
								year,
								y.getValue()));
				});
		
	}
	
	
	//***************************************************************
	//
	//  Method:       MinMaxByYear
	// 
	//  Description:  calculates and prints out the highest and lowest gas 
	//				  price per year
	//
	//  Parameters:   None
	//
	//  Returns:      N/A 
	//
	//**************************************************************
	public void MinMaxByYear()
	{		
		// group and find the min and max yearly prices
		Map<Integer, List<Pair<String,Double>>> minMaxPerYear = records.stream()
				.collect(
						Collectors.groupingBy(GasData::getYear,
						Collectors.collectingAndThen(Collectors.toList(), list -> {
							// list that hold both min and max
							List<Pair<String,Double>> result = new ArrayList<Pair<String,Double>>();
							
							// get min of grouped data
							result.add(list.stream()
							.collect(
									Collectors.minBy(
									Comparator.comparingDouble(
											GasData::getPrice)))
									.map(x -> new Pair<>(x.getDate(), x.getPrice()))
									.orElse(new Pair<>("1990",0.0)));
							
							// get max of grouped data
							result.add(list.stream()
							.collect(
									Collectors.maxBy(
									Comparator.comparingDouble(
											GasData::getPrice)))
									.map(x -> new Pair<>(x.getDate(), x.getPrice()))
									.orElse(new Pair<>("1990",0.0)));

							// return result
							return result;
						})));
		
		// print out averages
		minMaxPerYear.entrySet().stream()
				.forEach(x -> System.out.println(
						x.getKey() + ":\n" +
						"Min: $" + 
						x.getValue().get(0).getValue() + " on " +
						x.getValue().get(0).getKey() + "\n" +
						"Max: $" + 
						x.getValue().get(1).getValue() + " on " +
						x.getValue().get(1).getKey() + "\n"));
	}
	
	
	//***************************************************************
	//
	//  Method:       SortByPrice
	// 
	//  Description:  calculates and prints out the highest and lowest gas 
	//				  price per year
	//
	//  Parameters:   None
	//
	//  Returns:      String: sorted list, formated and ready to be output 
	//
	//**************************************************************
	public String SortByPrice(boolean ascending)
	{
		final StringBuilder output = new StringBuilder();
		List<GasData> sortedData;
		
		if (ascending)
		{
			// sort listed data by price in ascending order
			sortedData = records.stream()
					.sorted((Comparator.comparingDouble(GasData::getPrice)))
					.collect(Collectors.toList());
		}
		else
		{
			// sort listed data by price in descending order
			sortedData = records.stream()
					.sorted((Comparator.comparingDouble(GasData::getPrice).reversed()))
					.collect(Collectors.toList());
		}
		
		// append ordered data to output string
		sortedData.stream()
				.forEach(x -> output.append(x.getDate() + ": $" + x.getPrice() + "\n"));
		
		// strip last newline from string
		String outputString = output.toString();
		return outputString.substring(0,outputString.length()-1);
	}
	
}
