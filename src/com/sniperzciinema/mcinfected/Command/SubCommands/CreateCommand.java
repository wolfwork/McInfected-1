
package com.sniperzciinema.mcinfected.Command.SubCommands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.block.Block;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.sniperzciinema.mcinfected.McInfected;
import com.sniperzciinema.mcinfected.Messanger.Messages;
import com.sniperzciinema.mcinfected.Command.SubCommand;
import com.sniperzciinema.mcinfected.Command.FancyMessages.FancyMessage;
import com.sniperzciinema.mcinfected.Utils.StringUtil;


public class CreateCommand extends SubCommand {
	
	public CreateCommand()
	{
		super("Create");
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) throws CommandException {
		
		if (!(sender instanceof Player))
			sender.sendMessage(getMessanger().getMessage(true, Messages.Error__Command__Not_A_Player));
		
		else if (!sender.hasPermission(getPermission()))
			sender.sendMessage(McInfected.getMessanger().getMessage(true, Messages.Error__Misc__Invalid_Permission));
		
		else
		{
			
			if (args.length >= 2)
			{
				String arenaName = StringUtil.getCapitalized(args[1]);
				if (!McInfected.getLobby().isArena(arenaName))
				{
					McInfected.getLobby().createArena(arenaName);
					Block block = ((Player) sender).getLocation().add(0, -1.0, 0).getBlock();
					ItemStack is = new ItemStack(block.getType());
					is.setDurability(block.getData());
					
					McInfected.getLobby().getArena(arenaName).setIcon(is);
					sender.sendMessage(getMessanger().getMessage(true, Messages.Command__Create, "<arena>", arenaName));
				}
				else
					sender.sendMessage(getMessanger().getMessage(true, Messages.Error__Arena__Already_Exists));
				
			}
			if (args.length == 3)
				if (McInfected.getLobby().isArena(args[1]))
					McInfected.getLobby().getArena(args[1]).setCreator(args[2]);
			if (args.length == 1)
				getFancyMessage().send((Player) sender);
		}
		
	}
	
	@Override
	public List<String> getAliases() {
		return Arrays.asList(new String[] { "c" });
	}
	
	@Override
	public FancyMessage getFancyMessage() {
		return new FancyMessage(getHelpMessage()).tooltip("Create", " ", "§eCreate an Arena", " ", "§f§l/McInfected Create <ArenaName> [Creator]").suggest("/McInfected Create");
	}
	
	@Override
	public String getHelpMessage() {
		return getMessanger().getMessage(false, Messages.Format__Prefix) + "§7/McInfected §oCreate <Arena> [Creator]";
	}
	
	@Override
	public String getPermission() {
		return "McInfected.Setup.Create";
	}
	
	@Override
	public List<String> getTabs(String[] args) {
		return Arrays.asList(new String[] { "" });
		
	}
}
