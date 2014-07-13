
package com.sniperzciinema.mcinfected.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.sniperzciinema.mcinfected.McInfected;
import com.sniperzciinema.mcinfected.Messanger.Messages;
import com.sniperzciinema.mcinfected.IPlayers.IPlayer;


public class Combat implements Listener {
	
	public static enum DeathType
	{
		BOW, GUN, OTHER, SWORD;
	};
	
	// If the damage is done by a Entity
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDamage(EntityDamageByEntityEvent e) {
		
		// Hmm so an Entity hit them, but which entity?
		
		// Is the victim a player?
		if (e.getEntity() instanceof Player)
		{
			Player victim = (Player) e.getEntity();
			Player killer = null;
			
			// Before we go any farther lets make sure they're even in the game
			if (McInfected.getLobby().isIPlayer(victim))
			{
				// By default we'll say it was melee
				DeathType death = DeathType.SWORD;
				
				// Was the entity that did the damage a player?
				if (e.getDamager() instanceof Player)
					killer = (Player) e.getDamager();
				
				// Was the entity that did the damage a arrow?
				else if (e.getDamager() instanceof Arrow)
				{
					victim = (Player) e.getEntity();
					Arrow arrow = (Arrow) e.getDamager();
					
					// Was the shooter of the arrow a player?
					if (arrow.getShooter() instanceof Player)
					{
						killer = (Player) arrow.getShooter();
						death = DeathType.BOW;
					}
				}
				else if (e.getDamager() instanceof Snowball)
				{
					victim = (Player) e.getEntity();
					Snowball sb = (Snowball) e.getDamager();
					
					// Was the shooter of the arrow a player?
					if (sb.getShooter() instanceof Player)
					{
						killer = (Player) sb.getShooter();
						death = DeathType.GUN;
					}
				}
				else if (e.getDamager() instanceof Egg)
				{
					victim = (Player) e.getEntity();
					Egg egg = (Egg) e.getDamager();
					
					// Was the shooter of the arrow a player?
					if (egg.getShooter() instanceof Player)
					{
						killer = (Player) egg.getShooter();
						death = DeathType.GUN;
					}
				}
				// Lets make sure the final killer is a Player
				if ((killer instanceof Player) && McInfected.getLobby().isIPlayer(killer))
				{
					IPlayer iVictim = McInfected.getLobby().getIPlayer(victim);
					IPlayer iKiller = McInfected.getLobby().getIPlayer(killer);
					
					// Make sure they arn't on the same team
					if (iVictim.getTeam() != iKiller.getTeam() && McInfected.getLobby().isStarted())
					{
						
						iVictim.setLastDamager(iKiller);
						
						// If it was enough to kill the player
						if ((victim.getHealth() - e.getDamage()) <= 0)
						{
							e.setDamage(0);
							
							iVictim.die();
							iKiller.kill();
							
							McInfected.getMessanger().broadcast(McInfected.getMessanger().getDeathMessage(iVictim.getTeam(), death, "<killer>", killer.getName(), "<killed>", victim.getName()));
							
							if (McInfected.getLobby().getHumans().isEmpty())
							{
								McInfected.getMessanger().broadcast(McInfected.getMessanger().getMessage(true, Messages.Game__Over__Zombies_Win));
								McInfected.getLobby().getTimers().startEndGame();
							}
						}
					}
					else
					{
						e.setDamage(0);
						e.setCancelled(true);
					}
				}
				else
				{
					e.setDamage(0);
					e.setCancelled(true);
				}
				
			}
		}
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDamage(PlayerDeathEvent e) {
		if (McInfected.getLobby().isIPlayer(e.getEntity()))
		{
			e.setDeathMessage("");
			e.setDroppedExp(0);
			IPlayer iVictim = McInfected.getLobby().getIPlayer(e.getEntity());
			
			if (McInfected.getLobby().isStarted())
			{
				iVictim.die();
				if (iVictim.getLastDamager() != null)
				{
					IPlayer iKiller = iVictim.getLastDamager();
					
					skipRespawnScreen(e.getEntity());
					iKiller.kill();
					
					McInfected.getMessanger().broadcast(McInfected.getMessanger().getDeathMessage(iVictim.getTeam(), DeathType.OTHER, "<killer>", iKiller.getPlayer().getName(), "<killed>", iVictim.getPlayer().getName()));
				}
				if (McInfected.getLobby().getHumans().isEmpty())
				{
					McInfected.getMessanger().broadcast(McInfected.getMessanger().getMessage(true, Messages.Game__Over__Zombies_Win));
					McInfected.getLobby().getTimers().startEndGame();
				}
			}
			else
			{
				
				final IPlayer victim = iVictim;
				
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(McInfected.getPlugin(), new Runnable()
				{
					
					@Override
					public void run() {
						if (victim.getPlayer().isDead())
							victim.getPlayer().setHealth(20.0);
						
						McInfected.getLobby().teleport(victim);
						
					}
				}, 1L);
			}
		}
	}
	
	public void skipRespawnScreen(final Player killed) {
		Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(McInfected.getPlugin(), new Runnable()
		{
			
			@Override
			public void run() {
				if (killed.isDead())
					killed.setHealth(20.0);
			}
		}, 1L);
		
	}
}
