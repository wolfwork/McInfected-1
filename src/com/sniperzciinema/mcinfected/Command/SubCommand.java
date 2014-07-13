
package com.sniperzciinema.mcinfected.Command;

import java.util.List;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

import com.sniperzciinema.mcinfected.McInfected;
import com.sniperzciinema.mcinfected.Messanger;
import com.sniperzciinema.mcinfected.Command.FancyMessages.FancyMessage;


public abstract class SubCommand {
	
	private String	name;
	
	public SubCommand(String name)
	{
		this.name = name;
	}
	
	public abstract void execute(CommandSender sender, String[] args) throws CommandException;
	
	public abstract List<String> getAliases();
	
	public abstract FancyMessage getFancyMessage();
	
	public abstract String getHelpMessage();
	
	public Messanger getMessanger() {
		return McInfected.getMessanger();
	}
	
	public String getName() {
		return this.name;
	}
	
	public abstract String getPermission();
	
	public abstract List<String> getTabs(String[] args);
	
	public final boolean hasPermission(CommandSender sender) {
		
		return sender.hasPermission(getPermission());
	}
	
}
