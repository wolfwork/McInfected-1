
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


public class KitCommand extends SubCommand {
	
	public KitCommand()
	{
		super("Kit");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) throws CommandException {
		
		if (!(sender instanceof Player))
			sender.sendMessage(getMessanger().getMessage(true, Messages.Error__Command__Not_A_Player));
		
		else if (!sender.hasPermission(getPermission()))
			sender.sendMessage(McInfected.getMessanger().getMessage(true, Messages.Error__Misc__Invalid_Permission));
		
		else if (!McInfected.getLobby().isIPlayer((Player) sender))
			sender.sendMessage(McInfected.getMessanger().getMessage(true, Messages.Error__Game__Not_In_A_Game));
		
		else if (McInfected.getLobby().isStarted())
			sender.sendMessage(McInfected.getMessanger().getMessage(true, Messages.Error__Game__Already_Started));
		
		else
			McInfected.getLobby().getIPlayer((Player) sender).getiMenus().getKitsTeam().open();
		
	}
	
	@Override
	public List<String> getAliases() {
		return Arrays.asList(new String[] { "kits", "kit" });
	}
	
	@Override
	public FancyMessage getFancyMessage() {
		return new FancyMessage(getHelpMessage()).tooltip("Kit", " ", "§eOpen a GUI and choose kits", " ", "§f§l/McInfected Kit").suggest("/McInfected Kit");
	}
	
	@Override
	public String getHelpMessage() {
		return getMessanger().getMessage(false, Messages.Format__Prefix) + "§7/McInfected §oKit";
	}
	
	@Override
	public String getPermission() {
		return "McInfected.Kit";
	}
	
	@Override
	public List<String> getTabs(String[] args) {
		return Arrays.asList(new String[] { "" });
	}
}
