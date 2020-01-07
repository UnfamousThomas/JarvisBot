package com.discordapp.JarvisBot.punishments.objects;

public enum Degree {
	DEGREE1("Degree I"),
	DEGREE2("Degree II"),
	DEGREE3("Degree III"),
	DEGREE4 ("Degree IV"),
	DEGREE5("Degree V"),
	WARNING("Warning");

	String name;

	public String getName() {
		return this.name;
	}
	Degree(String name){
		this.name = name;
	}
}
