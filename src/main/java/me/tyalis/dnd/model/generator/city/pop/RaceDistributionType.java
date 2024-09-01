package me.tyalis.dnd.model.generator.city.pop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Tyalis
 */
public class RaceDistributionType {
	public static final RaceDistributionType ISOLATED = new RaceDistributionType(new double[]{ 96, 2, 1, 1 });
	public static final RaceDistributionType MIXED = new RaceDistributionType(new double[]{ 79, 9, 5, 3, 2, 1, 1 });
	public static final RaceDistributionType INTEGRATED = new RaceDistributionType(new double[]{ 37, 20, 18, 7, 5, 3 });
	
	private double[] distribution;
	
	
	public RaceDistributionType(double[] values) {
		
		if ( ! this.isValid(values)) {
			throw new IllegalArgumentException("A distribution must have a total of precisely 100%.");
		}
		
		this.distribution = values.clone();
	}
	
	
	public List<Double> getDistribution() {
		List<Double> distrib = new ArrayList<>();
		Arrays.stream(this.distribution)
				.forEach(percent -> distrib.add(percent));
		return distrib;
	}
	
	
	private double sum(double[] values) {
		return Arrays.stream(values).reduce(0d, Double::sum);
	}
	
	private boolean isValid(double[] values) {
		return this.sum(values) == 100d;
	}
}
