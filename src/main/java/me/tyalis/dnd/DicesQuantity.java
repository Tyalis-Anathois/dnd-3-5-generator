package me.tyalis.dnd;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Tyalis
 */
public class DicesQuantity implements Rollable {
	
	private Map<Dices, Integer> diceQuantity;
	
	
	public static DicesQuantity of(Dices dice, int quantity) {
		return new DicesQuantity(dice, quantity);
	}
	
	
	public DicesQuantity() {
		this.diceQuantity = new HashMap<>();
	}
	
	public DicesQuantity(int nbDiffDices) {
		this.diceQuantity = new HashMap<>(nbDiffDices);
	}
	
	public DicesQuantity(Dices dice, int quantity) {
		this();
		this.addDiceQuantity(dice, quantity);
	}
	
	
	public void addDiceQuantity(Dices dice, int quantity) {
		if (this.diceQuantity.containsKey(dice)) {
			quantity += this.diceQuantity.get(dice);
		}
		
		if (quantity < 0) quantity = 0;
		
		this.diceQuantity.put(dice, quantity);
	}
	
	public int roll() {
		return this.roll(0, 0, 0);
	}
	
	public int roll(int modifierOnTotal) {
		return this.roll(modifierOnTotal, 0, 0);
	}
	
	public int roll(int modifierOnTotal, int modifierPerRoll, int modifierPerDiceType) {
		Dices dice;
		int result = modifierOnTotal;
		
		for (Map.Entry entry : this.diceQuantity.entrySet()) {
			for (int i = 0; i < (int) entry.getValue(); i++) {
				dice = (Dices) entry.getKey();
				result += dice.roll() + modifierPerRoll;
			}
			
			result += modifierPerDiceType;
		}
		
		return result;
	}
}
