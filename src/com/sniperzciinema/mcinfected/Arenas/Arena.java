
package com.sniperzciinema.mcinfected.Arenas;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import com.sniperzciinema.mcinfected.McInfected;
import com.sniperzciinema.mcinfected.IPlayers.IPlayer.Team;
import com.sniperzciinema.mcinfected.Utils.Coord;
import com.sniperzciinema.mcinfected.Utils.ItemUtil;


public class Arena {
	
	private String						creator;
	private ArrayList<Coord>	humanSpawns;
	private ItemStack					icon;
	
	private String						name;
	
	private int								votes;
	private ArrayList<Coord>	zombieSpawns;
	
	public Arena(String name)
	{
		this.name = name;
		
		// Create Spawn Lists
		this.humanSpawns = new ArrayList<Coord>();
		this.zombieSpawns = new ArrayList<Coord>();
		
		// Load Creators
		if (McInfected.getFileManager().getArenas().getString(getName() + ".Creator") != null)
			this.creator = McInfected.getFileManager().getArenas().getString(getName() + ".Creator");
		
		// Load icon
		if (McInfected.getFileManager().getArenas().getString(getName() + ".Icon") != null)
			this.icon = ItemUtil.getItemStack(McInfected.getFileManager().getArenas().getString(getName() + ".Icon"));
		
		// Load Spawns
		if (!McInfected.getFileManager().getArenas().getStringList(getName() + ".Spawns.Humans").isEmpty())
			for (String string : McInfected.getFileManager().getArenas().getStringList(getName() + ".Spawns.Humans"))
				this.humanSpawns.add(new Coord(string));
		
		if (!McInfected.getFileManager().getArenas().getStringList(getName() + ".Spawns.Zombies").isEmpty())
			for (String string : McInfected.getFileManager().getArenas().getStringList(getName() + ".Spawns.Zombies"))
				this.zombieSpawns.add(new Coord(string));
	}
	
	/**
	 * Add a human spawn
	 * 
	 * @param coord
	 */
	public void addHumanSpawn(Coord coord) {
		ArrayList<Coord> spawns = getHumanSpawns();
		spawns.add(coord);
		setHumanSpawns(spawns);
	}
	
	/**
	 * Add a spawn to both spawns
	 * 
	 * @param coord
	 */
	public void addSpawn(Coord coord) {
		addHumanSpawn(coord);
		addZombieSpawn(coord);
	}
	
	/**
	 * Add a zombie spawn
	 * 
	 * @param coord
	 */
	public void addZombieSpawn(Coord coord) {
		ArrayList<Coord> spawns = getZombieSpawns();
		spawns.add(coord);
		setZombieSpawns(spawns);
	}
	
	/**
	 * @return the creator
	 */
	public String getCreator() {
		return this.creator;
	}
	
	/**
	 * @return the human spawns
	 */
	public ArrayList<Coord> getHumanSpawns() {
		return this.humanSpawns;
	}
	
	/**
	 * @return the icon
	 */
	public ItemStack getIcon() {
		return this.icon;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return the all spawns
	 */
	public ArrayList<Coord> getSpawns() {
		ArrayList<Coord> spawns = this.zombieSpawns;
		spawns.addAll(this.humanSpawns);
		return spawns;
	}
	
	/**
	 * @param team
	 * @return the spawns for the team
	 */
	public ArrayList<Coord> getSpawns(Team team) {
		ArrayList<Coord> spawns = team == Team.Zombie ? this.zombieSpawns : this.humanSpawns;
		return spawns;
	}
	
	/**
	 * @return the votes
	 */
	public int getVotes() {
		return this.votes;
	}
	
	/**
	 * @return the zombie spawns
	 */
	public ArrayList<Coord> getZombieSpawns() {
		return this.zombieSpawns;
	}
	
	/**
	 * Reset the arena
	 * <ul>
	 * <li>Clear Votes</li>
	 * <li>Clear Npcs</li>
	 * </ul>
	 */
	public void reset() {
		this.votes = 0;
	}
	
	/**
	 * @param creator
	 *          the creator to set
	 */
	public void setCreator(String creator) {
		this.creator = creator;
		McInfected.getFileManager().getArenas().set(getName() + ".Creator", this.creator);
		McInfected.getFileManager().saveArenas();
	}
	
	/**
	 * @param humanSpawns
	 *          the humansSpawns to set
	 */
	public void setHumanSpawns(ArrayList<Coord> humanSpawns) {
		this.humanSpawns = humanSpawns;
		List<String> spawnList = new ArrayList<String>();
		
		for (Coord coord : humanSpawns)
			spawnList.add(coord.asString());
		
		McInfected.getFileManager().getArenas().set(getName() + ".Spawns.Humans", spawnList);
		McInfected.getFileManager().saveArenas();
	}
	
	/**
	 * @param icon
	 *          the icon to set
	 */
	public void setIcon(ItemStack icon) {
		this.icon = icon;
		McInfected.getFileManager().getArenas().set(getName() + ".Icon", ItemUtil.getItemStackToString(icon));
		McInfected.getFileManager().saveArenas();
	}
	
	/**
	 * @param name
	 *          the name to set
	 */
	public void setName(String name) {
		this.name = name;
		McInfected.getFileManager().getArenas().set(getName() + ".Creator", getCreator());
		McInfected.getFileManager().saveArenas();
		
	}
	
	/**
	 * @param votes
	 *          the votes to set
	 */
	public void setVotes(int votes) {
		this.votes = votes;
	}
	
	/**
	 * @param zombieSpawns
	 *          the zombieSpawns to set
	 */
	public void setZombieSpawns(ArrayList<Coord> zombieSpawns) {
		this.zombieSpawns = zombieSpawns;
		List<String> spawnList = new ArrayList<String>();
		
		for (Coord coord : zombieSpawns)
			spawnList.add(coord.asString());
		
		McInfected.getFileManager().getArenas().set(getName() + ".Spawns.Zombies", spawnList);
		McInfected.getFileManager().saveArenas();
	}
	
}
