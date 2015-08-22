package org.apache.bigtop.bigpetstore.datagenerator.generators.weather.generators.weather;

import org.apache.bigtop.bigpetstore.datagenerator.framework.samplers.ConditionalSampler;
import org.apache.commons.lang3.tuple.Pair;

public class WindChillConditionalSampler implements ConditionalSampler<Double, Pair<Double, Double>>
{
	public Double sample(Pair<Double, Double> conditional)
	{
		double temp = conditional.getLeft();
		double windSpeed = conditional.getRight();
		
		double v_16 = Math.pow(windSpeed, 0.16);
		double windChill = 35.74 + 0.6215 * temp - 35.74 * v_16 + 0.4275 * temp * v_16;
		
		return windChill;
	}
}
