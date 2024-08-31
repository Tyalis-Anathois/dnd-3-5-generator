package me.tyalis.dnd.generator.city.model.pop;

import java.util.Arrays;
import java.util.List;
import me.tyalis.dnd.PlayableRace;
import me.tyalis.dnd.Race;
import me.tyalis.dnd.SemiSociableRace;

/**
 *
 * @author Tyalis
 */
public class RacePrevalenceOrder {
	public static final RacePrevalenceOrder STD;
	public static final RacePrevalenceOrder AUBERVIVE_FOREST_COLONY;
	public static final RacePrevalenceOrder AUBERVIVE_INLAND;
	public static final RacePrevalenceOrder AUBERVIVE_MONTAIN_FOOT;
	public static final RacePrevalenceOrder AUBERVIVE_SOUTHERN_FRONTIER;
	
	private final Race[] prevalence;
	
	
	static {
		STD = new RacePrevalenceOrder(new Race[] {
			PlayableRace.HUMAN, PlayableRace.HALFLING, PlayableRace.ELF, PlayableRace.DWARF,
			PlayableRace.GNOME, PlayableRace.HALF_ELF, PlayableRace.HALF_ORC
		});
		
		AUBERVIVE_FOREST_COLONY = new RacePrevalenceOrder(new Race[] {
			PlayableRace.HUMAN, PlayableRace.HALFLING, PlayableRace.HALF_ORC, PlayableRace.DWARF,
			PlayableRace.GNOME
		});
		
		AUBERVIVE_INLAND = new RacePrevalenceOrder(new Race[] {
			PlayableRace.HUMAN, PlayableRace.HALFLING, PlayableRace.HALF_ORC, PlayableRace.DWARF,
			PlayableRace.GNOME, SemiSociableRace.ORC, PlayableRace.HALF_ELF
		});
		
		AUBERVIVE_MONTAIN_FOOT = new RacePrevalenceOrder(new Race[] {
			PlayableRace.HUMAN, PlayableRace.DWARF, PlayableRace.GNOME, PlayableRace.HALFLING,
			PlayableRace.HALF_ORC, PlayableRace.HALF_ELF, SemiSociableRace.ORC
		});
		
		AUBERVIVE_SOUTHERN_FRONTIER = new RacePrevalenceOrder(new Race[] {
			PlayableRace.HUMAN, PlayableRace.HALFLING, PlayableRace.HALF_ELF, PlayableRace.ELF,
			PlayableRace.GNOME, PlayableRace.DWARF, PlayableRace.HALF_ORC
		});
	}
	
	
	public RacePrevalenceOrder(Race[] orderedRacePrevalence) {
		this.prevalence = orderedRacePrevalence;
	}
	
	
	public List<Race> getDescOrderedRacePrevalence() {
		return Arrays.asList(this.prevalence);
	}
}
