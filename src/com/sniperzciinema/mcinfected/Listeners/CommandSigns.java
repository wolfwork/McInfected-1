
package com.sniperzciinema.mcinfected.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.sniperzciinema.mcinfected.McInfected;
import com.sniperzciinema.mcinfected.Messanger.Messages;


public class CommandSigns implements Listener {
	
	// Sign ------ CommandSigns ------ Click
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerClickCommandSign(PlayerInteractEvent event) {
		if (!event.isCancelled())
		{
			Player player = event.getPlayer();
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
			{
				Block b = event.getClickedBlock();
				if ((b.getType() == Material.SIGN_POST) || (b.getType() == Material.WALL_SIGN))
				{
					Sign sign = ((Sign) b.getState());
					if (sign.getLine(0).contains("[McInfected]") && sign.getLine(1).contains("Command"))
					{
						String command = ChatColor.stripColor(sign.getLine(2) + sign.getLine(3));
						if (player.hasPermission("Infected.Sign.Command.*") || player.hasPermission("Infected.Sign.CommandSet." + command))
						{
							command = "/McInfected " + command;
							player.performCommand(command);
						}
						else
							player.sendMessage(McInfected.getMessanger().getMessage(true, Messages.Error__Misc__Invalid_Permission));
					}
				}
			}
		}
	}
	
	// Sign ------ CommandSet ------ Create
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerCreateCommandSetSign(SignChangeEvent event) {
		if (!event.isCancelled())
		{
			Player player = event.getPlayer();
			
			Sign sign = (Sign) event.getBlock().getState();
			if (sign.getLine(0).contains("[McInfected]") && sign.getLine(1).contains("Command"))
				if (player.hasPermission("Infected.Setup.Command"))
				{
					String signName = sign.getLine(2) + sign.getLine(3);
					
					sign.setLine(0, ChatColor.DARK_RED + "[McInfected]");
					sign.setLine(1, ChatColor.DARK_AQUA + "-Command-");
					sign.setLine(2, sign.getLine(2));
					sign.setLine(3, sign.getLine(3));
					
					player.sendMessage(McInfected.getMessanger().getMessage(true, Messages.Sign__Command__Created, "<sign>", signName));
				}
				else
					player.sendMessage(McInfected.getMessanger().getMessage(true, Messages.Error__Misc__Invalid_Permission));
		}
	}
	
}
