
package com.sniperzciinema.mcinfected.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.sniperzciinema.mcinfected.McInfected;
import com.sniperzciinema.mcinfected.Messanger.Messages;


public class MiscListeners implements Listener {
	
	@EventHandler(priority = EventPriority.LOW)
	public void onPlayerHunger(FoodLevelChangeEvent e) {
		if (!e.isCancelled())
		{
			Player player = (Player) e.getEntity();
			if (McInfected.getLobby().isIPlayer(player))
				e.setCancelled(true);
		}
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerCommandAttempt(PlayerCommandPreprocessEvent e) {
		if (McInfected.getLobby().isIPlayer(e.getPlayer()) && !e.getPlayer().isOp())
		{
			String msg = null;
			if (e.getMessage().contains(" "))
			{
				String[] ss = e.getMessage().split(" ");
				msg = ss[0];
			}
			else
				msg = e.getMessage();
			if (!McInfected.getSettings().getAllowedCommands().contains(msg.toLowerCase()) && !e.getMessage().toLowerCase().contains("inf"))
			{
				e.getPlayer().sendMessage(McInfected.getMessanger().getMessage(true, Messages.Error__Misc__Cant_Use_Command));
				e.setCancelled(true);
			}
		}
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onArrowShoot(final ProjectileHitEvent e) {
		
		if (e.getEntity().getShooter() instanceof Player)
		{
			Player player = (Player) e.getEntity().getShooter();
			if (McInfected.getLobby().isIPlayer(player) && !McInfected.getLobby().isStarted())
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(McInfected.getPlugin(), new Runnable()
				{
					
					@Override
					public void run() {
						if (e.getEntity() != null)
							e.getEntity().remove();
					}
				}, 1);
		}
	}
	
}
