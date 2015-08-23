package org.apache.bigtop.bigpetstore.datagenerator.generators.weather;

import java.util.Collection;

import org.apache.bigtop.bigpetstore.datagenerator.framework.SeedFactory;
import org.apache.bigtop.bigpetstore.datagenerator.framework.samplers.ConditionalSampler;
import org.apache.bigtop.bigpetstore.datagenerator.framework.samplers.Sampler;
import org.apache.bigtop.bigpetstore.datagenerator.generators.locations.Location;
import org.joda.time.LocalDate;

public class WeatherSamplerBuilder
{
	
	private final WeatherStationParameters parameters;
	private final SeedFactory seedFactory;
	private final LocalDate startDate;
	
	public WeatherSamplerBuilder(Collection<WeatherStationParameters> weatherParameters,
			Location location, LocalDate startDate, SeedFactory seedFactory)
	{
		parameters = findClosest(weatherParameters, location);
		this.seedFactory = seedFactory;
		this.startDate = startDate;
	}
	
	private WeatherStationParameters findClosest(Collection<WeatherStationParameters> weatherParameters,
			Location location)
	{
		WeatherStationParameters closestStation = null;
		double minDist = Double.MAX_VALUE;
		
		for(WeatherStationParameters parameters : weatherParameters)
		{
			double dist = location.distance(parameters.getCoordinates());
			if (dist < minDist)
			{
				minDist = dist;
				closestStation = parameters;
			}
		}
		
		return closestStation;
	}

	private ConditionalSampler<WeatherRecordBuilder, WeatherRecordBuilder> buildTempSampler()
	{
		return new TemperatureSampler(startDate, parameters.getTemperatureAverage(),
				parameters.getTemperatureRealCoeff(), parameters.getTemperatureImagCoeff(),
				parameters.getTemperatureDerivStd(), seedFactory);
	}
	
	private ConditionalSampler<WeatherRecordBuilder, WeatherRecordBuilder> buildWindSpeedSampler()
	{
		return new WindSpeedSampler(parameters.getWindSpeedRealCoeff(),
				parameters.getWindSpeedImagCoeff(),
				parameters.getWindSpeedK(),
				parameters.getWindSpeedTheta(), seedFactory);
	}
	
	private ConditionalSampler<WeatherRecordBuilder, WeatherRecordBuilder> buildPrecipitationSampler()
	{
		return new PrecipitationSampler(parameters.getPrecipitationAverage(), seedFactory);
	}
	
	public Sampler<WeatherRecord> build()
	{
		return new WeatherSampler(startDate, buildTempSampler(),
				buildWindSpeedSampler(), buildPrecipitationSampler());
	}
}
