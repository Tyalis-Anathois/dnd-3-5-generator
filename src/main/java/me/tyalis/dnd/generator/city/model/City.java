package me.tyalis.dnd.generator.city.model;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Tyalis
 */
public class City implements CityModel {
	
	protected CityState city;
	
	private final List<CityView> views;
	
	
	public City() {
		this(new StateBuilder().get());
	}
	
	public City(CityState state) {
		this.views = new CopyOnWriteArrayList<>();
		this.city = state;
	}
	
	
	@Override
	public CityState getCityState() {
		return this.city;
	}
	
	@Override
	public void addCityStateListener(CityView view) {
		this.views.add(view);
		this.fire();
	}
	
	@Override
	public void removeCityStateListener(CityView view) {
		this.views.remove(view);
		this.fire();
	}
	
	@Override
	public int getListeners() {
		return this.views.size();
	}
	
	
	private void fire() {
		Iterator<CityView> viewIt = this.views.iterator();
		
		while(viewIt.hasNext()) {
			viewIt.next().notifyChange();
		}
	}
	
}
