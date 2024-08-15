package me.tyalis.dnd.generator.city.model;

import me.tyalis.dnd.generator.city.model.gov.GovernmentType;
import me.tyalis.dnd.generator.city.model.gov.Government;
import me.tyalis.dnd.generator.city.model.pop.ClassLevel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import me.tyalis.dnd.Alignment;
import me.tyalis.dnd.Classes;
import static me.tyalis.dnd.Dices.D20;
import static me.tyalis.dnd.Dices.D100;
import me.tyalis.dnd.Race;
import me.tyalis.dnd.generator.city.model.pop.DicesPerClass;
import me.tyalis.dnd.DicesQuantity;

/**
 *
 * @author Tyalis
 */
public class StateBuilder {
	
	private CityClass cityClass;
	
	private int nbPop;
	private int financeLimit;
	private int liquidity;
	
	private int nbChildren;	// TODO children and teens between 10% to 40%
	private HashMap<Race, Integer> popPerRace;
	
	private int nbSoldiers;
	private int nbMilitia;
	
	private ArrayList<Government> governments;
	
	private HashMap<ClassLevel, Integer> nbClassLevel;
	
	
	public StateBuilder() {
		this.nbClassLevel = new HashMap<>();
		this.popPerRace = new HashMap<>();
		this.governments = new ArrayList<>();
	}
	
	public StateBuilder(CityClass cityClass, int pop) {
		this();
		this.nbPop = pop;
		this.cityClass = cityClass;
		this.financeLimit = this.cityClass.financeLimit;
	}
	
	
	public CityState get() {
		HashMap<Race, Integer> popPerRace = (HashMap<Race, Integer>) this.popPerRace.clone();
		ArrayList<Government> governments = (ArrayList<Government>) this.governments.clone();
		HashMap<ClassLevel, Integer> nbClassLevel = (HashMap<ClassLevel, Integer>) this.nbClassLevel.clone();
		
		// TODO validity check
		
		return new CityState(cityClass, nbPop, financeLimit, liquidity, nbChildren, popPerRace, nbSoldiers, nbMilitia,
				governments, nbClassLevel);
	}
	
	
	public StateBuilder inferChildPop() {
		int percent = 10 + new Random().nextInt(31);
		
		this.inferChildPop(percent);
		
		return this;
	}
	
	public StateBuilder inferChildPop(int percent) {	// XXX write unit tests
		this.nbChildren = (this.nbPop * percent) / 100;
		return this;
	}
	
	public StateBuilder recommandedLiquidity() {
		this.liquidity = this.nbPop * this.financeLimit /2 /10;
		return this;
	}
	
	public StateBuilder recommandedGuards() {
		this.nbSoldiers = this.nbPop / 100;
		return this;
	}
	
	public StateBuilder recommandedMilitia(){
		this.nbMilitia = this.nbPop /20;
		return this;
	}
	
	public StateBuilder addGovernment(Government gov) {
		this.governments.add(gov);
		return this;
	}
	
	public StateBuilder addRandomGovernments() {
		this.addRandomGovernmentsFor(cityClass);
		return this;
	}
	
	public StateBuilder addRandomGovernmentsFor(CityClass cityClass) {
		GovernmentType govType;
		Alignment govAlign;
		
		for (int i = 0; i < cityClass.govMultiplier; i++){
			govType = this.randomGovType(cityClass.govModifier);
			govAlign = this.randomGovAlignment(cityClass.govModifier);
			this.addGovernment(new Government(govType, govAlign));
		}
		
		return this;
	}
	
	public StateBuilder addPnjQtyByClassLevel(ClassLevel cl, int qty) {
		if (this.nbClassLevel.containsKey(cl)) {
			qty += this.nbClassLevel.get(cl);
		}
		
		this.nbClassLevel.put(cl, qty);
		return this;
	}
	
	public StateBuilder addStdPnjQtyByClassLevel() {
		this.addStdPnjQtyByClassLevelFor(cityClass);
		return this;
	}
	
	public StateBuilder addStdPnjQtyByClassLevelFor(CityClass cityClass) {
		Map<Classes, DicesQuantity> dpc = DicesPerClass.getDefaultDicesPerClass();
		DicesQuantity diceQty;
		int level;
		ClassLevel classLvl;
		
		for (Classes classe : dpc.keySet()) {
			for (int i = 0; i < cityClass.communityMultiplier; i++) {
				diceQty = (DicesQuantity) dpc.get(classe);
				level = diceQty.roll(cityClass.communityModifier);
				classLvl = new ClassLevel(classe, level);
				
				this.addPnjQtyByClassLevel(classLvl, 1);
				// TODO track dependent qty
			}
		}
		// TODO propagate keeping total pop in mind
		
		// TODO assign rest of population
		throw new UnsupportedOperationException("Not implemented yet");
	}
	
	
	protected GovernmentType randomGovType(int modif) {
		int roll = D20.roll() + modif;
		GovernmentType govType;
		
		if (roll <= 13) {
			govType = (D100.roll() <= 5)
					? GovernmentType.MONSTROUS
					: GovernmentType.TRADITIONAL;
		} else if (roll <= 18) {
			govType = GovernmentType.INHABITUAL;
		} else {
			govType = GovernmentType.MAGICAL;
		}
		
		return govType;
	}
	
	protected Alignment randomGovAlignment(int modif) {
		int roll = D100.roll() + modif;
		Alignment govAlign;
		
		if (roll <= 35) {
			govAlign = Alignment.LG;
		} else if (roll <= 39) {
			govAlign = Alignment.NG;
		} else if (roll <= 41) {
			govAlign = Alignment.CG;
		} else if (roll <= 61) {
			govAlign = Alignment.LN;
		} else if (roll <= 63) {
			govAlign = Alignment.NN;
		} else if (roll <= 64) {
			govAlign = Alignment.CN;
		} else if (roll <= 90) {
			govAlign = Alignment.LE;
		} else if (roll <= 98) {
			govAlign = Alignment.NE;
		} else {
			govAlign = Alignment.CE;
		}
		
		return govAlign;
	}
	
	
	public CityClass getCityClass() {
		return cityClass;
	}
	public void setCityClass(CityClass cityClass) {
		this.cityClass = cityClass;
	}
	
	public int getNbPop() {
		return nbPop;
	}
	public void setNbPop(int nbPop) {
		this.nbPop = nbPop;
	}
	
	public int getFinanceLimit() {
		return financeLimit;
	}
	public void setFinanceLimit(int financeLimit) {
		this.financeLimit = financeLimit;
	}
	
	public int getLiquidity() {
		return liquidity;
	}
	public void setLiquidity(int liquidity) {
		this.liquidity = liquidity;
	}
	
	public int getNbChildren() {
		return nbChildren;
	}
	public void setNbChildren(int nbChildren) {
		this.nbChildren = nbChildren;
	}
	
	public int getNbSoldiers() {
		return nbSoldiers;
	}
	public void setNbSoldiers(int nbSoldiers) {
		this.nbSoldiers = nbSoldiers;
	}
	
	public int getNbMilitia() {
		return nbMilitia;
	}
	public void setNbMilitia(int nbMilitia) {
		this.nbMilitia = nbMilitia;
	}
	
}
