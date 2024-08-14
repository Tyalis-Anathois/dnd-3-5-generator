package me.tyalis.dnd.generator.city.model;

import java.io.Serializable;
import java.util.Objects;
import me.tyalis.dnd.Alignment;

/**
 *
 * @author Tyalis
 */
public class Government implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	
	private final GovernmentType type;
	private final Alignment alignment;
	
	
	public Government(GovernmentType type, Alignment alignment) {
		this.type = type;
		this.alignment = alignment;
	}
	
	
	public GovernmentType getType() {
		return type;
	}
	
	public Alignment getAlignment() {
		return alignment;
	}
	
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return new Government(type, alignment);
	}
	
	@Override
	public String toString() {
		return "Government{" + "type=" + type + ", alignment=" + alignment + '}';
	}
	
	@Override
	public int hashCode() {
		int hash = 5;
		hash = 19 * hash + Objects.hashCode(this.type);
		hash = 19 * hash + Objects.hashCode(this.alignment);
		return hash;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Government other = (Government) obj;
		if (this.type != other.type) {
			return false;
		}
		return this.alignment == other.alignment;
	}
	
}
