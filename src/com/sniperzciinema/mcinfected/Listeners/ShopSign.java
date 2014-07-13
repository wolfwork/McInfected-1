
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
import org.bukkit.inventory.ItemStack;

import com.sniperzciinema.mcinfected.McInfected;
import com.sniperzciinema.mcinfected.Messanger.Messages;
import com.sniperzciinema.mcinfected.Utils.ItemUtil;


public class ShopSign implements Listener {
	
	// Sign ------ ShopSigns ------ Click
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
					if (sign.getLine(0).contains("[McInfected]") && !sign.getLine(1).contains("Command"))
					{
						String item = ChatColor.stripColor(sign.getLine(2));
						if (player.hasPermission("Infected.Sign.Shop.*") || player.hasPermission("Infected.Sign.Shop." + item))
						{
							String costString = ChatColor.stripColor(sign.getLine(3).replaceAll("Cost:", ""));
							ItemStack itemStack = ItemUtil.getItemStack(McInfected.getFileManager().getShops().getString(item));
							if (costString.startsWith("\\$") && (McInfected.getEconomy() != null))
							{
								int cost = Integer.valueOf(costString.replaceAll("\\$", ""));
								if (McInfected.getEconomy().getBalance(player.getName()) >= cost)
								{
									McInfected.getEconomy().withdrawPlayer(player.getName(), cost);
									player.getInventory().addItem(itemStack);
									player.sendMessage(McInfected.getMessanger().getMessage(true, Messages.Sign__Shop__Purchased, "<item>", itemStack.getItemMeta().getDisplayName()));
								}
								else
									player.sendMessage(McInfected.getMessanger().getMessage(true, Messages.Error__Sign__Shop__Not_Enough, "<economy>", "money"));
							}
							else
							{
								int cost = Integer.valueOf(costString.replaceAll("\\$", ""));
								if (McInfected.getLobby().getIPlayer(player).getScore() >= cost)
								{
									McInfected.getLobby().getIPlayer(player).setScore(McInfected.getLobby().getIPlayer(player).getScore() - cost);
									player.getInventory().addItem(itemStack);
									player.sendMessage(McInfected.getMessanger().getMessage(true, Messages.Sign__Shop__Purchased, "<item>", itemStack.getItemMeta().getDisplayName()));
									
								}
								else
									player.sendMessage(McInfected.getMessanger().getMessage(true, Messages.Error__Sign__Shop__Not_Enough, "<economy>", "score"));
							}
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
			if (sign.getLine(0).contains("[McInfected]") && sign.getLine(1).contains("Shop"))
				if (player.hasPermission("Infected.Setup.Shop"))
				{
					String signName = ChatColor.stripColor(sign.getLine(2));
					if (McInfected.getFileManager().getShops().getString(signName) != null)
					{
						sign.setLine(0, ChatColor.DARK_RED + "[McInfected]");
						sign.setLine(1, ChatColor.DARK_AQUA + "-Shop-");
						sign.setLine(2, ItemUtil.getItemStack(McInfected.getFileManager().getShops().getString(sign.getLine(2))).getItemMeta().getDisplayName());
						sign.setLine(3, ChatColor.GREEN + "Cost: " + ChatColor.WHITE + sign.getLine(3));
						
						player.sendMessage(McInfected.getMessanger().getMessage(true, Messages.Sign__Command__Created, "<sign>", signName));
					}
					else
						player.sendMessage(McInfected.getMessanger().getMessage(true, Messages.Sign__Shop__Doesnt_Exist));
				}
				else
					player.sendMessage(McInfected.getMessanger().getMessage(true, Messages.Error__Misc__Invalid_Permission));
		}
	}
	
}
