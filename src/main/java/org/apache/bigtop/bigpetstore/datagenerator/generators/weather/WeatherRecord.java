package org.apache.bigtop.bigpetstore.datagenerator.generators.weather;

import org.joda.time.LocalDate;

public interface WeatherRecord
{

	public abstract LocalDate getDate();

	public abstract double getTemperature();

	public abstract double getPrecipitation();

	public abstract double getWindSpeed();

	public abstract double getWindChill();

	public abstract double getRainFall();

	public abstract double getSnowFall();

}