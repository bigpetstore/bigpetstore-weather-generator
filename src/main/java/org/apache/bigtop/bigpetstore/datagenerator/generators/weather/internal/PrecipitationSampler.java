package org.apache.bigtop.bigpetstore.datagenerator.generators.weather.internal;

import org.apache.bigtop.bigpetstore.datagenerator.framework.SeedFactory;
import org.apache.bigtop.bigpetstore.datagenerator.framework.samplers.ConditionalSampler;
import org.apache.bigtop.bigpetstore.datagenerator.framework.samplers.ExponentialSampler;
import org.apache.bigtop.bigpetstore.datagenerator.framework.samplers.Sampler;

public class PrecipitationSampler implements ConditionalSampler<WeatherRecordBuilder, WeatherRecordBuilder>
{
	private final Sampler<Double> precipitationSampler;
	
	public PrecipitationSampler(double averagePrecipitation, SeedFactory seedFactory)
	{
		precipitationSampler = new ExponentialSampler(1.0 / averagePrecipitation, seedFactory);
	}
	
	public WeatherRecordBuilder sample(WeatherRecordBuilder record) throws Exception
	{
		double temp = record.getTemperature();
		double precipitation = precipitationSampler.sample();
		record.setPrecipitation(precipitation);
		
		double fractionRain = 1.0 / (1.0 + Math.exp(- WeatherConstants.PRECIPITATION_A * (temp - WeatherConstants.PRECIPITATION_B)));
		
		double rainfall = fractionRain * precipitation;
		double snowfall = WeatherConstants.PRECIPITATION_TO_SNOWFALL * (1.0 - fractionRain) * precipitation;
		record.setRainFall(rainfall);
		record.setSnowFall(snowfall);
		
		return record;
	}
}
