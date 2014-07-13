
package com.sniperzciinema.mcinfected.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.massivecraft.factions.event.FactionsEventPowerChange;
import com.massivecraft.factions.event.FactionsEventPvpDisallowed;
import com.sniperzciinema.mcinfected.McInfected;


/**
 * The Factions Api Listener
 */
public class FactionsEvents implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler(priority = EventPriority.HIGHEST)
	public void factionLoosePower(FactionsEventPowerChange e) {
		Player player = Bukkit.getPlayer(e.getUPlayer().getName());
		if (McInfected.getLobby().isIPlayer(player))
			e.setCancelled(true);
		
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void factionPVP(FactionsEventPvpDisallowed e) {
		
		Player attacker = e.getAttacker();
		Player defender = e.getDefender();
		
		if (McInfected.getLobby().isIPlayer(attacker) && McInfected.getLobby().isIPlayer(defender))
			e.setCancelled(true);
	}
	
}
