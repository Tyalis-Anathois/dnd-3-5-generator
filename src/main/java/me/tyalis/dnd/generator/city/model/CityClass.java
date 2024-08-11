package me.tyalis.dnd.generator.city.model;

/**
 *
 * @author Tyalis
 */
public enum CityClass {
	
	LIEU_DIT(	20,		80,				40,	"Lieu-dit",				-1,	1, -3, 1),	// TODO 5% +10 druide ou rodeur
	HAMEAU(		81,		400,				100,	"Hameau",				0,	1, -2, 1),	// TODO 5% +10 druide ou rodeur
	VILLAGE(	401,		900,				200,	"Village",				1,	1, -1, 1),
	BOURG(		901,		2000,				800,	"Bourg (petite ville)",	2,	1, 0, 1),
	VILLE(		2001,		5000,				3000,	"Ville",				3,	1, 3, 1),
	GRANDE_VILLE(5001,	12000,			15000,"Grande Ville",			4,	2, 6, 2),
	CITE(		12001,	25000,			40000,"Cité",					5,	3, 9, 3),
	METROPOLE(	25001,	Integer.MAX_VALUE,100000,"Métropole",			6,	4, 12, 4)
	;
	
	
	private static final CityClass[] vals = values();
	
	
	public final int popMin;
	public final int popMax;
	public final int financeLimit;
	public final String name;
	public final int govModifier;
	public final int govMultiplier;
	public final int communityModifier;
	public final int communityMultiplier;
	
	
	private CityClass (int popMin, int popMax, int financeLimit, String name,
			int govModifier, int govMultiplier, int communityModifier, int communityMultiplier) {
		this.popMin = popMin;
		this.popMax = popMax;
		this.financeLimit = financeLimit;
		this.name = name;
		this.govModifier = govModifier;
		this.govMultiplier = govMultiplier;
		this.communityModifier = communityModifier;
		this.communityMultiplier = communityMultiplier;
	}
	
	
	public static CityClass smallest() {
		return vals[0];
	}
	
	public static CityClass biggest() {
		return vals[vals.length-1];
	}
	
	
	public int comparePopToCityClass(int pop) {
		if (pop < this.popMin) {
			return -1;
		} else if (pop > this.popMax) {
			return 1;
		} else {
			return 0;
		}
	}
	
	public CityClass next() {
		return vals[(this.ordinal() +1) % vals.length];
	}
	
	public CityClass previous() {
		return vals[(vals.length + this.ordinal() -1) %vals.length];
	}
	
}
