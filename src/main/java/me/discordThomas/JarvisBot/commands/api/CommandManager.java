package me.discordThomas.JarvisBot.commands.api;

import com.google.common.collect.Maps;
import me.discordThomas.JarvisBot.utils.Logger;
import me.discordThomas.JarvisBot.utils.ReadPropertyFile;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;
import java.util.*;

public class CommandManager extends ListenerAdapter {
	public Map<Long, Long> factsMap = new HashMap<>();
	public Map<Long, Long> unicodeMap = new HashMap<>();

	public Map<String, Command> commands = Maps.newHashMap();
	public static CommandManager instance;
	private static String prefix;
	public static HelpBuilder helpBuilder;

	public static void registerCommand(Command command) {
		Logger.log(Logger.Level.INFO, "Attempting to register discord command: " + command.name);
		instance.commands.put(command.name.toLowerCase(), command);

		for(String alias : command.aliases)
			instance.commands.put(alias.toLowerCase(), command);

		helpBuilder.addCommand(command);
		Logger.log(Logger.Level.SUCCESS, "Registered discord command: " + command.name);
	}

	public static void registerCommands(Command... commands) {
		for(Command command: commands)
			registerCommand(command);
	}

	public static void init(JDABuilder builder) throws IOException {
		CommandManager manager = new CommandManager();
		instance = manager;
		builder.addEventListeners(manager);

		ReadPropertyFile properties = new ReadPropertyFile();
		prefix = properties.getPropValues().get("prefix");

		helpBuilder = new HelpBuilder();

	}

	public void onMessageReceived(MessageReceivedEvent event) {
		if(prefix == null) prefix = ".";

		String[] argArray = event.getMessage().getContentRaw().split(" ");
		if(!event.getAuthor().isBot()) {
			if (argArray[0].startsWith(prefix)) {
				String commandStr = argArray[0].substring(1);

				List<String> argsList = new ArrayList<>(Arrays.asList(argArray));
				argsList.remove(0);

				if (commands.containsKey(commandStr.toLowerCase())) {
					commands.get(commandStr.toLowerCase()).execute(event, argsList);
				}

			}
		}}
}
