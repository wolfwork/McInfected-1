
package com.sniperzciinema.mcinfected.Utils;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.sniperzciinema.mcinfected.McInfected;
import com.sniperzciinema.mcinfected.IPlayers.IPlayer;


public class TeleportFix implements Listener {
	
	private final int	TELEPORT_FIX_DELAY	= 15;	// ticks
																							
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlayerTeleport(PlayerTeleportEvent event) {
		
		final Player player = event.getPlayer();
		if (McInfected.getLobby().isIPlayer(player))
			// Fix the visibility issue one tick later
			Bukkit.getScheduler().scheduleSyncDelayedTask(McInfected.getPlugin(), new Runnable()
			{
				
				@Override
				public void run() {
					// Refresh nearby clients
					final List<IPlayer> nearby = McInfected.getLobby().getIPlayers();
					
					// Hide every player
					updateEntities(player, nearby);
					
					// Then show them again
					Bukkit.getScheduler().scheduleSyncDelayedTask(McInfected.getPlugin(), new Runnable()
					{
						
						@Override
						public void run() {
							updateEntities(player, nearby);
						}
					}, 1);
				}
			}, this.TELEPORT_FIX_DELAY);
	}
	
	public void updateEntities(Player player, List<IPlayer> nearby) {
		// Hide or show every player to tpedPlayer
		// and hide or show tpedPlayer to every player.
		for (IPlayer iPlayer : nearby)
			
			// When disguises are enabled they make it more difficult
			if (McInfected.getSettings().isDisguiseSupportEnabled())
			{
				// If the player in the loop is not disguised
				if (!McInfected.getDisguiseManager().isDisguised(iPlayer.getPlayer()))
					// Hide or show the player in the loop
					if (player.canSee(iPlayer.getPlayer()))
						player.hidePlayer(iPlayer.getPlayer());
					else
						player.showPlayer(iPlayer.getPlayer());
				// If the player that teleported isn't disguised
				if (!McInfected.getDisguiseManager().isDisguised(player))
					// Hide or show the player that teleported
					if (iPlayer.getPlayer().canSee(player))
						iPlayer.getPlayer().hidePlayer(player);
					else
						iPlayer.getPlayer().showPlayer(player);
			}
			else
			{
				
				// Hide or show the player in the loop
				if (player.canSee(iPlayer.getPlayer()))
					player.hidePlayer(iPlayer.getPlayer());
				else
					player.showPlayer(iPlayer.getPlayer());
				
				// Hide or show the player that teleported
				if (iPlayer.getPlayer().canSee(player))
					iPlayer.getPlayer().hidePlayer(player);
				else
					iPlayer.getPlayer().showPlayer(player);
				
			}
	}
	
}
