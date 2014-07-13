
package com.sniperzciinema.mcinfected.Disguises;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.sniperzciinema.mcinfected.McInfected;


public class DisguiseManager {
	
	private Disguises	disguises;
	
	public DisguiseManager()
	{
		if (McInfected.getSettings().isDisguiseSupportEnabled())
			if (Bukkit.getServer().getPluginManager().getPlugin("DisguiseCraft") != null)
			{
				this.disguises = new DisguiseCraft();
				McInfected.getPlugin().getLogger().log(Level.INFO, "For Disguise Support we're using DisguiseCraft");
			}
			else if (Bukkit.getServer().getPluginManager().getPlugin("iDisguise") != null)
			{
				this.disguises = new IDisguise();
				McInfected.getPlugin().getLogger().log(Level.INFO, "For Disguise Support we're using iDisguise");
			}
			else if (Bukkit.getServer().getPluginManager().getPlugin("LibsDisguises") != null)
			{
				this.disguises = new LibsDisguises();
				McInfected.getPlugin().getLogger().log(Level.INFO, "For Disguise Support we're using LibsDisguise");
			}
			else
			{
				McInfected.getPlugin().getLogger().log(Level.WARNING, "No Valid Disguise Plugins found... disabling Disguise Support");
				McInfected.getFileManager().getConfig().set("Disguise Support", false);
			}
		if (this.disguises != null)
		{
			McInfected.getPlugin().getLogger().log(Level.INFO, "Filling Disguises.yml with possible disguises for " + this.disguises.getName());
			McInfected.getFileManager().getDisguises().set(this.disguises.getName(), this.disguises.disguiseList);
			McInfected.getFileManager().saveDisguises();
		}
	}
	
	/**
	 * Disguise the player
	 * 
	 * @param player
	 */
	public void disguise(Player player) {
		if (this.disguises != null)
			this.disguises.disguise(player);
	}
	
	/**
	 * @param player
	 * @return if the player is disguised
	 */
	public boolean isDisguised(Player player) {
		if (this.disguises != null)
			return this.disguises.isDisguised(player);
		return false;
	}
	
	/**
	 * Undisguise the player
	 * 
	 * @param player
	 */
	public void unDisguise(Player player) {
		if (this.disguises != null)
			this.disguises.unDisguise(player);
	}
}
