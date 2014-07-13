
package com.sniperzciinema.mcinfected.Command.SubCommands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sniperzciinema.mcinfected.McInfected;
import com.sniperzciinema.mcinfected.Messanger.Messages;
import com.sniperzciinema.mcinfected.Command.SubCommand;
import com.sniperzciinema.mcinfected.Command.FancyMessages.FancyMessage;
import com.sniperzciinema.mcinfected.Utils.Coord;


public class SetLobbyCommand extends SubCommand {
	
	public SetLobbyCommand()
	{
		super("SetLobby");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) throws CommandException {
		
		if (!(sender instanceof Player))
			sender.sendMessage(getMessanger().getMessage(true, Messages.Error__Command__Not_A_Player));
		
		else if (!sender.hasPermission(getPermission()))
			sender.sendMessage(McInfected.getMessanger().getMessage(true, Messages.Error__Misc__Invalid_Permission));
		else
		{
			McInfected.getLobby().setLocation(new Coord(((Player) sender).getLocation()));
			sender.sendMessage(getMessanger().getMessage(true, Messages.Command__SetLobby));
		}
	}
	
	@Override
	public List<String> getAliases() {
		return Arrays.asList(new String[] { "" });
	}
	
	@Override
	public FancyMessage getFancyMessage() {
		return new FancyMessage(getHelpMessage()).tooltip("SetLobby", " ", "§eSet the lobby's position to your current spot", " ", "§f§l/McInfected SetLobby").suggest("/McInfected SetLobby");
	}
	
	@Override
	public String getHelpMessage() {
		return getMessanger().getMessage(false, Messages.Format__Prefix) + "§7/McInfected §oSetLobby";
	}
	
	@Override
	public String getPermission() {
		return "McInfected.Setup.SetLobby";
	}
	
	@Override
	public List<String> getTabs(String[] args) {
		return Arrays.asList(new String[] { "" });
	}
}
