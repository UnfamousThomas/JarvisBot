package com.discordapp.JarvisBot.utils;

import com.discordapp.JarvisBot.commands.fun.dailyfact.Animal;
import com.discordapp.JarvisBot.utils.mysql.MySQLManager;

public class LoadingMethods {

	public void loadDadJokes() {
		MySQLManager.select("SELECT * from dad_jokes", resultSet -> {
			while (resultSet.next()) {
				//TODO: CREDIT FIELD?
				String text = resultSet.getString("text");
				DataFields.dadJokesList.add(text);
			}
		});
	}

	public void loadNormalJokes() {
		MySQLManager.select("SELECT * from normal_jokes", resultSet -> {
			while (resultSet.next()) {
				//TODO: CREDIT FIELD?
				String text = resultSet.getString("text");
				DataFields.normalJokesList.add(text);
			}
		});
	}

	public void loadAnimals() {
		MySQLManager.select("SELECT * from daily_facts WHERE animal='SHEEP' AND date=CURRENT_DATE", resultSet -> {
			if (resultSet.next()) {
				DataFields.factsStringMap.put(Animal.SHEEP, resultSet.getString("fact"));
			}
		});
		MySQLManager.select("SELECT * from daily_facts WHERE animal='SQUID' AND date=CURRENT_DATE", resultSet -> {
			if (resultSet.next()) {
				DataFields.factsStringMap.put(Animal.SQUID, resultSet.getString("fact"));
			}
		});
		MySQLManager.select("SELECT * from daily_facts WHERE animal='CHICKEN' AND date=CURRENT_DATE", resultSet -> {
			if (resultSet.next()) {
				DataFields.factsStringMap.put(Animal.CHICKEN, resultSet.getString("fact"));
			}
		});
	}

	public void loadBlacklists() {
		MySQLManager.select("SELECT * FROM blacklisted_users", resultSet -> {
			while (resultSet.next()) {
				Long id = resultSet.getLong("userid");
				DataFields.blacklistedPeopleList.put(id, resultSet.getString("reason"));
			}
		});
	}
}
