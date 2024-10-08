package me.tyalis.dnd.model;

import java.io.Serializable;

/**
 *
 * @author Tyalis
 */
public enum Alignment implements Serializable {
	LG("LB","Loyal Bon"),		NG("NB","Neutre Bon"),		CG("CB","Chaotique Bon"),
	LN("LN","Loyal Neutre"),		NN("NN","Neutre Neutre"),	CN("CN","Chaotique Neutre"),
	LE("LM","Loyal Mauvais"),	NE("NM","Neutre Mauvais"),	CE("CM","Chaotique Mauvais");
	
	public final String initials;
	public final String name;
	
	private Alignment(String initials, String name) {
		this.initials = initials;
		this.name = name;
	}
}
