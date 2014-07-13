
package com.sniperzciinema.mcinfected.Command.SubCommands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sniperzciinema.mcinfected.McInfected;
import com.sniperzciinema.mcinfected.Messanger.Messages;
import com.sniperzciinema.mcinfected.Command.SubCommand;
import com.sniperzciinema.mcinfected.Command.FancyMessages.FancyMessage;


public class HelpCommand extends SubCommand {
	
	private static int	cmdsPerPage	= 6;
	
	public HelpCommand()
	{
		super("Help");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) throws CommandException {
		
		if (!sender.hasPermission(getPermission()))
			sender.sendMessage(McInfected.getMessanger().getMessage(true, Messages.Error__Misc__Invalid_Permission));
		else if (args.length == 2)
		{
			sender.sendMessage(McInfected.getMessanger().getMessage(false, Messages.Format__Header, "<title>", "McInfected Commands " + args[1]));
			int min = (Integer.valueOf(args[1]) - 1) * HelpCommand.cmdsPerPage;
			int max = min + HelpCommand.cmdsPerPage;
			int i = 0;
			for (SubCommand command : McInfected.getCHandler().getSubCommands())
				if (sender.hasPermission(command.getPermission()))
				{
					if ((i >= min) && (i < max))
						if (sender instanceof Player)
							command.getFancyMessage().send((Player) sender);
						else
							sender.sendMessage(command.getHelpMessage());
					
					i++;
				}
			if (sender instanceof Player)
			{
				sender.sendMessage("");
				new FancyMessage("§b§lTip: ").then("§f§o§nHover§f§o over commands for a description").tooltip("Just like that!").send((Player) sender);
				new FancyMessage(McInfected.getMessanger().getMessage(false, Messages.Format__Prefix)).then("§4<< Back").tooltip("Go back a Help Page").command("/McInfected Help " + String.valueOf(Integer.parseInt(args[1]) - 1)).then("             ").then("§6Next >>").tooltip("Go to the next Help Page").command("/McInfected Help " + String.valueOf(Integer.parseInt(args[1]) + 1)).send((Player) sender);
			}
		}
		else
			Bukkit.getServer().dispatchCommand(sender, "McInfected Help 1");
	}
	
	@Override
	public List<String> getAliases() {
		return Arrays.asList(new String[] { "h", "?" });
	}
	
	@Override
	public FancyMessage getFancyMessage() {
		return new FancyMessage(getHelpMessage()).tooltip("Help", " ", "§eView all the commands", " ", "§f§l/McInfected Help").suggest("/McInfected Help");
	}
	
	@Override
	public String getHelpMessage() {
		return getMessanger().getMessage(false, Messages.Format__Prefix) + "§7/McInfected §oHelp";
	}
	
	@Override
	public String getPermission() {
		return "McInfected.Help";
	}
	
	@Override
	public List<String> getTabs(String[] args) {
		return Arrays.asList(new String[] { "" });
	}
}
