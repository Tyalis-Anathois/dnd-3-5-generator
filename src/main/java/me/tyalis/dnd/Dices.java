package me.tyalis.dnd;

import java.util.Random;

/**
 *
 * @author Tyalis
 */
public enum Dices {
	D4(4),
	D6(6),
	D8(8),
	D10(10),
	D12(12),
	D20(20),
	D100(100)
	;
	
	public final int nbFaces;
	
	private Dices(int nbFaces) {
		this.nbFaces = nbFaces;
	}
	
	public int roll() {
		return new Random().nextInt(nbFaces)+1;
	}
}
