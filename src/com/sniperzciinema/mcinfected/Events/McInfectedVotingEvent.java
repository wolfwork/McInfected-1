
package com.sniperzciinema.mcinfected.Events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.sniperzciinema.mcinfected.Lobby;
import com.sniperzciinema.mcinfected.McInfected;


public class McInfectedVotingEvent extends Event {
	
	private static final HandlerList	handlers	= new HandlerList();
	
	public static HandlerList getHandlerList() {
		return McInfectedVotingEvent.handlers;
	}
	
	public McInfectedVotingEvent()
	{
	}
	
	@Override
	public HandlerList getHandlers() {
		return McInfectedVotingEvent.handlers;
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
