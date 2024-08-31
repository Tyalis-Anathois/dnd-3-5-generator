package me.tyalis.dnd.generator.city.model.pop;

import java.util.Iterator;
import me.tyalis.dnd.Race;
import me.tyalis.dnd.tables.PercentTable;

/**
 *
 * @author Tyalis
 */
public class RaceDistributionFactory {
	
	/**
	 * @param type
	 * @param order
	 * @param pop
	 * @return
	 * @throws IllegalArgumentException if there is not enough race in the order to use all the distribution (nb distrib &lte; nb race.
	 */
	public PercentTable<Race>.IntegerResult<Race> getRaceDistributionStrict(RaceDistributionType type, RacePrevalenceOrder order, int pop) {
		if (type.getDistribution().size() < order.getDescOrderedRacePrevalence().size()) {
			throw new IllegalArgumentException("Not enough races to cover distribution");
		}
		
		PercentTable<Race> table = new PercentTable<>();
		Iterator<Double> typeIt = type.getDistribution().iterator();
		Iterator<Race> raceIt = order.getDescOrderedRacePrevalence().iterator();
		
		while(typeIt.hasNext()) {
			table.set(raceIt.next(), typeIt.next());
		}
		
		return table.apply(pop);
	}
	
	// XXX getRaceDistributionConstrained => size of both type and race must be strictly equals, not type gte race.
	
	// XXX getRaceDistributionLax with strategy as an input(internal public enum) and an overload without strategy specified but use a default one
	
}
