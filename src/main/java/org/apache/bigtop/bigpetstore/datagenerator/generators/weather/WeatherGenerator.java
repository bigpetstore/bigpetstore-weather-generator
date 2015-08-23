package org.apache.bigtop.bigpetstore.datagenerator.generators.weather;

import java.util.List;

import org.apache.bigtop.bigpetstore.datagenerator.framework.SeedFactory;
import org.apache.bigtop.bigpetstore.datagenerator.framework.samplers.Sampler;
import org.apache.bigtop.bigpetstore.datagenerator.generators.locations.Location;
import org.joda.time.LocalDate;

public class WeatherGenerator implements Sampler<WeatherRecord>
{
	private final Sampler<WeatherRecord> weatherSampler;
	
	public WeatherGenerator(LocalDate startDate, Location location, SeedFactory seedFactory) throws Exception
	{
		List<WeatherStationParameters> parameters = new WeatherParametersReader().readParameters();
		WeatherSamplerBuilder builder = new WeatherSamplerBuilder(
				parameters, location, startDate, seedFactory);
		weatherSampler = builder.build();
	}
	
	public WeatherRecord sample() throws Exception
	{
		return weatherSampler.sample();
	}
}
