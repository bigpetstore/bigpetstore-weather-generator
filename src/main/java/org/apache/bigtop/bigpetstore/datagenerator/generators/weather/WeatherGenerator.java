package org.apache.bigtop.bigpetstore.datagenerator.generators.weather;

import java.util.List;

import org.apache.bigtop.bigpetstore.datagenerator.framework.SeedFactory;
import org.apache.bigtop.bigpetstore.datagenerator.framework.samplers.Sampler;
import org.apache.bigtop.bigpetstore.datagenerator.generators.locations.Location;
import org.apache.bigtop.bigpetstore.datagenerator.generators.weather.datamodels.Weather;
import org.apache.bigtop.bigpetstore.datagenerator.generators.weather.datamodels.inputs.WeatherStationParameters;
import org.apache.bigtop.bigpetstore.datagenerator.generators.weather.generators.weather.WeatherSamplerBuilder;
import org.joda.time.LocalDate;

public class WeatherGenerator implements Sampler<Weather>
{
	private final Sampler<Weather> weatherSampler;
	
	public WeatherGenerator(List<WeatherStationParameters> parameters, LocalDate startDate, 
			Location location, SeedFactory seedFactory)
	{
		WeatherSamplerBuilder builder = new WeatherSamplerBuilder(
				parameters, location, startDate, seedFactory);
		weatherSampler = builder.build();
	}
	
	public Weather sample() throws Exception
	{
		return weatherSampler.sample();
	}
}
