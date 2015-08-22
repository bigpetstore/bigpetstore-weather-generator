package org.apache.bigtop.bigpetstore.datagenerator.generators.weather.cli;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.List;
import java.util.Random;

import org.apache.bigtop.bigpetstore.datagenerator.framework.SeedFactory;
import org.apache.bigtop.bigpetstore.datagenerator.generators.locations.Location;
import org.apache.bigtop.bigpetstore.datagenerator.generators.locations.LocationReader;
import org.apache.bigtop.bigpetstore.datagenerator.generators.weather.WeatherGenerator;
import org.apache.bigtop.bigpetstore.datagenerator.generators.weather.datamodels.Weather;
import org.apache.bigtop.bigpetstore.datagenerator.generators.weather.datamodels.inputs.WeatherStationParameters;
import org.apache.bigtop.bigpetstore.datagenerator.generators.weather.datareaders.WeatherParametersReader;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.google.common.collect.Lists;


public class Driver
{
	List<Location> locations;
	List<WeatherStationParameters> parameters;
	Location location;
	int simulationLength;
	LocalDate startDate;
	SeedFactory seedFactory;
	File outputDir;
	
	static final int NPARAMS = 5;
	
	private void printUsage()
	{
		String usage = "BigPetStore Data Generator\n" +
				"\n" +
				"Usage: java -jar bigpetstore-weather-generator.java outputDir zipcode simulationLength startDate [seed]\n" +
				"\n" + 
				"outputDir - (string) directory to write files\n" +
				"zipcode - (string) location zipcode\n" +
				"simulationLength - (int) number of days to simulate\n" +
				"startDate - (string) simulation start date in YYYY-MM-DD format\n" +
				"seed - (long) seed for RNG. If not given, one is randomly generated.\n";
		
		System.out.println(usage);
	}
	
	private Location findLocation(String zipcode)
	{
		for(Location location: locations)
		{
			if(location.getZipcode().equals(zipcode))
				return location;
		}
		
		return null;
	}
	
	/**
	 * Returns local date from string in YYYY-MM-DD format
	 * @param text
	 * @return
	 */
	private LocalDate parseDate(String text)
	{
		DateTimeFormatter dtFormatter = DateTimeFormat.forPattern("yyyy-MM-dd");
		DateTime dt = dtFormatter.parseDateTime(text);
		
		return dt.toLocalDate();
	}
	
	public void parseArgs(String[] args)
	{
		if(args.length != NPARAMS && args.length != (NPARAMS - 1))
		{
			printUsage();
			System.exit(1);
		}
		
		int i = -1;
		
		outputDir = new File(args[++i]);
		if(! outputDir.exists())
		{
			System.err.println("Given path (" + args[i] + ") does not exist.\n");
			printUsage();
			System.exit(1);
		}
		
		if(! outputDir.isDirectory())
		{
			System.err.println("Given path (" + args[i] + ") is not a directory.\n");
			printUsage();
			System.exit(1);
		}
		
		location = findLocation(args[++i]);
		if(location == null)
		{
			System.err.println("No location found for given zipcode \"" + args[i] + "\"");
			printUsage();
			System.exit(1);
		}
		
		try
		{
			simulationLength = Integer.parseInt(args[++i]);
		}
		catch(Exception e)
		{
			System.err.println("Unable to parse '" + args[i] + "' as an int for simulationLength.\n");
			printUsage();
			System.exit(1);
		}
		
		try
		{
			startDate = parseDate(args[++i]);
		}
		catch(Exception e)
		{
			System.err.println("Unable to parse '" + args[i] + "'. Expected string in 'YYYY-MM-DD' format.\n");
			printUsage();
			System.exit(1);
		}
		
		long seed = (new Random()).nextLong();
		if(args.length == NPARAMS)
		{
			try
			{
				seed = Long.parseLong(args[++i]);
			}
			catch(Exception e)
			{
				System.err.println("Unable to parse '" + args[i] + "' as a long for the seed.\n");
				printUsage();
				System.exit(1);
			}
		}

		seedFactory = new SeedFactory(seed);
	}
	
	private void writeWeather(Location location, List<Weather> trajectory) throws Exception
	{
		File outputFile = new File(outputDir.toString() + File.separator + location.getZipcode() + ".txt");
		Writer output = new BufferedWriter(new FileWriter(outputFile));

		for(Weather weather : trajectory)
		{
			String record = weather.getDate() + ",";
			record += location.getCity() + ",";
			record += location.getState() + ",";
			record += location.getZipcode() + ",";
			record += weather.getTemperature() + ",";
			record += weather.getWindChill() + ",";
			record += weather.getWindSpeed() + ",";
			record += weather.getPrecipitation() + ",";
			record += weather.getRainFall() + ",";
			record += weather.getSnowFall() + "\n";
			
			output.write(record);
		}
		
		output.close();
	}
	
	public void run(String[] args) throws Exception
	{
		locations = new LocationReader().loadData();
		parameters = new WeatherParametersReader().readParameters();
		
		parseArgs(args);
		
		WeatherGenerator generator = new WeatherGenerator(parameters,
				startDate, location, seedFactory);
		
		LocalDate date = startDate;
		LocalDate endDate = startDate.plusDays(simulationLength);
		List<Weather> trajectory = Lists.newArrayList();
		
		while(date.isBefore(endDate))
		{
			Weather weather = generator.sample();
			trajectory.add(weather);
			date = weather.getDate();
		}
		
		writeWeather(location, trajectory);
	}
	
	public static void main(String[] args) throws Exception
	{
		Driver driver = new Driver();
		driver.run(args);		
	}
}
