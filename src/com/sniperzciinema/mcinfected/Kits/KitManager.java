
package com.sniperzciinema.mcinfected.Kits;

import java.util.ArrayList;
import java.util.logging.Level;

import com.sniperzciinema.mcinfected.McInfected;
import com.sniperzciinema.mcinfected.IPlayers.IPlayer.Team;


public class KitManager {
	
	private ArrayList<Kit>	kits;
	
	public KitManager()
	{
		this.kits = new ArrayList<Kit>();
		loadKitsFromFile();
	}
	
	/**
	 * Create a kit
	 * 
	 * @param kitName
	 * @param team
	 */
	public void createKit(String kitName, Team team) {
		this.kits.add(new Kit(kitName, team));
	}
	
	/**
	 * @param kitName
	 * @return the kit
	 */
	public Kit getKit(String kitName) {
		for (Kit kit : this.kits)
			if (kit.getName().equalsIgnoreCase(kitName))
				return kit;
		return null;
	}
	
	/**
	 * @return kits
	 */
	public ArrayList<Kit> getKits() {
		return this.kits;
	}
	
	/**
	 * @param team
	 * @return kits that belong to the team
	 */
	public ArrayList<Kit> getKits(Team team) {
		ArrayList<Kit> teamKits = new ArrayList<Kit>();
		for (Kit kit : this.kits)
			if (kit.getTeam() == team)
				teamKits.add(kit);
		return teamKits;
	}
	
	/**
	 * Load kits from the default file
	 */
	public void loadKitsFromFile() {
		
		// Load Human Kits
		if (McInfected.getFileManager().getKits().getConfigurationSection("Human") != null)
			for (String kitName : McInfected.getFileManager().getKits().getConfigurationSection("Human").getKeys(true))
				if (!kitName.contains("."))
				{
					createKit(kitName, Team.Human);
					McInfected.getPlugin().getLogger().log(Level.INFO, "Loaded Human Kit: " + kitName);
				}
		// Load Infected Kits
		if (McInfected.getFileManager().getKits().getConfigurationSection("Infected") != null)
			for (String kitName : McInfected.getFileManager().getKits().getConfigurationSection("Infected").getKeys(true))
				if (!kitName.contains("."))
				{
					createKit(kitName, Team.Infected);
					McInfected.getPlugin().getLogger().log(Level.INFO, "Loaded Infected Kit: " + kitName);
				}
	}
	
	/**
	 * Remove a kit
	 * 
	 * @param kitName
	 */
	public void removeKit(String kitName) {
		this.kits.remove(getKit(kitName));
	}
	
}
