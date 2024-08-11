package me.tyalis.dnd.generator.city.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import me.tyalis.dnd.Classes;
import me.tyalis.dnd.Race;

/**
 *
 * @author Tyalis
 */
public class CityState implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
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
	
	
	CityState(CityClass cityClass, int nbPop, int financeLimit, int liquidity, int nbChildren,
			HashMap<Race, Integer> popPerRace, int nbSoldiers, int nbMilitia, ArrayList<Government> government,
			HashMap<Classes, HashMap<Integer, Integer> > nbClassLevel) {
		
		this.cityClass = cityClass;
		this.nbPop = nbPop;
		this.financeLimit = financeLimit;
		this.liquidity = liquidity;
		this.nbChildren = nbChildren;
		this.popPerRace = popPerRace;
		this.nbSoldiers = nbSoldiers;
		this.nbMilitia = nbMilitia;
		this.government = government;
		this.nbClassLevel = nbClassLevel;
	}
	
	
	public CityClass getCityClass() {
		return cityClass;
	}
	
	public int getNbPop() {
		return nbPop;
	}
	
	public int getFinanceLimit() {
		return financeLimit;
	}
	
	public int getLiquidity() {
		return liquidity;
	}
	
	public int getNbChildren() {
		return nbChildren;
	}
	
	public HashMap<Race, Integer> getPopPerRace() {
		return (HashMap<Race, Integer>) popPerRace.clone();
	}
	
	public int getNbSoldiers() {
		return nbSoldiers;
	}
	
	public int getNbMilitia() {
		return nbMilitia;
	}
	
	public ArrayList<Government> getGovernment() {
		return (ArrayList<Government>) government.clone();
	}
	
	public HashMap<Classes, HashMap<Integer, Integer>> getNbClassLevel() {
		return (HashMap<Classes, HashMap<Integer, Integer>>) nbClassLevel.clone();	// TODO return a deep clone, not a shallow one
	}
	
}
