package org.apache.bigtop.bigpetstore.datagenerator.generators.weather.generators.weather;

import org.apache.bigtop.bigpetstore.datagenerator.framework.samplers.ConditionalSampler;
import org.apache.bigtop.bigpetstore.datagenerator.framework.samplers.Sampler;
import org.apache.bigtop.bigpetstore.datagenerator.generators.weather.WeatherConstants;
import org.apache.bigtop.bigpetstore.datagenerator.generators.weather.datamodels.Weather;
import org.apache.commons.lang3.tuple.Pair;
import org.joda.time.LocalDate;

public class WeatherSampler implements Sampler<Weather>
{
	private final ConditionalSampler<Double, LocalDate> tempSampler;
	private final Sampler<Double> precipSampler;
	private final ConditionalSampler<Double, LocalDate> windSpeedSampler;
	private final ConditionalSampler<Double, Pair<Double, Double>> windChillSampler;
	private final ConditionalSampler<Pair<Double, Double>, Pair<Double, Double>> rainfallSnowfallSampler;
	private LocalDate date;
	
	public WeatherSampler(LocalDate startDate,
			ConditionalSampler<Double, LocalDate> tempSampler,
			Sampler<Double> precipSampler,
			ConditionalSampler<Double, LocalDate> windSpeedSampler,
			ConditionalSampler<Double, Pair<Double, Double>> windChillSampler,
			ConditionalSampler<Pair<Double, Double>, Pair<Double, Double>> rainfallSnowfallSampler)
	{
		this.tempSampler = tempSampler;
		this.precipSampler = precipSampler;
		this.windSpeedSampler = windSpeedSampler;
		this.windChillSampler = windChillSampler;
		this.rainfallSnowfallSampler = rainfallSnowfallSampler;
		date = startDate;
	}
	
	public Weather sample() throws Exception
	{
		double temp = tempSampler.sample(date);
		double precip = precipSampler.sample();
		double windSpeed = windSpeedSampler.sample(date);
		
		double windChill = windChillSampler.sample(Pair.of(temp, windSpeed));
		Pair<Double, Double> rainfallSnowfall = rainfallSnowfallSampler.sample(Pair.of(temp, precip));
		
		Weather weather = new Weather(date, temp, precip, windSpeed, windChill, rainfallSnowfall.getLeft(),
				rainfallSnowfall.getRight());
		
		date = date.plusDays(WeatherConstants.WEATHER_TIMESTEP);
		
		return weather;
	}
}
