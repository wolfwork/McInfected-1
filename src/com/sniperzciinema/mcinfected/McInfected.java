
package com.sniperzciinema.mcinfected;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import com.sniperzciinema.mcinfected.Messanger.Messages;
import com.sniperzciinema.mcinfected.Command.CHandler;
import com.sniperzciinema.mcinfected.Disguises.DisguiseManager;
import com.sniperzciinema.mcinfected.IPlayers.IPlayer;
import com.sniperzciinema.mcinfected.IPlayers.IScoreboard;
import com.sniperzciinema.mcinfected.Kits.KitManager;
import com.sniperzciinema.mcinfected.Listeners.Combat;
import com.sniperzciinema.mcinfected.Listeners.CommandSetSigns;
import com.sniperzciinema.mcinfected.Listeners.CommandSigns;
import com.sniperzciinema.mcinfected.Listeners.FactionsEvents;
import com.sniperzciinema.mcinfected.Listeners.MiscListeners;
import com.sniperzciinema.mcinfected.Listeners.mcMMOEvents;
import com.sniperzciinema.mcinfected.MySQL.MySQLManager;
import com.sniperzciinema.mcinfected.Utils.Metrics;
import com.sniperzciinema.mcinfected.Utils.Updater;


public class McInfected extends JavaPlugin {
	
	private static CHandler					cHandler;
	
	private static DisguiseManager	disguiseManager;
	private static Economy					economy;
	private static FileManager			fileManager;
	private static KitManager				kitManager;
	private static Lobby						lobby;
	private static Plugin						me;
	private static Messanger				messanger;
	
	private static MySQLManager			mySQLManager;
	private static Settings					settings;
	private static Updater					updater;
	
	/**
	 * Create new instances of:
	 * <ul>
	 * <li>Lobby</li>
	 * <li>KitManager</li>
	 * <li>FileManager</li>
	 * <li>DisguiseManager</li>
	 * </ul>
	 */
	public static void reload() {
		lobby = new Lobby();
		kitManager = new KitManager();
		fileManager = new FileManager();
		disguiseManager = new DisguiseManager();
	}
	
	/**
	 * @return the command handler
	 */
	public static CHandler getCHandler() {
		return McInfected.cHandler;
	}
	
	/**
	 * @return the disguiseManager
	 */
	public static DisguiseManager getDisguiseManager() {
		return McInfected.disguiseManager;
	}
	
	public static Economy getEconomy() {
		return McInfected.economy;
	}
	
	/**
	 * @return the fileManager
	 */
	public static FileManager getFileManager() {
		return McInfected.fileManager;
	}
	
	/**
	 * @return the kitManager
	 */
	public static KitManager getKitManager() {
		return McInfected.kitManager;
	}
	
	/**
	 * @return the lobby
	 */
	public static Lobby getLobby() {
		return McInfected.lobby;
	}
	
	/**
	 * @return the messanger
	 */
	public static Messanger getMessanger() {
		return McInfected.messanger;
	}
	
	/**
	 * @return the MySQLManager
	 */
	public static MySQLManager getMySQLManager() {
		return McInfected.mySQLManager;
	}
	
	/**
	 * @return plugin
	 */
	public static Plugin getPlugin() {
		return McInfected.me;
	}
	
	/**
	 * @return the settings
	 */
	public static Settings getSettings() {
		return McInfected.settings;
	}
	
	public static Updater getUpdater() {
		return McInfected.updater;
	}
	
	/**
	 * Check for any sort of update
	 * 
	 * @param id
	 */
	void checkForUpdates(int id) {
		
		if (McInfected.settings.isUpdaterDownloading())
			McInfected.updater = new Updater(this, id, getFile(), Updater.UpdateType.DEFAULT, true);
		else
			McInfected.updater = new Updater(this, id, getFile(), Updater.UpdateType.NO_DOWNLOAD, true);
		
		McInfected.updater.getResult();
	}
	
	public void getVaultEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		
		if (economyProvider != null)
			McInfected.economy = economyProvider.getProvider();
	}
	
	@Override
	public void onDisable() {
		for (IPlayer players : McInfected.lobby.getIPlayers())
			players.leave();
		
		getLobby().getTimers().stop();
		
		if (McInfected.settings.isMySQLEnabled())
		{
			if (McInfected.mySQLManager.hasOpenStatement())
				try
				{
					McInfected.mySQLManager.closeStatement();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			if (McInfected.mySQLManager.hasOpenConnection())
				try
				{
					McInfected.mySQLManager.closeConnection();
				}
				catch (SQLException e)
				{
					e.printStackTrace();
				}
		}
	}
	
	@Override
	public void onEnable() {
		
		McInfected.me = this;
		
		McInfected.fileManager = new FileManager();
		McInfected.messanger = new Messanger();
		
		this.getLogger().log(Level.WARNING, ChatColor.stripColor(McInfected.messanger.getMessage(false, Messages.Format__Header, "<title>", "McInfected")));
		
		McInfected.settings = new Settings();
		McInfected.disguiseManager = new DisguiseManager();
		McInfected.kitManager = new KitManager();
		McInfected.lobby = new Lobby();
		McInfected.cHandler = new CHandler();
		
		// Create Commands
		getCommand("Infected").setExecutor(McInfected.cHandler);
		getCommand("Infected").setTabCompleter(McInfected.cHandler);
		
		// Create Listeners
		getServer().getPluginManager().registerEvents(new Combat(), this);
		getServer().getPluginManager().registerEvents(new MiscListeners(), this);
		getServer().getPluginManager().registerEvents(new IScoreboard(), this);
		getServer().getPluginManager().registerEvents(new CommandSetSigns(), this);
		getServer().getPluginManager().registerEvents(new CommandSigns(), this);
		
		// Check for other plugins support
		if (Bukkit.getServer().getPluginManager().getPlugin("Factions") != null)
		{
			this.getLogger().log(Level.INFO, "Enabling Factions support");
			getServer().getPluginManager().registerEvents(new FactionsEvents(), this);
		}
		if (Bukkit.getServer().getPluginManager().getPlugin("mcMMO") != null)
		{
			this.getLogger().log(Level.INFO, "Enabling mcMMO support");
			getServer().getPluginManager().registerEvents(new mcMMOEvents(), this);
		}
		
		if (Bukkit.getServer().getPluginManager().getPlugin("Vault") != null)
		{
			this.getLogger().log(Level.INFO, "Enabling Vault Economy");
			getVaultEconomy();
		}
		
		// Start Metrics
		this.getLogger().log(Level.INFO, "Starting Metrics");
		startMetrics();
		
		// Start Updater
		if (McInfected.settings.isUpdaterEnabled())
		{
			this.getLogger().log(Level.INFO, "Checking for updates");
			checkForUpdates(44622);
		}
		// Check for MySQL
		if (McInfected.settings.isMySQLEnabled())
		{
			this.getLogger().log(Level.INFO, "Connecting to MySQL Database");
			McInfected.mySQLManager = new MySQLManager(McInfected.me, McInfected.settings.getMySQLHost(), McInfected.settings.getMySQLPort(),
					McInfected.settings.getMySQLDatabase(), McInfected.settings.getMySQLUsername(), McInfected.settings.getMySQLPassword());
			try
			{
				McInfected.mySQLManager.checkIfTableExists("Infected", "Score INT(10), Kills INT(10), Deaths INT(10), Time INT(10), KillStreak INT(10), Wins INT(10), Losses INT(10)");
			}
			catch (SQLException e)
			{
				this.getLogger().log(Level.SEVERE, "Unable to connect to the MySQL Database");
			}
		}
		this.getLogger().log(Level.WARNING, ChatColor.stripColor(McInfected.messanger.getMessage(false, Messages.Format__Line)));
		
	}
	
	void startMetrics() {
		try
		{
			Metrics metrics = new Metrics(this);
			metrics.start();
		}
		catch (IOException e)
		{
			this.getLogger().log(Level.SEVERE, "Unable to start Metrics");
			
		}
	}
	
}
