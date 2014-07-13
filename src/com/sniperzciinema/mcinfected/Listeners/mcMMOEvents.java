
package com.sniperzciinema.mcinfected.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.gmail.nossr50.events.fake.FakeEntityDamageByEntityEvent;
import com.gmail.nossr50.events.skills.abilities.McMMOPlayerAbilityActivateEvent;
import com.gmail.nossr50.events.skills.unarmed.McMMOPlayerDisarmEvent;
import com.sniperzciinema.mcinfected.McInfected;


/**
 * The mcMMO Api Listener
 */
public class mcMMOEvents implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void mcMMOAbilityActivate(McMMOPlayerAbilityActivateEvent e) {
		if (McInfected.getLobby().isIPlayer(e.getPlayer()))
			e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void mcMMODisarm(McMMOPlayerDisarmEvent e) {
		if (McInfected.getLobby().isIPlayer(e.getDefender()) && McInfected.getLobby().isIPlayer(e.getPlayer()))
			e.setCancelled(true);
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void mcMMOExtraDamage(FakeEntityDamageByEntityEvent e) {
		if ((e.getEntity() instanceof Player) && (e.getDamager() instanceof Player))
			if (McInfected.getLobby().isIPlayer((Player) e.getEntity()) && McInfected.getLobby().isIPlayer((Player) e.getDamager()))
			{
				e.setDamage(0);
				e.setCancelled(true);
			}
		
	}
	
}
