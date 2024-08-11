package me.tyalis.dnd.generator.city.model;

import java.util.Random;
import static me.tyalis.dnd.Dices.D100;

/**
 *
 * @author Tyalis
 */
public class DefaultCityFactory implements CityFactory {
	
	@Override
	public CityModel createCityOfClass(CityClass cityClass) {
		int nbPop = (cityClass == CityClass.METROPOLE)
				? this.generateMetropolePop()
				: this.generateGenericPop(cityClass.popMin, cityClass.popMax);
		
		return this.createCity(cityClass, nbPop);
	}
	
	@Override
	public CityModel createCityOfPop(int nbPop) {
		CityClass cityClass = CityClass.smallest();
		
		while (cityClass.comparePopToCityClass(nbPop) == 1 && cityClass != CityClass.biggest()) {
			cityClass = cityClass.next();
		}
		
		return this.createCity(cityClass, nbPop);
	}
	
	@Override
	public CityModel createCityOfRandom100() {
		int random100 = D100.roll();
		CityClass cityClass;
		
		if (random100 == 100) {
			cityClass = CityClass.METROPOLE;
		} else if (random100 >= 96) {
			cityClass = CityClass.CITE;
		} else if (random100 >= 86) {
			cityClass = CityClass.GRANDE_VILLE;
		} else if (random100 >= 71) {
			cityClass = CityClass.VILLE;
		} else if (random100 >= 51) {
			cityClass = CityClass.BOURG;
		} else if (random100 >= 31) {
			cityClass = CityClass.VILLAGE;
		} else if (random100 >= 11) {
			cityClass = CityClass.HAMEAU;
		} else {
			cityClass = CityClass.LIEU_DIT;
		}
		
		return this.createCityOfClass(cityClass);
	}
	
	
	protected int generateGenericPop(int popMin, int popMax) {	// XXX unit test with edge case Integer.MAX
		int variance = popMax - popMin +1;
		return new Random().nextInt(variance) + popMin;
	}
	
	protected int generateMetropolePop() {
		int max = CityClass.METROPOLE.popMin * 10;
		boolean growMore = D100.roll() == 100;
		
		while(this.maxCanGrow(max) && growMore) {
			max *= 10;
			
			growMore = D100.roll() == 100;
		}
		
		if (growMore) {
			max = CityClass.METROPOLE.popMax;
		}
		
		return this.generateGenericPop(CityClass.METROPOLE.popMin, max);
	}
	
	protected boolean maxCanGrow(int max) {
		return max < (CityClass.METROPOLE.popMax / 10);
	}
	
	protected CityModel createCity(CityClass cityClass, int nbPop) {
		StateBuilder stateBuilder = new StateBuilder(cityClass, nbPop)
				.inferChildPop()
				.recommandedLiquidity()
				.recommandedGuards()
				.recommandedMilitia()
				// TODO generate government (type + alignment)
				// TODO generate classes pop
				// TODO pick capitain
				;
		
		throw new UnsupportedOperationException("Not supported yet.");
	}
	
}
