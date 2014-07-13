
package com.sniperzciinema.mcinfected.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.sniperzciinema.mcinfected.Lobby;
import com.sniperzciinema.mcinfected.McInfected;
import com.sniperzciinema.mcinfected.Listeners.Combat.DeathType;


public class McInfectedDeathEvent extends Event implements Cancellable {
	
	private static final HandlerList	handlers	= new HandlerList();
	
	public static HandlerList getHandlerList() {
		return McInfectedDeathEvent.handlers;
	}
	
	private boolean		canceled;
	private DeathType	deathType;
	
	private Player		killed, killer;
	
	public McInfectedDeathEvent(Player killer, Player killed, DeathType deathType)
	{
		this.killer = killer;
		this.killed = killed;
		setDeathType(deathType);
	}
	
	/**
	 * @return the deathType
	 */
	public DeathType getDeathType() {
		return this.deathType;
	}
	
	@Override
	public HandlerList getHandlers() {
		return McInfectedDeathEvent.handlers;
	}
	
	/**
	 * @return the killed
	 */
	public Player getKilled() {
		return this.killed;
	}
	
	/**
	 * @return the killer
	 */
	public Player getKiller() {
		return this.killer;
	}
	
	/**
	 * The lobby can be used to get many different things
	 * 
	 * @return the lobby
	 */
	public Lobby getLobby() {
		return McInfected.getLobby();
	}
	
	@Override
	public boolean isCancelled() {
		return this.canceled;
	}
	
	@Override
	public void setCancelled(boolean canceled) {
		this.canceled = canceled;
	}
	
	/**
	 * @param deathType
	 *          the deathType to set
	 */
	public void setDeathType(DeathType deathType) {
		this.deathType = deathType;
	}
	
}
