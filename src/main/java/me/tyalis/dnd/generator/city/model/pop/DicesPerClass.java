package me.tyalis.dnd.generator.city.model.pop;

import me.tyalis.dnd.DicesQuantity;
import java.util.HashMap;
import java.util.Map;
import me.tyalis.dnd.Classes;
import me.tyalis.dnd.Dices;
import me.tyalis.dnd.NpcClasses;
import me.tyalis.dnd.PcClasses;

/**
 *
 * @author Tyalis
 */
public class DicesPerClass {
	
	public static Map<Classes, DicesQuantity> getDefaultDicesPerClass() {
		Map<Classes, DicesQuantity> dpc = new HashMap<>(16);
		
		dpc.put(NpcClasses.ADEPT, DicesQuantity.of(Dices.D6, 1));
		dpc.put(NpcClasses.ARISTOCRAT, DicesQuantity.of(Dices.D4, 1));
		dpc.put(NpcClasses.COMMONER, DicesQuantity.of(Dices.D4, 4));
		dpc.put(NpcClasses.EXPERT, DicesQuantity.of(Dices.D4, 3));
		dpc.put(NpcClasses.WARRIOR, DicesQuantity.of(Dices.D4, 2));
		
		dpc.put(PcClasses.BARBARIAN, DicesQuantity.of(Dices.D4, 1));
		dpc.put(PcClasses.BARD, DicesQuantity.of(Dices.D6, 1));
		dpc.put(PcClasses.DRUID, DicesQuantity.of(Dices.D6, 1));
		dpc.put(PcClasses.SORCERER, DicesQuantity.of(Dices.D4, 1));
		dpc.put(PcClasses.FIGHTER, DicesQuantity.of(Dices.D8, 1));
		dpc.put(PcClasses.WIZARD, DicesQuantity.of(Dices.D4, 1));
		dpc.put(PcClasses.MONK, DicesQuantity.of(Dices.D4, 1));
		dpc.put(PcClasses.PALADIN, DicesQuantity.of(Dices.D3, 1));
		dpc.put(PcClasses.CLERIC, DicesQuantity.of(Dices.D6, 1));
		dpc.put(PcClasses.RANGER, DicesQuantity.of(Dices.D3, 1));
		dpc.put(PcClasses.ROGUE, DicesQuantity.of(Dices.D8, 1));
		
		return dpc;
	}
	
}
