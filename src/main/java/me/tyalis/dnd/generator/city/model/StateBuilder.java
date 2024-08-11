package me.tyalis.dnd.generator.city.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import me.tyalis.dnd.Classes;
import me.tyalis.dnd.Race;

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
	
	private ArrayList<Government> government;
	
	private HashMap<Classes, HashMap<Integer, Integer> > nbClassLevel;
	
	
	public StateBuilder() {
		this.nbClassLevel = new HashMap<>();
		this.popPerRace = new HashMap<>();
		this.government = new ArrayList<>();
	}
	
	public StateBuilder(CityClass cityClass, int pop) {
		this();
		this.nbPop = pop;
		this.cityClass = cityClass;
		this.financeLimit = this.cityClass.financeLimit;
	}
	
	
	public CityState get() {
		HashMap<Race, Integer> popPerRace = (HashMap<Race, Integer>) this.popPerRace.clone();
		// TODO deep clone government list
		ArrayList<Government> government = (ArrayList<Government>) this.government.clone();
		// TODO deep clone class levels map
		HashMap<Classes, HashMap<Integer, Integer> > nbClassLevel = (HashMap<Classes, HashMap<Integer, Integer> >) this.nbClassLevel.clone();
		
		// TODO validity check
		
		return new CityState(cityClass, nbPop, financeLimit, liquidity, nbChildren, popPerRace, nbSoldiers, nbMilitia,
				government, nbClassLevel);
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
