
package com.sniperzciinema.mcinfected;

import java.util.List;


public class Settings {
	
	private FileManager	fileManager;
	
	public Settings()
	{
		this.fileManager = McInfected.getFileManager();
	}
	
	/**
	 * @return the number of players till Autostart will start
	 */
	public int getAutoStartPlayers() {
		return this.fileManager.getConfig().getInt("Automatic Start Players");
	}
	
	/**
	 * @return the infecting alpha percent
	 */
	public double getGameInfectingAlphaPercent() {
		return this.fileManager.getConfig().getDouble("Game.Infecting.Alpha Percent");
	}
	
	/**
	 * @return MySQL database
	 */
	public String getMySQLDatabase() {
		return this.fileManager.getConfig().getString("MySQL.Database");
	}
	
	/**
	 * @return MySQL host
	 */
	public String getMySQLHost() {
		return this.fileManager.getConfig().getString("MySQL.Host");
	}
	
	/**
	 * @return MySQL password
	 */
	public String getMySQLPassword() {
		return this.fileManager.getConfig().getString("MySQL.Password");
	}
	
	/**
	 * @return MySQL Port
	 */
	public String getMySQLPort() {
		return String.valueOf(this.fileManager.getConfig().getInt("MySQL.Port"));
	}
	
	/**
	 * @return MySQL Username
	 */
	public String getMySQLUsername() {
		return this.fileManager.getConfig().getString("MySQL.Username");
	}
	
	/**
	 * @return the games time limit
	 */
	public int getTimeGame() {
		return this.fileManager.getConfig().getInt("Time.Game");
	}
	
	/**
	 * @return the infecting time limit
	 */
	public int getTimeInfecting() {
		return this.fileManager.getConfig().getInt("Time.Infecting");
	}
	
	/**
	 * @return the pregame time limit
	 */
	public int getTimePreGame() {
		return this.fileManager.getConfig().getInt("Time.Pre-Game");
	}
	
	/**
	 * @return the voting time limit
	 */
	public int getTimeVoting() {
		return this.fileManager.getConfig().getInt("Time.Voting");
	}
	
	/**
	 * @return if disguise support is enabled
	 */
	public boolean isDisguiseSupportEnabled() {
		return this.fileManager.getConfig().getBoolean("Disguise Support");
	}
	
	/**
	 * @return If using MySQL
	 */
	public boolean isMySQLEnabled() {
		return this.fileManager.getConfig().getBoolean("MySQL.Enabled");
	}
	
	/**
	 * @return If downloading with updater
	 */
	public boolean isUpdaterDownloading() {
		return this.fileManager.getConfig().getBoolean("Updates.Download");
	}
	
	/**
	 * @return If using updater
	 */
	public boolean isUpdaterEnabled() {
		return this.fileManager.getConfig().getBoolean("Updates.Check");
	}
	
	public List<String> getAllowedCommands() {
		return this.fileManager.getConfig().getStringList("Allowed Commands");
	}
}
