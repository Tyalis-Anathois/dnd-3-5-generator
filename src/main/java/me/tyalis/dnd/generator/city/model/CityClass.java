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
	
	
	public final int popMin;
	public final int popMax;
	public final int financeLimit;
	public final String name;
	public final int govModifier;
	public final int govMultiplier;
	public final int communityModifier;
	public final int communityMultiplier;
	
	
	public static CityClass random100() {
		// TODO implement random 100 selection of city class (1 to 100)
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	
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
	
}
