
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
import com.sniperzciinema.mcinfected.IPlayers.IPlayer.Team;
import com.sniperzciinema.mcinfected.Utils.Coord;
import com.sniperzciinema.mcinfected.Utils.StringUtil;


public class SetSpawnCommand extends SubCommand {
	
	public SetSpawnCommand()
	{
		super("SetSpawn");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) throws CommandException {
		
		if (!(sender instanceof Player))
			sender.sendMessage(getMessanger().getMessage(true, Messages.Error__Command__Not_A_Player));
		
		else if (!sender.hasPermission(getPermission()))
			sender.sendMessage(McInfected.getMessanger().getMessage(true, Messages.Error__Misc__Invalid_Permission));
		else if ((args.length == 2) || (args.length == 3))
		{
			Team team = Team.Global;
			if (args.length == 3)
				team = Team.valueOf(StringUtil.getCapitalized(args[2]));
			
			String arenaName = args[1];
			Coord coord = new Coord(((Player) sender).getLocation());
			
			switch (team)
			{
				case Human:
					McInfected.getLobby().getArena(arenaName).addHumanSpawn(coord);
					break;
				
				case Infected:
					McInfected.getLobby().getArena(arenaName).addInfectedSpawn(coord);
					break;
				
				default:
					McInfected.getLobby().getArena(arenaName).addSpawn(coord);
					break;
			}
			sender.sendMessage(getMessanger().getMessage(true, Messages.Command__SetSpawn, "<team>", team.toString()));
			
		}
		else
			getFancyMessage().send((Player) sender);
	}
	
	@Override
	public List<String> getAliases() {
		return Arrays.asList(new String[] { "ss" });
	}
	
	@Override
	public FancyMessage getFancyMessage() {
		return new FancyMessage(getHelpMessage()).tooltip("SetSpawn", " ", "§eSet an Arenas spawns", "Set the possible spawns", "Set spawns per team, by saying what team", "§f§l/McInfected SetSpawn <Arena> [Human/Infected]").suggest("/McInfected SetSpawn");
	}
	
	@Override
	public String getHelpMessage() {
		return getMessanger().getMessage(false, Messages.Format__Prefix) + "§7/McInfected §oSetSpawn <Arena>";
	}
	
	@Override
	public String getPermission() {
		return "McInfected.Setup.SetSpawn";
	}
	
	@Override
	public List<String> getTabs(String[] args) {
		if (args.length == 1)
		{
			
			ArrayList<String> arenas = new ArrayList<String>();
			for (Arena arena : McInfected.getLobby().getArenas())
				arenas.add(arena.getName());
			
			return arenas;
		}
		else if (args.length == 2)
			return Arrays.asList(new String[] { " ", "Human", "Infected" });
		else
			return Arrays.asList(new String[] { "" });
	}
}
