
package com.sniperzciinema.mcinfected;

import org.bukkit.ChatColor;

import com.sniperzciinema.mcinfected.IPlayers.IPlayer;
import com.sniperzciinema.mcinfected.IPlayers.IPlayer.Team;
import com.sniperzciinema.mcinfected.Listeners.Combat.DeathType;
import com.sniperzciinema.mcinfected.Utils.StringUtil;


public class Messanger {
	
	public enum Messages
	{
		Command__Create,
		Command__Game_Joined,
		Command__Game_Left,
		Command__Join,
		Command__Leave,
		Command__SetLobby,
		Command__SetSpawn,
		Command__End,
		Command__Start,
		Error__Arena__No_Valid_Arenas,
		Error__Arena__Already_Exists,
		Error__Command__Not_A_Player,
		Error__Command__Not_Enough_Players,
		Error__Game__Already_In_A_Game,
		Error__Game__Already_Started,
		Error__Game__Not_Enough_Players,
		Error__Game__Not_In_A_Game,
		Error__Game__Not_Started,
		Error__Lobby__No_Location,
		Error__Misc__Invalid_Permission,
		Error__Sign__CommandSet__Doesnt_Exist,
		Error__Sign__Shop__Not_Enough,
		Error__Command__Unkown,
		Format__Header,
		Format__Line,
		Format__Prefix,
		Game__Arena__Chosen,
		Game__Infected,
		Game__Infecting,
		Game__Over__Humans_Win,
		Game__Over__Notification,
		Game__Over__Infecteds_Win,
		Game__Time__Game,
		Game__Time__Infecting,
		Game__Time__Measurements__Minute,
		Game__Time__Measurements__Minutes,
		Game__Time__Measurements__Second,
		Game__Time__Measurements__Seconds,
		Game__Time__PreGame,
		Game__Time__Voting,
		Game__Voted,
		Menu__Kit__Choose,
		Menu__Kit__Chosen,
		Menu__Kit__Return,
		Menu__Team__Choose,
		Menu__Vote__Choose,
		Menu__Vote__Random,
		Picture__Human__To_Win,
		Picture__Human__You,
		Picture__Infected__To_Win,
		Picture__Infected__You,
		Sign__Command__Created,
		Sign__CommandSet__Created,
		Sign__Shop__Doesnt_Exist,
		Sign__Shop__Purchased,
		Command__Reloaded,
		Error__Misc__Cant_Use_Command,
		Game__Voting;
	};
	
	private FileManager	fileManager;
	
	public Messanger()
	{
		this.fileManager = McInfected.getFileManager();
	}
	
	/**
	 * Send a message to a whole lobby
	 * 
	 * @param string
	 */
	public void broadcast(String string) {
		for (IPlayer iPlayer : McInfected.getLobby().getIPlayers())
			iPlayer.getPlayer().sendMessage(string);
	}
	
	/**
	 * @param deadTeam
	 * @param deathtype
	 * @param variables
	 * @return the string for the team dieing by the deathtype
	 */
	public String getDeathMessage(Team deadTeam, DeathType deathtype, String... variables) {
		String configLoc = "Death Messages." + deadTeam.toString() + "." + StringUtil.getCapitalized(deathtype.toString());
		
		String message = this.fileManager.getMessages().getString(configLoc);
		
		if (message == null)
			return ChatColor.RED + "Unable to find the message \"" + ChatColor.BOLD + configLoc + ChatColor.RED + "\"...";
		
		for (int i = 0; i != (variables.length); i++)
			if ((i % 2) != 0)
				message = message.replaceAll(variables[i - 1], variables[i]);
		message = StringUtil.addColor(message);
		return (getMessage(false, Messages.Format__Prefix)) + message;
	}
	
	/**
	 * @param usePrefix
	 * @param msg
	 * @param variables
	 * @return the message
	 */
	public String getMessage(boolean usePrefix, Messages msg, String... variables) {
		String configLoc = msg.toString();
		
		configLoc = configLoc.replaceAll("__", "\\.");
		configLoc = configLoc.replaceAll("_", " ");
		
		String message = this.fileManager.getMessages().getString(configLoc);
		
		if (message == null)
			return ChatColor.RED + "Unable to find the message \"" + ChatColor.BOLD + configLoc + ChatColor.RED + "\"...";
		
		for (int i = 0; i != (variables.length); i++)
			if ((i % 2) != 0)
				message = message.replaceAll(variables[i - 1], variables[i]);
		message = StringUtil.addColor(message);
		return (usePrefix ? getMessage(false, Messages.Format__Prefix) : "") + message;
	}
}
