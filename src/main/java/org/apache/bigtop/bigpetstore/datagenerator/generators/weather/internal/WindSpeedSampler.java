package org.apache.bigtop.bigpetstore.datagenerator.generators.weather.internal;

import org.apache.bigtop.bigpetstore.datagenerator.framework.SeedFactory;
import org.apache.bigtop.bigpetstore.datagenerator.framework.samplers.ConditionalSampler;
import org.apache.bigtop.bigpetstore.datagenerator.framework.samplers.GammaSampler;
import org.apache.bigtop.bigpetstore.datagenerator.framework.samplers.Sampler;

public class WindSpeedSampler implements ConditionalSampler<WeatherRecordBuilder, WeatherRecordBuilder>
{
	private final double coeffReal;
	private final double coeffImag;
	private final Sampler<Double> gamma;
	
	public WindSpeedSampler(double windSpeedRealCoeff, double windSpeedImagCoeff,
			double windSpeedK, double windSpeedTheta, SeedFactory seedFactory)
	{
		coeffReal = windSpeedRealCoeff;
		coeffImag = windSpeedImagCoeff;
		
		gamma = new GammaSampler(windSpeedK, windSpeedTheta, seedFactory);

	}
	
	private Double windChill(double temp, double windSpeed)
	{
		double v_16 = Math.pow(windSpeed, 0.16);
		double windChill = 35.74 + 0.6215 * temp - 35.74 * v_16 + 0.4275 * temp * v_16;
		
		return windChill;
	}
	
	public WeatherRecordBuilder sample(WeatherRecordBuilder weatherRecord) throws Exception
	{

		double dayOfYear = weatherRecord.getDate().getDayOfYear();
		double windSpeed = Math.max(0.0, coeffReal * Math.cos(-2.0 * Math.PI * dayOfYear / WeatherConstants.TEMPERATURE_PERIOD) +
					coeffImag * Math.sin(-2.0 * Math.PI * dayOfYear / WeatherConstants.TEMPERATURE_PERIOD) + gamma.sample());
		
		double windChill = windChill(weatherRecord.getTemperature(), windSpeed);
		
		weatherRecord.setWindSpeed(windSpeed);
		weatherRecord.setWindChill(windChill);
		
		return weatherRecord;
	}
}
