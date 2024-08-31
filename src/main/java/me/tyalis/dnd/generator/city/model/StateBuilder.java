package me.tyalis.dnd.generator.city.model;

import me.tyalis.dnd.generator.city.model.gov.GovernmentType;
import me.tyalis.dnd.generator.city.model.gov.Government;
import me.tyalis.dnd.generator.city.model.pop.ClassLevel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.stream.Collectors;
import me.tyalis.dnd.Alignment;
import me.tyalis.dnd.Classes;
import static me.tyalis.dnd.Dices.D20;
import static me.tyalis.dnd.Dices.D100;
import me.tyalis.dnd.Race;
import me.tyalis.dnd.generator.city.model.pop.DicesPerClass;
import me.tyalis.dnd.DicesQuantity;
import me.tyalis.dnd.NpcClasses;
import me.tyalis.dnd.PcClasses;
import me.tyalis.dnd.generator.city.model.pop.RaceDistributionFactory;
import me.tyalis.dnd.generator.city.model.pop.RaceDistributionType;
import me.tyalis.dnd.generator.city.model.pop.RacePrevalenceOrder;
import me.tyalis.dnd.tables.PercentTable;

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
	private ClassLevel captain;
	
	
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
				governments, nbClassLevel, captain);
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
		this.addStdPnjQtyByClassLevelFor(cityClass, nbPop);
		return this;
	}
	
	public StateBuilder addStdPnjQtyByClassLevelFor(CityClass cityClass, int nbPop) {
		int estimNbDerived = this.generateMaxLevelClassAndEstimateDerived();
		
		// Propagate keeping total pop in mind
		if (nbPop > (this.getCurrentAssignedPopCount() + estimNbDerived) ) {
			this.propagateDerivedDefault();
		} else {
			this.propagateDerivedNotEnoughPop(nbPop);
		}
		
		// Assign rest of population
		int restToAssign = nbPop - this.getCurrentAssignedPopCount();
		this.distributeUnassignedToNpcClassByPercent(restToAssign);
		
		return this;
	}
	
	public StateBuilder pickCaptainByStdRoll() {
		int roll = D100.roll();
		
		boolean hasWarrior = this.populationHasClass(NpcClasses.WARRIOR);
		boolean hasFighter = this.populationHasClass(PcClasses.FIGHTER);
		
		if (hasWarrior && !hasFighter) {
			this.captain = this.getClassLevelByRank(NpcClasses.WARRIOR, 1);
		} else if (hasFighter) {
			while (!hasWarrior && roll < 61) {
				roll = D100.roll();
			}
			
			this.captain = roll < 61 ? this.getClassLevelByRank(NpcClasses.WARRIOR, 1)
					: roll < 81 ? this.getClassLevelByRank(PcClasses.FIGHTER, 2)
					: this.getClassLevelByRank(PcClasses.FIGHTER, 1);
		}
		
		return this;
	}
	
	public StateBuilder distributeRaces() {
		PercentTable<Race>.IntegerResult<Race> distrib = new RaceDistributionFactory().getRaceDistributionStrict(
				RaceDistributionType.MIXED, 
				RacePrevalenceOrder.STD, 
				nbPop);
		
		this.setDistributeRaces(distrib);
		
		return this;
	}
	
	public StateBuilder distributeRaceAubInland() {
		PercentTable<Race>.IntegerResult<Race> distrib = new RaceDistributionFactory().getRaceDistributionStrict(
				RaceDistributionType.MIXED, 
				RacePrevalenceOrder.AUBERVIVE_INLAND, 
				nbPop);
		
		this.setDistributeRaces(distrib);
		
		return this;
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
	
	protected int generateMaxLevelClassAndEstimateDerived () {
		Map<Classes, DicesQuantity> dpc = DicesPerClass.getDefaultDicesPerClass();
		boolean isNpcClass;
		DicesQuantity diceQty;
		int level;
		int stopBeforeLevel;
		ClassLevel classLvl;
		int estimNbDerived = 0;
		
		for (Classes classe : dpc.keySet()) {
			for (int i = 0; i < cityClass.communityMultiplier; i++) {
				diceQty = (DicesQuantity) dpc.get(classe);
				level = diceQty.roll(cityClass.communityModifier);
				isNpcClass = this.isNpcClass(classe);
				stopBeforeLevel = (isNpcClass) ? 1 : 0;
				
				if (level > stopBeforeLevel) {
					classLvl = new ClassLevel(classe, level);
					this.addPnjQtyByClassLevel(classLvl, 1);
					estimNbDerived += this.estimDerived(classe, level);
				}
			}
		}
		
		return estimNbDerived;
	}
	
	protected int estimDerived(Classes classe, int level) {	// TODO unit test
		boolean isNpcClass = this.isNpcClass(classe);
		int stopAtLevel = (isNpcClass) ? 2 : 1;
		int nbAdd = 2;
		level /= 2;
		int tot = 0;
		
		while(level > stopAtLevel) {
			tot += nbAdd;
			level /= 2;
			nbAdd *= 2;
		}
		
		return tot;
	}
	
	protected void propagateDerivedDefault() {
		Set<ClassLevel> classLvls = this.nbClassLevel.keySet();
		
		for (ClassLevel classLvl : classLvls) {
			this.propagateDerivedDefaultFor(classLvl.getClasse(), classLvl.getLevel(), this.nbClassLevel.get(classLvl));
		}
	}
	
	protected void propagateDerivedDefaultFor(Classes classe, int level, int qty) {
		boolean isNpcClass = this.isNpcClass(classe);
		int stopAtLevel = (isNpcClass) ? 2 : 1;
		ClassLevel classLvl;
		int nbAdd = qty * 2;
		level /= 2;
		
		while(level > stopAtLevel) {
			classLvl = new ClassLevel(classe, level);
			this.addPnjQtyByClassLevel(classLvl, nbAdd);
			level /= 2;
			nbAdd *= 2;
		}
	}
	
	protected void propagateDerivedNotEnoughPop(int nbPop) {
		Set<ClassLevel> keys = this.nbClassLevel.keySet();
		int countPop = keys.size();	// at start, only 1 for top of each class, except for big cities in which case this method is not used.
		Queue<ClassLevel> queue = new ArrayBlockingQueue<>(countPop * 2, false, keys);
		int jailBreak = nbPop;
		boolean isNpcClass;
		int stopAtLevel;
		ClassLevel classLvl;
		Classes classe;
		int level;
		ClassLevel newClassLvl;
		int nbAdd;
		
		do {
			classLvl = queue.remove();
			classe = classLvl.getClasse();
			level = classLvl.getLevel() / 2;
			nbAdd = this.nbClassLevel.get(classLvl) * 2;
			
			isNpcClass = this.isNpcClass(classe);
			stopAtLevel = (isNpcClass) ? 2 : 1;
			
			nbAdd = ( (countPop + nbAdd) > nbPop) ? nbPop - countPop : nbAdd;
			
			if (level > stopAtLevel) {
				newClassLvl = new ClassLevel(classe, level);
				this.addPnjQtyByClassLevel(newClassLvl, nbAdd);
				queue.add(newClassLvl);
				countPop += nbAdd;
			}
			
			jailBreak--;
			
		} while (nbPop > countPop && jailBreak > 0 && !queue.isEmpty());
	}
	
	protected void distributeUnassignedToNpcClassByPercent(int toAssign) {
		// Calculate raw floating values
		HashMap<ClassLevel, Double> ratioPerClass = new HashMap<>();
		ratioPerClass.put(new ClassLevel(NpcClasses.COMMONER, 1), toAssign * 0.91);
		ratioPerClass.put(new ClassLevel(NpcClasses.WARRIOR, 1), toAssign * 0.05);
		ratioPerClass.put(new ClassLevel(NpcClasses.EXPERT, 1), toAssign * 0.03);
		ratioPerClass.put(new ClassLevel(NpcClasses.ADEPT, 1), toAssign * 0.005);
		ratioPerClass.put(new ClassLevel(NpcClasses.ARISTOCRAT, 1), toAssign * 0.005);
		
		// Floor them and (by) convert into integer
		HashMap<ClassLevel, Integer> numberPerClassLevel = new HashMap<>();
		for (ClassLevel classLvl : ratioPerClass.keySet()) {
			numberPerClassLevel.put(classLvl, (int) ratioPerClass.get(classLvl).doubleValue());
		}
		
		int roundError = toAssign - this.getAssignedPopCountFor(numberPerClassLevel);
		
		if (roundError < 0) {
			// Should not happen : rounding with int cast should always lower the round value, not augment it,
			// so we should never be over the total number to add
			throw new StateBuildingException("Impossible rounding during assignation by percentage of population to NPC classes");
			
		} else if (roundError > 0) {
			// If there is round error, we have the exact number, so we can correct by adding 1 by order of highest decimals until fixed
			HashMap<ClassLevel, Double> restPerClass = (HashMap<ClassLevel, Double>) ratioPerClass.clone();
			restPerClass.entrySet().stream().forEach(entry -> entry.setValue(entry.getValue() % 1) );
			List<Entry<ClassLevel, Double> > entries = restPerClass.entrySet().stream()
					.sorted((o1, o2) -> (o1.getValue().equals(o2.getValue()) ? 0 : o1.getValue() > o2.getValue() ? 1 : 0) )
					.collect(Collectors.toList());
			
			Entry entry;
			for (int i = 0; i < roundError; i++) {
				entry = entries.get(i);
				numberPerClassLevel.put( (ClassLevel) entry.getKey(), 
						1 + (int) ((Double) entry.getValue()).doubleValue() );
			}
		}
		
		this.nbClassLevel.putAll(numberPerClassLevel);
	}
	
	protected int getCurrentAssignedPopCount() {
		return this.getAssignedPopCountFor(this.nbClassLevel);
	}
	
	protected int getAssignedPopCountFor(HashMap<ClassLevel, Integer> nbByClassLevel) {
		int popTot = 0;
		
		for (ClassLevel classLvl : nbByClassLevel.keySet()) {
			popTot += nbByClassLevel.get(classLvl);
		}
		
		return popTot;
	}
	
	protected boolean isNpcClass(Classes classe) {
		return classe instanceof NpcClasses;
	}
	
	protected boolean populationHasClass(Classes classe) {
		return this.hasClass(nbClassLevel, classe);
	}
	
	protected boolean hasClass(HashMap<ClassLevel, Integer> assignedPop, Classes classe) {
		long count = assignedPop.keySet().stream()
				.filter(classLvl -> classLvl.getClasse().equals(classe))
				.count();
		
		return count > 0;
	}
	
	protected ClassLevel getClassLevelByRank(Classes classe, int rank) {
		return this.getClassLevelByRank(nbClassLevel, classe, rank);
	}
	
	protected ClassLevel getClassLevelByRank(HashMap<ClassLevel, Integer> assignedPop, Classes classe, int rank) {
		List<ClassLevel> classLvlsDesc = assignedPop.keySet().stream()
				.filter(classLvl -> classLvl.getClasse().equals(classe))
				.sorted((o1, o2) -> o1.getLevel() - o2.getLevel())
				.collect(Collectors.toList());
		
		return classLvlsDesc.get(rank -1 );
	}
	
	protected void setDistributeRaces(PercentTable<Race>.IntegerResult<Race> distrib) {
		distrib.getValues().stream()
				.forEach(race -> this.popPerRace.put(race, distrib.getResultFor(race)));
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
	
	public ClassLevel getCaptain() {
		return captain;
	}
	public void setCaptain(ClassLevel captain) {
		this.captain = captain;
	}
	
}
