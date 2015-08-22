package org.apache.bigtop.bigpetstore.datagenerator.generators.weather;

import org.apache.bigtop.bigpetstore.datagenerator.framework.SeedFactory;
import org.apache.bigtop.bigpetstore.datagenerator.framework.samplers.ConditionalSampler;
import org.apache.bigtop.bigpetstore.datagenerator.framework.samplers.GammaSampler;
import org.apache.bigtop.bigpetstore.datagenerator.framework.samplers.Sampler;
import org.joda.time.LocalDate;

public class WindSpeedSampler implements ConditionalSampler<Double, LocalDate>
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
	
	public Double sample(LocalDate endDate) throws Exception
	{

		double dayOfYear = endDate.getDayOfYear();
		double windSpeed = Math.max(0.0, coeffReal * Math.cos(-2.0 * Math.PI * dayOfYear / WeatherConstants.TEMPERATURE_PERIOD) +
					coeffImag * Math.sin(-2.0 * Math.PI * dayOfYear / WeatherConstants.TEMPERATURE_PERIOD) + gamma.sample());
		
		return windSpeed;
	}
}
