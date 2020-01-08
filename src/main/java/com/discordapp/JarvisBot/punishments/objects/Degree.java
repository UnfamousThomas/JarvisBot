package com.discordapp.JarvisBot.punishments.objects;

public enum Degree {
	DEGREE1("Degree I Punishment"),
	DEGREE2("Degree II Punishment"),
	DEGREE3("Degree III Punishment"),
	DEGREE4 ("Degree IV Punishment"),
	DEGREE5("Degree V Punishment"),
	WARNING("Warning Punishment");

	String name;

	public String getName() {
		return this.name;
	}
	Degree(String name){
		this.name = name;
	}
}
