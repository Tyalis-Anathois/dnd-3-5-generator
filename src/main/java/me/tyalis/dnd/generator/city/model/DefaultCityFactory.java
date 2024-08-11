package me.tyalis.dnd.generator.city.model;

import static me.tyalis.dnd.Dices.D100;

/**
 *
 * @author Tyalis
 */
public class DefaultCityFactory implements CityFactory {
	
	@Override
	public CityModel createCityOfClass(CityClass cityClass) {
		throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
	}
	
	@Override
	public CityModel createCityOfPop(int nbPop) {
		CityClass cityClass = CityClass.smallest();
		
		while (cityClass.comparePopToCityClass(nbPop) == 1 && cityClass != CityClass.biggest()) {
			cityClass = cityClass.next();
		}
		
		throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
	
}
