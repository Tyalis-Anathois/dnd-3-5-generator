package me.tyalis.dnd.generator.city.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import me.tyalis.dnd.Alignment;

/**
 *
 * @author Tyalis
 */
public class CityState implements Serializable {
	
	private CityClass cityClass;
	
	private int nbPop;
	private int financeLimit;
	private int liquidity;
	
	private int nbChildren;	// TODO children and teens between 10% to 40%
	
	private int nbSoldiers;
	private int nbMilitia;
	
	private ArrayList<GovernmentType> govTypes;
	private Alignment govAlign;
	
	private HashMap<String, HashMap<Integer, Integer> > nbClassLevel;
	
}
