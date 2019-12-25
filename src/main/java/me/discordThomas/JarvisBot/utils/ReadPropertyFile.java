package me.discordThomas.JarvisBot.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ReadPropertyFile {
	Map<String, String> result = new HashMap<>();
	InputStream inputStream;

	public Map<String, String> getPropValues() throws IOException {

		try {
			Properties prop = new Properties();
			String propFileName = "config.properties";

			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}

			// get the property value and print it out
			String key = prop.getProperty("discord.key");
			String shards = prop.getProperty("discord.initialshards");
			String prefix = prop.getProperty("discord.prefix");

			result.put("key", key);
			result.put("shards", shards);
			result.put("prefix", prefix);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			inputStream.close();
		}
		return result;
	}
}
