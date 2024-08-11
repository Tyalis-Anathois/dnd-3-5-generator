package me.tyalis.dnd.generator.city.model;

import me.tyalis.dnd.Alignment;

/**
 *
 * @author Tyalis
 */
public class Government {
	
	private GovernmentType type;
	private Alignment alignment;
	
	
	public Government(GovernmentType type, Alignment alignment) {
		this.type = type;
		this.alignment = alignment;
	}
	
	
	public GovernmentType getType() {
		return type;
	}
	
	public void setType(GovernmentType type) {
		this.type = type;
	}
	
	public Alignment getAlignment() {
		return alignment;
	}
	
	public void setAlignment(Alignment alignment) {
		this.alignment = alignment;
	}
	
}
