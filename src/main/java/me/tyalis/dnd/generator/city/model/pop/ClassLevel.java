package me.tyalis.dnd.generator.city.model.pop;

import java.io.Serializable;
import java.util.Objects;
import me.tyalis.dnd.Classes;

/**
 *
 * @author Tyalis
 */
public class ClassLevel implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;
	
	
	private final Classes classe;
	private final int level;
	
	
	public ClassLevel(Classes classe, int level) {
		this.classe = classe;
		this.level = level;
	}
	
	
	public Classes getClasse() {
		return classe;
	}
	
	public int getLevel() {
		return level;
	}
	
	
	@Override
	public int hashCode() {
		int hash = 7;
		hash = 41 * hash + Objects.hashCode(this.classe);
		hash = 41 * hash + this.level;
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
		final ClassLevel other = (ClassLevel) obj;
		if (this.level != other.level) {
			return false;
		}
		return Objects.equals(this.classe, other.classe);
	}
	
	@Override
	public String toString() {
		return "ClassLevel{" + "classe=" + classe + ", level=" + level + '}';
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return new ClassLevel(classe, level);
	}
	
}
