package me.tyalis.dnd.tables;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A table where a set of value is attributed a percentage which is applied to a number.
 * 
 * @param <V> The type of the values that will be managed.
 * 
 * @author Tyalis
 */
public class PercentTable<V> {
	
	protected Map<V,Double> table;
	
	
	public PercentTable() {
		this.table = new HashMap<>();
	}
	
	public PercentTable(PercentTable<? extends V> pt) {
		this.table = new HashMap<>((Map<V,Double>) pt.table);
	}
	
	
	public int size() {
		return this.table.size();
	}
	
	public boolean contains(V v) {
		return this.table.containsKey(v);
	}
	
	public double get(V v) {
		return this.table.get(v);
	}
	
	public double remove(V v) {
		return this.table.remove(v);
	}
	
	/**
	 * @param v - The given value.
	 * @param percent - The percent value, exprimed as a decimal number comprised between 0.0 and 100.0.
	 * @throws IllegalArgumentException - when the percentage is not comprised between 0.0 and 100.0.
	 */
	public void set(V v, double percent) {
		this.validateValue(v);
		this.validatePercent(percent);
		
		this.table.put(v, percent);
	}
	
	/**
	 * @param v - The given value.
	 * @param percent - The percent value, exprimed as a decimal number comprised between 0.0 and 100.0.
	 * @throws IllegalArgumentException - when the sum of the current percentage and given percentage is not comprised between 0.0 and 100.0.
	 */
	@SuppressWarnings("null")
	public void add(V v, double percent) {
		this.validateValue(v);
		
		percent += (this.table.containsKey(v)) ? this.table.get(v) : 0d;
		
		this.validatePercent(percent);
		
		this.table.put(v, percent);
	}
	
	public Set<V> getValues() {
		return new HashSet<>(this.table.keySet());
	}
	
	public List<V> getPercentOrderedValues() {
		return this.table.entrySet().stream()
				.sorted((entry1, entry2) -> entry1.getValue().equals(entry2.getValue())
						? 0 : entry1.getValue() > entry2.getValue() ? 1 : -1)
				.map((entry) -> entry.getKey())
				.collect(Collectors.toList());
	}
	
	public double totalAllocated() {
		return this.table.values().stream().reduce(0d, Double::sum);
	}
	
	public boolean isValid() {
		return this.totalAllocated() == 100d;
	}
	
	public boolean isValidPercent(double percent) {
		boolean invalid = percent < 0d || percent > 100d;
		return ! invalid;
	}
	
	
	/**
	 * Applies a valid percent table to a integer that is the number to distribute using the defined percentages.
	 * 
	 * @param nbTot - int - Total on which the % table is applied to.
	 * @return IntegerResult&lt;V&gt; - A integer based result table.
	 * @throws IllegalArgumentException - when a negative number is given.
	 * @throws InvalidTableException - when the total percentage is not equal to 100%.
	 * @throws ArithmeticException - if, somehow, the sum of all applied values rounded down is bigger than the total it was applied to.
	 */
	public IntegerResult<V> apply(int nbTot) {
		IntegerResult<V> resultTable;
		int roundError;
		
		if (nbTot < 0) {
			throw new IllegalArgumentException("The nbTot must not be negative");
		}
		if ( ! this.isValid()) {
			throw new InvalidTableException("The table must have precisely 100% allocated");
		}
		
		resultTable = this.doApplyAndFloor(nbTot);
		roundError = nbTot - resultTable.sumResults();
		
		if (roundError < 0) {
			throw new ArithmeticException("Rounding error : the sum of all parts rounded down cannot be bigger than the total");
			
		} else if (roundError > 0) {
			resultTable = this.fixRoundError(resultTable, roundError);
		}
		
		return resultTable;
	}
	
	
	/**
	 * A integer based resul table that contain the result of a percent table applied to an integer.
	 * 
	 * @param <V> Same type of values than the percent table that produced it.
	 */
	public class IntegerResult<V> {
		
		protected Map<V,Integer> results;
		
		
		private IntegerResult() {
			this.results = new HashMap<>();
		}
		
		
		public int getResultFor(V v) {
			return this.results.get(v);
		}
		
		public Set<V> getValues() {
			return new HashSet<>(this.results.keySet());
		}
		
		
		private void putResult(V v, int result) {
			this.results.put(v, result);
		}
		
		private void increment(V v) {
			@SuppressWarnings("null")
			int current = ( ! this.results.containsKey(v)) ? 0 : this.results.get(v);
			this.results.put(v, ++current);
		}
		
		private int sumResults(){
			return this.results.values().stream()
					.reduce(0, Integer::sum);
		}
	}
	
	
	private IntegerResult<V> doApplyAndFloor(int nbTot) {
		IntegerResult<V> resultTable = new IntegerResult<>();
		int result;
		
		for (V v : this.table.keySet()) {
			result =  (int) (nbTot * this.table.get(v));
			resultTable.putResult(v, result);
		}
		
		return resultTable;
	}
	
	private IntegerResult<V> fixRoundError(IntegerResult<V> resultTable, int nbRoundError) {
		for (V v : this.getValuesByHighestDecimals()) {
			if(nbRoundError <= 0) break;
			
			resultTable.increment(v);
		}
		
		return resultTable;
	}
	
	private List<V> getValuesByHighestDecimals() {
		HashMap<V, Double> shallowCopy = new HashMap<>(this.table);
		List<V> orderedEntries = shallowCopy.entrySet().stream()
			.map(entry -> {
				entry.setValue(entry.getValue() % 1);
				return entry;
			})
			.sorted((entry1, entry2) -> (entry1.getValue().equals(entry2.getValue()) ? 0 : entry1.getValue() > entry2.getValue() ? 1 : 0) )
			.map(entry -> entry.getKey())
			.collect(Collectors.toList())
			;
		
		return orderedEntries;
	}
	
	private void validatePercent(double percent) {
		if (! this.isValidPercent(percent)) {
			throw new IllegalArgumentException("The percent value must be comprised between 0.0 and 100.0 as any percentage");
		}
	}
	
	private void validateValue(V v) {
		if (v == null) {
			throw new IllegalArgumentException("The given value should always be specified, but null was given");
		}
	}
}
