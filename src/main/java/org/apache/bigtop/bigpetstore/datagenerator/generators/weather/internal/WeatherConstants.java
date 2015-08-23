package org.apache.bigtop.bigpetstore.datagenerator.generators.weather.internal;

import java.io.File;

public class WeatherConstants
{	
	public static final File WEATHER_PARAMETERS_FILE = new File("weather_parameters.csv");
	
	public static final double TEMPERATURE_GAMMA = 0.5; // 2 / day
	public static final int WEATHER_TIMESTEP = 1; // days
	public static final double TEMPERATURE_PERIOD = 365.0; // days
	
	public static final double PRECIPITATION_A = 0.2;
	public static final double PRECIPITATION_B = 27.0;
	public static final double PRECIPITATION_TO_SNOWFALL = 10.0;
	
	public static final double WIND_CHILL_PROBABILITY_A = 0.8;
	public static final double WIND_CHILL_PROBABILITY_B = 0.5;
	public static final double WIND_CHILL_PROBABILITY_C = 10.0; // F
	public static final double WIND_CHILL_PROBABILITY_D = 0.2;
	
	public static final double WIND_SPEED_PROBABILITY_A = -0.5;
	public static final double WIND_SPEED_PROBABILITY_B = 0.8;
	public static final double WIND_SPEED_PROBABILITY_C = 17.5; // mph
	public static final double WIND_SPEED_PROBABILITY_D = 1.0;
	
	public static final double SNOWFALL_PROBABILITY_A = -0.8;
	public static final double SNOWFALL_PROBABILITY_B = 10.0; 
	public static final double SNOWFALL_PROBABILITY_C = 0.75; // in
	public static final double SNOWFALL_PROBABILITY_D = 1.0;
	
	public static final double RAINFALL_PROBABILITY_A = -0.6;
	public static final double RAINFALL_PROBABILITY_B = 7.5;
	public static final double RAINFALL_PROBABILITY_C = 0.75; // in
	public static final double RAINFALL_PROBABILITY_D = 1.0;
}
