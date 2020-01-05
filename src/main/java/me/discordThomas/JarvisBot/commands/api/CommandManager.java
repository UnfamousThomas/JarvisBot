package me.discordThomas.JarvisBot.commands.api;

import com.google.common.collect.Maps;
import me.discordThomas.JarvisBot.utils.DataFields;
import me.discordThomas.JarvisBot.utils.Logger;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class CommandManager extends ListenerAdapter {

	public static CommandManager instance;
	public static HelpBuilder helpBuilder;
	private static String prefix;
	public Map<String, Command> commands = Maps.newHashMap();

	public static void registerCommand(Command command) {
		Logger.log(Logger.Level.INFO, "Attempting to register discord command: " + command.name);
		instance.commands.put(command.name.toLowerCase(), command);

		for (String alias : command.aliases)
			instance.commands.put(alias.toLowerCase(), command);

		helpBuilder.addCommand(command);
		Logger.log(Logger.Level.SUCCESS, "Registered discord command: " + command.name);
	}

	public static void registerCommands(Command... commands) {
		for (Command command : commands)
			registerCommand(command);
	}

	public static void init(JDABuilder builder) {
		CommandManager manager = new CommandManager();
		instance = manager;
		builder.addEventListeners(manager);

		prefix = DataFields.prefix;

		helpBuilder = new HelpBuilder();


	}

	public void onMessageReceived(MessageReceivedEvent event) {
		if (DataFields.blacklistedPeopleList.get(event.getAuthor().getIdLong()) != null) return;
		if (prefix == null) prefix = ".";

		String[] argArray = event.getMessage().getContentRaw().split("\\s");
		if (!event.getAuthor().isBot()) {
			if (argArray[0].startsWith(prefix)) {
				String commandStr = argArray[0].substring(1);

				List<String> argsList = new ArrayList<>(Arrays.asList(argArray));
				argsList.remove(0);

				if (commands.containsKey(commandStr.toLowerCase())) {
					commands.get(commandStr.toLowerCase()).execute(event, argsList);
				}

			}
		}
	}

}
