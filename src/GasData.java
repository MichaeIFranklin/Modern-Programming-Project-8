//******************************************************************************
//
//  Developer:     Michael Franklin
//
//  Project #:     Project 8
//
//  File Name:     GasData.java
//
//  Course:        COSC 4301 - Modern Programming
//
//  Due Date:      04/20/2022
//
//  Instructor:    Fred Kumi 
//
//  Description:   simple data structure class that holds data fields of a gas 
//				   record
//
//
//******************************************************************************

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class GasData {
	private double price;
	private int year;
	private int month;
	private int day;
	private String date;
	
	// accessors
	public String getDate()
	{
		return date;
	}
	public double getPrice()
	{
		return price;
	}
	public int getYear()
	{
		return year;
	}
	public int getMonth()
	{
		return month;
	}
	public int getDay()
	{
		return day;
	}
	
	// parser
	public void parseRecord(String line)
	{
		// check for non empty strings
		if (line.isEmpty())
		{
			// use default values
			year = 1990;
			month = 1;
			day = 1;
			date = "01-01-1990";
			price = 0;
		}
		else
		{		
			// set date values
			this.date = line.substring(0,line.indexOf(":"));
			month = Integer.parseInt(date.substring(0,2));
			day = Integer.parseInt(date.substring(3,5));
			year = Integer.parseInt(date.substring(6));
			
			// set price
			price = Double.parseDouble(line.substring(line.indexOf(":")+1));
		}
	}
	
}
