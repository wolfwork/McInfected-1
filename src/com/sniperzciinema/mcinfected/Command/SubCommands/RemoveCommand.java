
package com.sniperzciinema.mcinfected.Command.SubCommands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sniperzciinema.mcinfected.McInfected;
import com.sniperzciinema.mcinfected.Messanger.Messages;
import com.sniperzciinema.mcinfected.Arenas.Arena;
import com.sniperzciinema.mcinfected.Command.SubCommand;
import com.sniperzciinema.mcinfected.Command.FancyMessages.FancyMessage;
import com.sniperzciinema.mcinfected.Utils.StringUtil;


public class RemoveCommand extends SubCommand {
	
	public RemoveCommand()
	{
		super("Remove");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) throws CommandException {
		
		if (!sender.hasPermission(getPermission()))
			sender.sendMessage(McInfected.getMessanger().getMessage(true, Messages.Error__Misc__Invalid_Permission));
		
		else
		{
			
			if (args.length == 2)
			{
				String arenaName = StringUtil.getCapitalized(args[1]);
				if (McInfected.getLobby().isArena(arenaName))
				{
					McInfected.getLobby().removeArena(McInfected.getLobby().getArena(arenaName));
					sender.sendMessage(getMessanger().getMessage(true, Messages.Command__Remove, "<arena>", arenaName));
				}
				else
					sender.sendMessage(getMessanger().getMessage(true, Messages.Error__Arena__Doesnt_Exists));
				
			}
			else
				getFancyMessage().send((Player) sender);
		}
		
	}
	
	@Override
	public List<String> getAliases() {
		return Arrays.asList(new String[] { "r" });
	}
	
	@Override
	public FancyMessage getFancyMessage() {
		return new FancyMessage(getHelpMessage()).tooltip("Remove", " ", "§eRemove an Arena", " ", "§f§l/McInfected Remove <ArenaName>").suggest("/McInfected Remove");
	}
	
	@Override
	public String getHelpMessage() {
		return getMessanger().getMessage(false, Messages.Format__Prefix) + "§7/McInfected §oRemove <Arena>";
	}
	
	@Override
	public String getPermission() {
		return "McInfected.Setup.Remove";
	}
	
	@Override
	public List<String> getTabs(String[] args) {
		
		ArrayList<String> arenas = new ArrayList<String>();
		for (Arena arena : McInfected.getLobby().getArenas())
			arenas.add(arena.getName());
		
		return arenas;
	}
}
