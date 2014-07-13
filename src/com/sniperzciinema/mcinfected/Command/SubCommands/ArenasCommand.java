
package com.sniperzciinema.mcinfected.Command.SubCommands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;

import com.sniperzciinema.mcinfected.McInfected;
import com.sniperzciinema.mcinfected.Messanger.Messages;
import com.sniperzciinema.mcinfected.Arenas.Arena;
import com.sniperzciinema.mcinfected.Command.SubCommand;
import com.sniperzciinema.mcinfected.Command.FancyMessages.FancyMessage;


public class ArenasCommand extends SubCommand {
	
	public ArenasCommand()
	{
		super("Arenas");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) throws CommandException {
		if (!sender.hasPermission(getPermission()))
			sender.sendMessage(McInfected.getMessanger().getMessage(true, Messages.Error__Misc__Invalid_Permission));
		
		else
		{
			StringBuilder valid = new StringBuilder();
			ArrayList<Arena> validArenas = McInfected.getLobby().getValidArenas();
			for (Arena a : validArenas)
			{
				valid.append(a.getName());
				if ((validArenas.size() > 1) && (validArenas.get(validArenas.size() - 1) != a))
					valid.append(", ");
			}
			
			StringBuilder inValid = new StringBuilder();
			ArrayList<Arena> invalidArenas = McInfected.getLobby().getInValidArenas();
			for (Arena a : invalidArenas)
			{
				inValid.append(a.getName());
				if ((invalidArenas.size() > 1) && (invalidArenas.get(invalidArenas.size() - 1) != a))
					inValid.append(", ");
			}
			
			sender.sendMessage(McInfected.getMessanger().getMessage(false, Messages.Format__Header, "<title>", "McInfected Arenas"));
			if (!McInfected.getLobby().getValidArenas().isEmpty())
				sender.sendMessage("" + ChatColor.WHITE + ChatColor.BOLD + "Valid: " + ChatColor.GREEN + valid.toString());
			if (!McInfected.getLobby().getInValidArenas().isEmpty())
				sender.sendMessage("" + ChatColor.WHITE + ChatColor.BOLD + "Not Valid: " + ChatColor.RED + inValid.toString());
		}
		
	}
	
	@Override
	public List<String> getAliases() {
		return Arrays.asList(new String[] { "maps" });
	}
	
	@Override
	public FancyMessage getFancyMessage() {
		return new FancyMessage(getHelpMessage()).tooltip("Arenas", " ", "§eList all Arenas", " ", "§f§l/McInfected Arenas").suggest("/McInfected Arenas");
	}
	
	@Override
	public String getHelpMessage() {
		return getMessanger().getMessage(false, Messages.Format__Prefix) + "§7/McInfected §oArenas";
	}
	
	@Override
	public String getPermission() {
		return "McInfected.Arenas";
	}
	
	@Override
	public List<String> getTabs(String[] args) {
		return Arrays.asList(new String[] { "" });
	}
}
