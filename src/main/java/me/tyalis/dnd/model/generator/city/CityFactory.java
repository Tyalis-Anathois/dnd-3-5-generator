package me.tyalis.dnd.model.generator.city;

/**
 *
 * @author Tyalis
 */
public interface CityFactory {
	
	public CityModel createCityOfClass(CityClass cityClass);
	public CityModel createCityOfPop(int nbPop);
	public CityModel createCityOfRandom100();
	
}
