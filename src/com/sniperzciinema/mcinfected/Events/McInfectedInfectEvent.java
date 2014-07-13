
package com.sniperzciinema.mcinfected.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.sniperzciinema.mcinfected.Lobby;
import com.sniperzciinema.mcinfected.McInfected;


public class McInfectedInfectEvent extends Event implements Cancellable {
	
	private static final HandlerList	handlers	= new HandlerList();
	
	public static HandlerList getHandlerList() {
		return McInfectedInfectEvent.handlers;
	}
	
	private boolean	canceled;
	
	private Player	player;
	
	public McInfectedInfectEvent(Player player)
	{
		this.player = player;
	}
	
	@Override
	public HandlerList getHandlers() {
		return McInfectedInfectEvent.handlers;
	}
	
	/**
	 * The lobby can be used to get many different things
	 * 
	 * @return the lobby
	 */
	public Lobby getLobby() {
		return McInfected.getLobby();
	}
	
	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return this.player;
	}
	
	@Override
	public boolean isCancelled() {
		return this.canceled;
	}
	
	@Override
	public void setCancelled(boolean canceled) {
		this.canceled = canceled;
	}
	
}
