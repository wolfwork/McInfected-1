
package com.sniperzciinema.mcinfected;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;


public class FileManager {
	
	// Set up all the needed things for files
	private YamlConfiguration	arenasConfig, messagesConfig, shopsConfig, playersConfig, kitsConfig, commandSetsConfig, disguisesConfig;
	private File							arenasFile, messagesFile, shopsFile, playersFile, kitsFile, commandSetsFile, disguisesFile;
	
	private Plugin						plugin;
	
	public FileManager()
	{
		this.plugin = McInfected.getPlugin();
		
		copyDefaults();
		saveAll();
		
	}
	
	public void copyDefaults() {
		getConfig().options().copyDefaults(true);
		getArenas().options().copyDefaults(true);
		getMessages().options().copyDefaults(true);
		getShops().options().copyDefaults(true);
		getPlayers().options().copyDefaults(true);
		getKits().options().copyDefaults(true);
		getCommandSets().options().copyDefaults(true);
		getDisguises().options().copyDefaults(true);
	}
	
	/**
	 * @return Arenas.yml
	 */
	public FileConfiguration getArenas() {
		if (this.arenasConfig == null)
			reloadArenas();
		return this.arenasConfig;
	}
	
	/**
	 * @return CommandSets.yml
	 */
	public FileConfiguration getCommandSets() {
		if (this.commandSetsConfig == null)
			reloadCommandSets();
		return this.commandSetsConfig;
	}
	
	/**
	 * @return Config.yml
	 */
	public FileConfiguration getConfig() {
		return this.plugin.getConfig();
	}
	
	/**
	 * @return Disguise.yml
	 */
	public FileConfiguration getDisguises() {
		if (this.disguisesConfig == null)
			reloadDisguises();
		return this.disguisesConfig;
	}
	
	/**
	 * @return Kits.yml
	 */
	public FileConfiguration getKits() {
		if (this.kitsConfig == null)
			reloadKits();
		return this.kitsConfig;
	}
	
	/**
	 * @return Messages.yml
	 */
	public FileConfiguration getMessages() {
		if (this.messagesConfig == null)
			reloadMessages();
		return this.messagesConfig;
	}
	
	/**
	 * @return Players.yml
	 */
	public FileConfiguration getPlayers() {
		if (this.playersConfig == null)
			reloadPlayers();
		return this.playersConfig;
	}
	
	/**
	 * @return Shops.yml
	 */
	public FileConfiguration getShops() {
		if (this.shopsConfig == null)
			reloadShops();
		return this.shopsConfig;
	}
	
	/**
	 * Reload all files
	 */
	public void reloadAll() {
		reloadConfig();
		reloadArenas();
		reloadMessages();
		reloadShops();
		reloadPlayers();
		reloadKits();
		reloadCommandSets();
		reloadDisguises();
	}
	
	/**
	 * Reload Arenas.yml
	 */
	public void reloadArenas() {
		if (this.arenasFile == null)
			this.arenasFile = new File(this.plugin.getDataFolder(), "Arenas.yml");
		this.arenasConfig = YamlConfiguration.loadConfiguration(this.arenasFile);
		// Look for defaults in the jar
		InputStream defConfigStream = this.plugin.getResource("Arenas.yml");
		if (defConfigStream != null)
		{
			@SuppressWarnings("deprecation")
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			if (!this.arenasFile.exists() || (this.arenasFile.length() == 0))
				this.arenasConfig.setDefaults(defConfig);
		}
	}
	
	/**
	 * Reload CommandSets.yml
	 */
	public void reloadCommandSets() {
		if (this.commandSetsFile == null)
			this.commandSetsFile = new File(this.plugin.getDataFolder(), "CommandSets.yml");
		this.commandSetsConfig = YamlConfiguration.loadConfiguration(this.commandSetsFile);
		// Look for defaults in the jar
		InputStream defConfigStream = this.plugin.getResource("CommandSets.yml");
		if (defConfigStream != null)
		{
			@SuppressWarnings("deprecation")
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			if (!this.commandSetsFile.exists() || (this.commandSetsFile.length() == 0))
				this.commandSetsConfig.setDefaults(defConfig);
		}
	}
	
	/**
	 * Reload Config.yml
	 */
	public void reloadConfig() {
		this.plugin.reloadConfig();
	}
	
	/**
	 * Reload Disguises.yml
	 */
	public void reloadDisguises() {
		if (this.disguisesFile == null)
			this.disguisesFile = new File(this.plugin.getDataFolder(), "Disguises.yml");
		this.disguisesConfig = YamlConfiguration.loadConfiguration(this.disguisesFile);
		
		// Look for defaults in the jar
		InputStream defConfigStream = this.plugin.getResource("Disguises.yml");
		if (defConfigStream != null)
		{
			@SuppressWarnings("deprecation")
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			this.disguisesConfig.setDefaults(defConfig);
		}
	}
	
	/**
	 * Reload Kits.yml
	 */
	public void reloadKits() {
		if (this.kitsFile == null)
			this.kitsFile = new File(this.plugin.getDataFolder(), "Kits.yml");
		this.kitsConfig = YamlConfiguration.loadConfiguration(this.kitsFile);
		// Look for defaults in the jar
		InputStream defConfigStream = this.plugin.getResource("Kits.yml");
		if (defConfigStream != null)
		{
			@SuppressWarnings("deprecation")
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			if (!this.kitsFile.exists() || (this.kitsFile.length() == 0))
				this.kitsConfig.setDefaults(defConfig);
		}
	}
	
	/**
	 * Reload Messages.yml
	 */
	public void reloadMessages() {
		if (this.messagesFile == null)
			this.messagesFile = new File(this.plugin.getDataFolder(), "Messages.yml");
		this.messagesConfig = YamlConfiguration.loadConfiguration(this.messagesFile);
		// Look for defaults in the jar
		InputStream defConfigStream = this.plugin.getResource("Messages.yml");
		if (defConfigStream != null)
		{
			@SuppressWarnings("deprecation")
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			this.messagesConfig.setDefaults(defConfig);
		}
	}
	
	/**
	 * Reload Players.yml
	 */
	public void reloadPlayers() {
		if (this.playersFile == null)
			this.playersFile = new File(this.plugin.getDataFolder(), "Players.yml");
		this.playersConfig = YamlConfiguration.loadConfiguration(this.playersFile);
		// Look for defaults in the jar
		InputStream defConfigStream = this.plugin.getResource("Players.yml");
		if (defConfigStream != null)
		{
			@SuppressWarnings("deprecation")
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			if (!this.playersFile.exists() || (this.playersFile.length() == 0))
				this.playersConfig.setDefaults(defConfig);
		}
	}
	
	/**
	 * Reload Shops.yml
	 */
	public void reloadShops() {
		if (this.shopsFile == null)
			this.shopsFile = new File(this.plugin.getDataFolder(), "Shops.yml");
		this.shopsConfig = YamlConfiguration.loadConfiguration(this.shopsFile);
		// Look for defaults in the jar
		InputStream defConfigStream = this.plugin.getResource("Shops.yml");
		if (defConfigStream != null)
		{
			@SuppressWarnings("deprecation")
			YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
			if (!this.shopsFile.exists() || (this.shopsFile.length() == 0))
				this.shopsConfig.setDefaults(defConfig);
		}
	}
	
	/**
	 * Save all files
	 */
	public void saveAll() {
		saveConfig();
		saveArenas();
		saveMessages();
		saveShops();
		savePlayers();
		saveKits();
		saveCommandSets();
		saveDisguises();
	}
	
	/**
	 * Save Arenas.yml
	 */
	public void saveArenas() {
		if ((this.arenasConfig == null) || (this.arenasFile == null))
			return;
		try
		{
			getArenas().save(this.arenasFile);
		}
		catch (IOException ex)
		{
			Bukkit.getLogger().log(Level.SEVERE, "Could not save config " + this.arenasFile, ex);
		}
	}
	
	/**
	 * Save CommandSets.yml
	 */
	public void saveCommandSets() {
		if ((this.commandSetsConfig == null) || (this.commandSetsFile == null))
			return;
		try
		{
			getCommandSets().save(this.commandSetsFile);
		}
		catch (IOException ex)
		{
			Bukkit.getLogger().log(Level.SEVERE, "Could not save config " + this.commandSetsFile, ex);
		}
	}
	
	/**
	 * Save Config.yml
	 */
	public void saveConfig() {
		this.plugin.saveConfig();
	}
	
	/**
	 * Save Disguises.yml
	 */
	public void saveDisguises() {
		if ((this.disguisesConfig == null) || (this.disguisesFile == null))
			return;
		try
		{
			getDisguises().save(this.disguisesFile);
		}
		catch (IOException ex)
		{
			Bukkit.getLogger().log(Level.SEVERE, "Could not save config " + this.disguisesFile, ex);
		}
	}
	
	/**
	 * Save Kits.yml
	 */
	public void saveKits() {
		if ((this.kitsConfig == null) || (this.kitsFile == null))
			return;
		try
		{
			getKits().save(this.kitsFile);
		}
		catch (IOException ex)
		{
			Bukkit.getLogger().log(Level.SEVERE, "Could not save config " + this.kitsFile, ex);
		}
	}
	
	/**
	 * Save Messages.yml
	 */
	public void saveMessages() {
		if ((this.messagesConfig == null) || (this.messagesFile == null))
			return;
		try
		{
			getMessages().save(this.messagesFile);
		}
		catch (IOException ex)
		{
			Bukkit.getLogger().log(Level.SEVERE, "Could not save config " + this.messagesFile, ex);
		}
	}
	
	/**
	 * Save Players.yml
	 */
	public void savePlayers() {
		if ((this.playersConfig == null) || (this.playersFile == null))
			return;
		try
		{
			getPlayers().save(this.playersFile);
		}
		catch (IOException ex)
		{
			Bukkit.getLogger().log(Level.SEVERE, "Could not save config " + this.playersFile, ex);
		}
	}
	
	/**
	 * Save Shops.yml
	 */
	public void saveShops() {
		if ((this.shopsConfig == null) || (this.shopsFile == null))
			return;
		try
		{
			getShops().save(this.shopsFile);
		}
		catch (IOException ex)
		{
			Bukkit.getLogger().log(Level.SEVERE, "Could not save config " + this.shopsFile, ex);
		}
	}
	
}
