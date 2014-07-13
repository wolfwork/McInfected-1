
package com.sniperzciinema.mcinfected.IPlayers;

import java.sql.SQLException;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import com.sniperzciinema.mcinfected.McInfected;


public class Stats {
	
	private UUID	uuid;
	
	private int		kills	= -1, deaths = -1, killStreak = -1;
	private int		score	= -1;
	private int		time	= -1;
	private int		wins	= -1, losses = -1;
	
	public Stats(OfflinePlayer op)
	{
		this.uuid = op.getUniqueId();
	}
	
	public Stats(Player player)
	{
		this.uuid = player.getUniqueId();
	}
	
	public Stats(UUID uuid)
	{
		this.uuid = uuid;
	}
	
	@SuppressWarnings("deprecation")
	public Stats(String name)
	{
		this.uuid = Bukkit.getOfflinePlayer(name).getUniqueId();
	}
	
	/**
	 * @return the Kills
	 */
	public int getKills() {
		if (this.kills == -1)
			if (McInfected.getSettings().isMySQLEnabled())
				try
				{
					this.kills = McInfected.getMySQLManager().getInt("McInfected", "Kills", uuid);
				}
				catch (SQLException e)
				{
					this.kills = 0;
				}
			else
				this.kills = McInfected.getFileManager().getPlayers().getInt(uuid + ".Kills");
		
		return this.kills;
	}
	
	/**
	 * @return the KillStreak
	 */
	public int getKillStreak() {
		if (this.killStreak == -1)
			if (McInfected.getSettings().isMySQLEnabled())
				try
				{
					this.killStreak = McInfected.getMySQLManager().getInt("McInfected", "KillStreak", uuid);
				}
				catch (SQLException e)
				{
					this.killStreak = 0;
				}
			else
				this.killStreak = McInfected.getFileManager().getPlayers().getInt(uuid + ".KillStreak");
		return this.killStreak;
	}
	
	/**
	 * @return the Time
	 */
	public long getTime() {
		if (this.time == -1)
			if (McInfected.getSettings().isMySQLEnabled())
				try
				{
					this.time = McInfected.getMySQLManager().getInt("McInfected", "Time", uuid);
				}
				catch (SQLException e)
				{
					this.time = 0;
				}
			else
				this.time = McInfected.getFileManager().getPlayers().getInt(uuid + ".Time");
		return this.time;
	}
	
	/**
	 * @param Kills
	 *          the Kills to set
	 */
	public void setKills(int kills) {
		this.kills = kills;
		
		if (McInfected.getSettings().isMySQLEnabled())
			try
			{
				McInfected.getMySQLManager().update("McInfected", "Kills", this.kills, uuid);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		else
			McInfected.getFileManager().getPlayers().set(uuid + ".Kills", this.kills);
	}
	
	/**
	 * @param Score
	 *          the Score to set
	 */
	public void setScore(int score) {
		this.score = score;
		
		if (McInfected.getSettings().isMySQLEnabled())
			try
			{
				McInfected.getMySQLManager().update("McInfected", "Score", this.score, uuid);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		else
			McInfected.getFileManager().getPlayers().set(uuid + ".Score", this.score);
	}
	
	/**
	 * @param Time
	 *          the Time to set
	 */
	public void setTime(int Time) {
		this.time = Time;
		if (McInfected.getSettings().isMySQLEnabled())
			try
			{
				McInfected.getMySQLManager().update("McInfected", "Time", this.time, uuid);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		else
			McInfected.getFileManager().getPlayers().set(uuid + ".Time", this.time);
	}
	
	/**
	 * @param Wins
	 *          the Wins to set
	 */
	public void setWins(int Wins) {
		this.wins = Wins;
		if (McInfected.getSettings().isMySQLEnabled())
			try
			{
				McInfected.getMySQLManager().update("McInfected", "Wins", this.wins, uuid);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		else
			McInfected.getFileManager().getPlayers().set(uuid + ".Wins", this.wins);
	}
	
	/**
	 * @param Losses
	 *          the Losses to set
	 */
	public void setLosses(int Losses) {
		this.losses = Losses;
		if (McInfected.getSettings().isMySQLEnabled())
			try
			{
				McInfected.getMySQLManager().update("McInfected", "Losses", this.losses, uuid);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		else
			McInfected.getFileManager().getPlayers().set(uuid + ".Losses", this.losses);
	}
	
	/**
	 * @param KillStreak
	 *          the KillStreak to set
	 */
	public void setKillStreak(int KillStreak) {
		this.killStreak = KillStreak;
		
		if (McInfected.getSettings().isMySQLEnabled())
			try
			{
				McInfected.getMySQLManager().update("McInfected", "KillStreak", this.killStreak, uuid);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		else
			McInfected.getFileManager().getPlayers().set(uuid + ".KillStreak", this.killStreak);
	}
	
	/**
	 * @param Deaths
	 *          the Deaths to set
	 */
	public void setDeaths(int Deaths) {
		this.deaths = Deaths;
		if (McInfected.getSettings().isMySQLEnabled())
			try
			{
				McInfected.getMySQLManager().update("McInfected", "Deaths", this.deaths, uuid);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		else
			McInfected.getFileManager().getPlayers().set(uuid + ".Deaths", this.deaths);
	}
	
	/**
	 * @return the Wins
	 */
	public int getWins() {
		if (this.wins == -1)
			if (McInfected.getSettings().isMySQLEnabled())
				try
				{
					this.wins = McInfected.getMySQLManager().getInt("McInfected", "Wins", uuid);
				}
				catch (SQLException e)
				{
					this.wins = 0;
				}
			else
				this.wins = McInfected.getFileManager().getPlayers().getInt(uuid + ".Wins");
		return this.wins;
	}
	
	/**
	 * @return the Score
	 */
	public int getScore() {
		if (this.score == -1)
			if (McInfected.getSettings().isMySQLEnabled())
				try
				{
					this.score = McInfected.getMySQLManager().getInt("McInfected", "Score", uuid);
				}
				catch (SQLException e)
				{
					this.score = 0;
				}
			else
				this.score = McInfected.getFileManager().getPlayers().getInt(uuid + ".Score");
		
		return this.score;
	}
	
	/**
	 * @return the Losses
	 */
	public int getLosses() {
		if (this.losses == -1)
			if (McInfected.getSettings().isMySQLEnabled())
				try
				{
					this.losses = McInfected.getMySQLManager().getInt("McInfected", "Losses", uuid);
				}
				catch (SQLException e)
				{
					this.losses = 0;
				}
			else
				this.losses = McInfected.getFileManager().getPlayers().getInt(uuid + ".Losses");
		return this.losses;
	}
	
	/**
	 * @return the Deaths
	 */
	public int getDeaths() {
		if (this.deaths == -1)
			if (McInfected.getSettings().isMySQLEnabled())
				try
				{
					this.deaths = McInfected.getMySQLManager().getInt("McInfected", "Deaths", uuid);
				}
				catch (SQLException e)
				{
					this.deaths = 0;
				}
			else
				this.deaths = McInfected.getFileManager().getPlayers().getInt(uuid + ".Deaths");
		return this.deaths;
	}
	
}
