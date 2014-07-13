
package com.sniperzciinema.mcinfected.Command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import com.sniperzciinema.mcinfected.McInfected;
import com.sniperzciinema.mcinfected.Messanger.Messages;
import com.sniperzciinema.mcinfected.Command.FancyMessages.FancyMessage;
import com.sniperzciinema.mcinfected.Command.SubCommands.AdminCommand;
import com.sniperzciinema.mcinfected.Command.SubCommands.ArenasCommand;
import com.sniperzciinema.mcinfected.Command.SubCommands.CreateCommand;
import com.sniperzciinema.mcinfected.Command.SubCommands.HelpCommand;
import com.sniperzciinema.mcinfected.Command.SubCommands.JoinCommand;
import com.sniperzciinema.mcinfected.Command.SubCommands.KitCommand;
import com.sniperzciinema.mcinfected.Command.SubCommands.LeaveCommand;
import com.sniperzciinema.mcinfected.Command.SubCommands.SetLobbyCommand;
import com.sniperzciinema.mcinfected.Command.SubCommands.SetSpawnCommand;
import com.sniperzciinema.mcinfected.Command.SubCommands.VoteCommand;


public class CHandler implements TabCompleter, CommandExecutor {
	
	private ArrayList<SubCommand>	commands;
	
	public CHandler()
	{
		this.commands = new ArrayList<SubCommand>();
		
		registerSubCommand(new AdminCommand());
		registerSubCommand(new ArenasCommand());
		registerSubCommand(new CreateCommand());
		registerSubCommand(new HelpCommand());
		registerSubCommand(new JoinCommand());
		registerSubCommand(new KitCommand());
		registerSubCommand(new LeaveCommand());
		registerSubCommand(new SetLobbyCommand());
		registerSubCommand(new SetSpawnCommand());
		registerSubCommand(new VoteCommand());
		
	}
	
	public SubCommand getSubCommand(String cmdName) {
		for (SubCommand cmd : this.commands)
			if (cmd.getName().equalsIgnoreCase(cmdName))
				return cmd;
		return null;
	}
	
	public ArrayList<SubCommand> getSubCommands() {
		return this.commands;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (cmd.getName().equalsIgnoreCase("McInfected"))
		{
			sender.sendMessage("");
			Player p = null;
			if (sender instanceof Player)
				p = (Player) sender;
			
			if (args.length == 0)
			{
				sender.sendMessage(McInfected.getMessanger().getMessage(false, Messages.Format__Header, "<title>", "McInfected"));
				if (McInfected.getSettings().isUpdaterEnabled() && McInfected.getUpdater().hasUpdate())
					if (p == null)
						sender.sendMessage(McInfected.getMessanger().getMessage(false, Messages.Format__Prefix) + ChatColor.RED + ChatColor.BOLD + "Update Available: " + ChatColor.WHITE + ChatColor.BOLD + McInfected.getUpdater().getLatestFileVersion());
					else
						new FancyMessage(
								McInfected.getMessanger().getMessage(false, Messages.Format__Prefix) + "§c§lUpdate Available: §f§l" + McInfected.getUpdater().getLatestFileVersion()).tooltip("§aClick to open page").link(McInfected.getUpdater().getLatestFileLink()).send(p);
				
				sender.sendMessage(McInfected.getMessanger().getMessage(false, Messages.Format__Prefix) + ChatColor.GRAY + "Author: " + ChatColor.GREEN + ChatColor.BOLD + "SniperzCiinema");
				sender.sendMessage(McInfected.getMessanger().getMessage(false, Messages.Format__Prefix) + ChatColor.GRAY + "Version: " + ChatColor.GREEN + ChatColor.BOLD + McInfected.getPlugin().getDescription().getVersion());
				sender.sendMessage(McInfected.getMessanger().getMessage(false, Messages.Format__Prefix) + ChatColor.WHITE + "BukkitDev: " + ChatColor.GREEN + ChatColor.BOLD + "http://bit.ly/McInfected");
				if (p == null)
					sender.sendMessage(McInfected.getMessanger().getMessage(false, Messages.Format__Prefix) + ChatColor.YELLOW + "For Help type: /McInfected Help");
				else
					new FancyMessage(McInfected.getMessanger().getMessage(false, Messages.Format__Prefix) + "§eFor Help type: §l/McInfected Help").tooltip("§aClick to autotype").suggest("/McInfected Help").send(p);
				sender.sendMessage(McInfected.getMessanger().getMessage(false, Messages.Format__Prefix) + ChatColor.WHITE + ChatColor.UNDERLINE + McInfected.getPlugin().getDescription().getDescription());
				
			}
			else
			{
				for (SubCommand subCommand : this.commands)
					if (subCommand.getName().equalsIgnoreCase(args[0]) || subCommand.getAliases().contains(args[0].toLowerCase()))
					{
						subCommand.execute(sender, args);
						return true;
					}
				sender.sendMessage(McInfected.getMessanger().getMessage(true, Messages.Error__Command__Unkown));
			}
		}
		return true;
	}
	
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
		
		if (args.length == 1)
		{
			List<String> cmds = new ArrayList<String>();
			
			for (SubCommand subCommand : this.commands)
				cmds.add(subCommand.getName());
			
			return TabCompletionHelper.getPossibleCompletionsForGivenArgs(args, cmds);
		}
		
		else
		{
			List<String> params = new ArrayList<String>();
			for (SubCommand subCommand : this.commands)
				if (subCommand.getName().equalsIgnoreCase(args[0]) || subCommand.getAliases().contains(args[0].toLowerCase()))
					params = subCommand.getTabs(args);
			System.out.println(params);
			if (params != null)
				return TabCompletionHelper.getPossibleCompletionsForGivenArgs(args, params);
		}
		return null;
	}
	
	public void registerSubCommand(SubCommand subCommand) {
		this.commands.add(subCommand);
	}
	
	public void unRegisterSubCommand(SubCommand subCommand) {
		this.commands.remove(subCommand);
	}
}
