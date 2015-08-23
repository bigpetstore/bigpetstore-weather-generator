package org.apache.bigtop.bigpetstore.datagenerator.generators.weather.internal;

import org.apache.bigtop.bigpetstore.datagenerator.framework.samplers.ConditionalSampler;
import org.apache.bigtop.bigpetstore.datagenerator.framework.samplers.Sampler;
import org.apache.bigtop.bigpetstore.datagenerator.generators.weather.WeatherRecord;
import org.joda.time.LocalDate;

public class WeatherSampler implements Sampler<WeatherRecord>
{
	private final ConditionalSampler<WeatherRecordBuilder, WeatherRecordBuilder> tempSampler;
	private final ConditionalSampler<WeatherRecordBuilder, WeatherRecordBuilder> windSpeedSampler;
	private final ConditionalSampler<WeatherRecordBuilder, WeatherRecordBuilder> precipitationSampler;
	private LocalDate date;
	
	public WeatherSampler(LocalDate startDate,
			ConditionalSampler<WeatherRecordBuilder, WeatherRecordBuilder> tempSampler,
			ConditionalSampler<WeatherRecordBuilder, WeatherRecordBuilder> windSpeedSampler,
			ConditionalSampler<WeatherRecordBuilder, WeatherRecordBuilder> precipitationSampler)
	{
		this.tempSampler = tempSampler;
		this.windSpeedSampler = windSpeedSampler;
		this.precipitationSampler = precipitationSampler;
		date = startDate;
	}
	
	public WeatherRecord sample() throws Exception
	{
		WeatherRecordBuilder weatherRecord = new WeatherRecordBuilder(date);
		
		weatherRecord = tempSampler.sample(weatherRecord);
		weatherRecord = windSpeedSampler.sample(weatherRecord);
		weatherRecord = precipitationSampler.sample(weatherRecord);
		
		date = date.plusDays(WeatherConstants.WEATHER_TIMESTEP);
		
		return weatherRecord.build();
	}
}
