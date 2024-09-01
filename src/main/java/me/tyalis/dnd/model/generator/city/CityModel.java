package me.tyalis.dnd.model.generator.city;

/**
 *
 * @author Tyalis
 */
public interface CityModel {
	
	public CityState getCityState();
	
	public void addCityStateListener(CityView view);
	
	public void removeCityStateListener(CityView view);
	
	public int getListeners();
	
	// TODO method that will conduct to new state
	
}
