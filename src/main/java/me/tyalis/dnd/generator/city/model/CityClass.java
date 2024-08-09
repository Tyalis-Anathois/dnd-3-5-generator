package me.tyalis.dnd.generator.city.model;

/**
 *
 * @author Tyalis
 */
public enum CityClass {
	
	LIEU_DIT(20, 80, 40, "Lieu-dit"),
	HAMEAU(81, 400, 100, "Hameau"),
	VILLAGE(401, 900, 200, "Village"),
	BOURG(901, 2000, 800, "Boug (petite ville)"),
	VILLE(2001, 5000, 3000, "Ville"),
	GRANDE_VILLE(5001, 12000, 15000, "Grande Ville"),
	CITE(12001, 25000, 40000, "Cité"),
	METROPOLE(25001, Integer.MAX_VALUE, 100000, "METROPOLE")
	;
	
	
	public final int popMin;
	public final int popMax;
	public final int financeLimit;
	public final String name;
	
	
	public static CityClass random100() {
		// TODO implement random 100 selection of city class (1 to 100)
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	
	private CityClass (int popMin, int popMax, int financeLimit, String name) {
		this.popMin = popMin;
		this.popMax = popMax;
		this.financeLimit = financeLimit;
		this.name = name;
	}
	
}
