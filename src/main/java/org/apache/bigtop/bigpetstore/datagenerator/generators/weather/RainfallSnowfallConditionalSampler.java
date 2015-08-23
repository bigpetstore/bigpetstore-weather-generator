package org.apache.bigtop.bigpetstore.datagenerator.generators.weather;

import org.apache.bigtop.bigpetstore.datagenerator.framework.samplers.ConditionalSampler;
import org.apache.commons.lang3.tuple.Pair;

public class RainfallSnowfallConditionalSampler implements
		ConditionalSampler<Pair<Double, Double>, Pair<Double, Double>>
{
	public Pair<Double, Double> sample(Pair<Double, Double> conditional)
	{
		double temp = conditional.getLeft();
		double precipitation = conditional.getRight();
		
		double fractionRain = 1.0 / (1.0 + Math.exp(- WeatherConstants.PRECIPITATION_A * (temp - WeatherConstants.PRECIPITATION_B)));
		
		double rainfall = fractionRain * precipitation;
		double snowfall = WeatherConstants.PRECIPITATION_TO_SNOWFALL * (1.0 - fractionRain) * precipitation;
		
		return Pair.of(rainfall, snowfall);
	}
}
