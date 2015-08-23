package org.apache.bigtop.bigpetstore.datagenerator.generators.weather;

import org.apache.bigtop.bigpetstore.datagenerator.framework.SeedFactory;
import org.apache.bigtop.bigpetstore.datagenerator.framework.samplers.ConditionalSampler;
import org.apache.bigtop.bigpetstore.datagenerator.framework.samplers.GaussianSampler;
import org.apache.bigtop.bigpetstore.datagenerator.framework.samplers.Sampler;
import org.joda.time.LocalDate;

public class TemperatureSampler implements ConditionalSampler<WeatherRecordBuilder, WeatherRecordBuilder>
{
	final private Sampler<Double> R;
	final private double average;
	final private double coeffReal;
	final private double coeffImag;
	
	private LocalDate date;
	private double noise;
	
	public TemperatureSampler(LocalDate startDate, double tempAverage, double tempRealCoeff, double tempImagCoeff,
			double tempSigma, SeedFactory seedFactory)
	{
		R = new GaussianSampler(0.0, tempSigma, seedFactory);
		
		this.average = tempAverage;
		this.coeffReal = tempRealCoeff;
		this.coeffImag = tempImagCoeff;

		date = startDate;
		noise = 0.0;
	}
	
	public WeatherRecordBuilder sample(WeatherRecordBuilder weatherRecord) throws Exception
	{
		double temp = 0.0;
		while(date.isBefore(weatherRecord.getDate()))
		{
			double dayOfYear = date.getDayOfYear();
			temp = average + coeffReal * Math.cos(-2.0 * Math.PI * dayOfYear / WeatherConstants.TEMPERATURE_PERIOD) +
				coeffImag * Math.sin(-2.0 * Math.PI * dayOfYear / WeatherConstants.TEMPERATURE_PERIOD) + noise;
		
			noise += -1.0 * noise * WeatherConstants.TEMPERATURE_GAMMA * WeatherConstants.WEATHER_TIMESTEP + 
				Math.sqrt(WeatherConstants.WEATHER_TIMESTEP) * R.sample();
		
			date = date.plusDays(WeatherConstants.WEATHER_TIMESTEP);
		}
		
		weatherRecord.setTemperature(temp);
		
		return weatherRecord;
	}
}
