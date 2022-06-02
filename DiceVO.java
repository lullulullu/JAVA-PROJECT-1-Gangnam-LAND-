package com.score6;

import java.io.Serializable;

public class DiceVO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String name;
	int seed;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public int getSeed() {
		return seed;
	}
//	public DiceVO getSeed() {
//		return vo;
//	}
	public void setSeed(int seed) {
		this.seed = seed;

	}
	public void setSeed() {
		this.seed = seed;
		
	}

	@Override
	public String toString() {

			return String.format(name+", ภÜพื :"+seed+"ฟ๘\n");
			  
		
		
	}	
	

}