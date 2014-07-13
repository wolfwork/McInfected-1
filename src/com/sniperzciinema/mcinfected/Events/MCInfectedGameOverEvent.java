
package com.sniperzciinema.mcinfected.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.sniperzciinema.mcinfected.Lobby;
import com.sniperzciinema.mcinfected.McInfected;


public class MCInfectedGameOverEvent extends Event {
	
	private static final HandlerList	handlers	= new HandlerList();
	
	public static HandlerList getHandlerList() {
		return MCInfectedGameOverEvent.handlers;
	}
	
	public MCInfectedGameOverEvent()
	{
	}
	
	@Override
	public HandlerList getHandlers() {
		return MCInfectedGameOverEvent.handlers;
	}
	
	/**
	 * The lobby can be used to get many different things
	 * 
	 * @return the lobby
	 */
	public Lobby getLobby() {
		return McInfected.getLobby();
	}
	
}
