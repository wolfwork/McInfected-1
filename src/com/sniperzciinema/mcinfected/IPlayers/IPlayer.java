
package com.sniperzciinema.mcinfected.IPlayers;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.DisplaySlot;

import com.sniperzciinema.mcinfected.Lobby;
import com.sniperzciinema.mcinfected.Lobby.GameState;
import com.sniperzciinema.mcinfected.McInfected;
import com.sniperzciinema.mcinfected.Messanger.Messages;
import com.sniperzciinema.mcinfected.Arenas.Arena;
import com.sniperzciinema.mcinfected.Kits.Kit;


@SuppressWarnings("deprecation")
public class IPlayer {
	
	public static enum Team
	{
		Global, Human, Infected;
	};
	
	private ItemStack[]	armor;
	
	private float				exp;
	private GameMode		gamemode;
	private double			health;
	private Kit					humanKit;
	private int					hunger;
	private IMenus			iMenus;
	private ItemStack[]	inventory;
	private IScoreboard	iScoreboard;
	
	private int					level;
	private Lobby				lobby;
	private Location		origionalLocation;
	private Player			player;
	
	private Stats				stats;
	
	private IPlayer			lastDamager;
	
	private Team				team;
	
	private Arena				vote;
	
	private Kit					InfectedKit;
	
	public IPlayer(Lobby lobby, Player player)
	{
		this.lobby = lobby;
		this.player = player;
		this.team = Team.Human;
		
		this.inventory = this.player.getInventory().getContents();
		this.armor = this.player.getInventory().getArmorContents();
		this.level = this.player.getLevel();
		this.exp = this.player.getExp();
		this.origionalLocation = this.player.getLocation();
		this.gamemode = this.player.getGameMode();
		this.health = this.player.getHealth();
		this.hunger = this.player.getFoodLevel();
		this.iScoreboard = new IScoreboard(this);
		this.iMenus = new IMenus(this);
		
		this.stats = new Stats(player);
		
		player.setLevel(0);
		player.setExp(0.99F);
	}
	
	/**
	 * Add potion effects
	 */
	public void addPotionEffects() {
		for (PotionEffect pe : getKit(this.team).getPotions())
			this.player.addPotionEffect(pe);
	}
	
	/**
	 * @param team
	 * @return a random kit that the player has permissions for
	 */
	public Kit chooseRandomKit(Team team) {
		ArrayList<Kit> kits = new ArrayList<Kit>();
		for (Kit kit : McInfected.getKitManager().getKits())
			if (this.player.hasPermission("McInfected.Kit." + team.toString() + "." + kit.getName()) || this.player.hasPermission("McInfected.Kit." + team.toString() + ".*"))
				kits.add(kit);
		
		Random r = new Random();
		return kits.get(r.nextInt(kits.size()));
	}
	
	/**
	 * Clear the players equipment
	 */
	public void clearEquipment() {
		this.player.getInventory().clear();
		this.player.getInventory().setArmorContents(null);
		this.player.updateInventory();
		clearPotions();
	}
	
	/**
	 * Clear the players potions
	 */
	public void clearPotions() {
		for (PotionEffect pe : this.player.getActivePotionEffects())
			this.player.removePotionEffect(pe.getType());
	}
	
	/**
	 * this player dies
	 */
	public void die() {
		stats.setDeaths(stats.getDeaths() - 1);
		
		setLastDamager(null);
		
		if (this.team == Team.Human)
			infect();
		else
			respawn();
		
		equip();
		
		for (IPlayer iPlayer : McInfected.getLobby().getIPlayers())
			iPlayer.getiScoreboard().update();
	}
	
	public void disguise() {
		McInfected.getDisguiseManager().disguise(this.player);
	}
	
	/**
	 * Equip the player's equipment
	 */
	public void equip() {
		
		// Equip Inventory
		for (ItemStack stack : getKit(team).getInventory())
			this.player.getInventory().addItem(stack);
		
		// Equip armor
		this.player.getInventory().setHelmet(getKit(team).getHelmet());
		this.player.getInventory().setChestplate(getKit(team).getChestplate());
		this.player.getInventory().setLeggings(getKit(team).getLeggings());
		this.player.getInventory().setBoots(getKit(team).getBoots());
		
		this.player.updateInventory();
		
		addPotionEffects();
		
	}
	
	/**
	 * @return the armor
	 */
	public ItemStack[] getArmor() {
		return this.armor;
	}
	
	/**
	 * @return the exp
	 */
	public double getExp() {
		return this.exp;
	}
	
	/**
	 * @return the gamemode
	 */
	public GameMode getGamemode() {
		return this.gamemode;
	}
	
	/**
	 * @return the health
	 */
	public double getHealth() {
		return this.health;
	}
	
	/**
	 * @return the humanKit
	 */
	public Kit getHumanKit() {
		return this.humanKit;
	}
	
	/**
	 * @return the hunger
	 */
	public int getHunger() {
		return this.hunger;
	}
	
	/**
	 * @return the iMenus
	 */
	public IMenus getiMenus() {
		return this.iMenus;
	}
	
	/**
	 * @return the inventory
	 */
	public ItemStack[] getInventory() {
		return this.inventory;
	}
	
	/**
	 * @return the iScoreboard
	 */
	public IScoreboard getiScoreboard() {
		return this.iScoreboard;
	}
	
	/**
	 * @param team
	 * @return the teams kit
	 */
	public Kit getKit(Team team) {
		return team == Team.Human ? this.humanKit : this.InfectedKit;
	}
	
	/**
	 * @return the level
	 */
	public int getLevel() {
		return this.level;
	}
	
	/**
	 * @return the lobby
	 */
	public Lobby getLobby() {
		return this.lobby;
	}
	
	/**
	 * @return the origionalLocation
	 */
	public Location getOrigionalLocation() {
		return this.origionalLocation;
	}
	
	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return this.player;
	}
	
	/**
	 * @return the team
	 */
	public Team getTeam() {
		return this.team;
	}
	
	/**
	 * @return the vote
	 */
	public Arena getVote() {
		return this.vote;
	}
	
	/**
	 * @return the InfectedKit
	 */
	public Kit getInfectedKit() {
		return this.InfectedKit;
	}
	
	/**
	 * Heal the players food and health
	 */
	public void heal() {
		this.player.setHealth(20.0);
		this.player.setFoodLevel(20);
	}
	
	/**
	 * Infect the player
	 * <ul>
	 * <li>Set Team to Infected</li>
	 * <li>Change Equipment</li>
	 * <li>Disguise</li>
	 * </ul>
	 */
	public void infect() {
		setTeam(Team.Infected);
		removeEquipment(Team.Human);
		equip();
		disguise();
	}
	
	/**
	 * this player gets a kill
	 */
	public void kill() {
		getStats().setKills(getStats().getKills() + 1);
	}
	
	/**
	 * Leave the game
	 * <ul>
	 * <li>Stops game if needed</li>
	 * <li>Resets player</li>
	 * </ul>
	 */
	public void leave() {
		
		// If theres only 1 player left end the game
		if (this.lobby.getIPlayers().size() <= 1)
			switch (this.lobby.getGameState())
			{
			// End the game if it's not a game over or in a lobby
				case GameOver:
					break;
				case InLobby:
					break;
				default:
					McInfected.getMessanger().broadcast(McInfected.getMessanger().getMessage(true, Messages.Error__Game__Not_Enough_Players));
					this.lobby.getTimers().startEndGame();
					break;
			}
		
		// If theres no Infecteds left, choose a new one
		if ((McInfected.getLobby().getGameState() == GameState.Game) && this.lobby.getInfecteds().isEmpty())
			this.lobby.getTimers().chooseAlphas(1);
		
		// Remove this players vote
		if (this.vote != null)
			this.vote.setVotes(this.vote.getVotes() - 1);
		
		this.player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		clearEquipment();
		restore();
		
	}
	
	/**
	 * Remove all items that the kit selected has
	 * 
	 * @param team
	 */
	public void removeEquipment(Team team) {
		// Remove all inventory items
		for (ItemStack item : getKit(team).getInventory())
			this.player.getInventory().remove(item.getType());
		
		// Remove any armor items they may have taken off
		for (ItemStack item : this.player.getInventory().getContents())
			if (item != null)
				if (item.getType() == getKit(team).getHelmet().getType())
					item.setType(Material.AIR);
				else if (item.getType() == getKit(team).getChestplate().getType())
					item.setType(Material.AIR);
				else if (item.getType() == getKit(team).getLeggings().getType())
					item.setType(Material.AIR);
				else if (item.getType() == getKit(team).getBoots().getType())
					item.setType(Material.AIR);
		
		// Check armor slots
		if (this.player.getInventory().getHelmet() != null)
			if (this.player.getInventory().getHelmet().getType() == getKit(team).getHelmet().getType())
				this.player.getInventory().getHelmet().setType(Material.AIR);
		
		if (this.player.getInventory().getChestplate() != null)
			if (this.player.getInventory().getChestplate().getType() == getKit(team).getChestplate().getType())
				this.player.getInventory().getChestplate().setType(Material.AIR);
		
		if (this.player.getInventory().getLeggings() != null)
			if (this.player.getInventory().getLeggings().getType() == getKit(team).getLeggings().getType())
				this.player.getInventory().getLeggings().setType(Material.AIR);
		
		if (this.player.getInventory().getBoots() != null)
			if (this.player.getInventory().getBoots().getType() == getKit(team).getBoots().getType())
				this.player.getInventory().getBoots().setType(Material.AIR);
	}
	
	/**
	 * Reset the player
	 * <ul>
	 * <li>Clear Vote</li>
	 * </ul>
	 */
	public void reset() {
		this.vote = null;
		clearEquipment();
		unDisguise();
	}
	
	/**
	 * Respawn the player
	 * <ul>
	 * <li>Clear Equipment</li>
	 * <li>Re-Equip</li>
	 * <li>Disguise</li>
	 * <li>Teleport to random spawn</li>
	 * <ul>
	 */
	public void respawn() {
		
		if (getKit(team) != null)
		{
			removeEquipment(getTeam());
			equip();
		}
		heal();
		this.lobby.teleportToArena(this, this.lobby.getPlayingIn());
	}
	
	/**
	 * Restore the players things
	 */
	public void restore() {
		this.player.teleport(this.origionalLocation);
		this.player.setLevel(this.level);
		this.player.setExp(this.exp);
		this.player.getInventory().setContents(this.inventory);
		this.player.getInventory().setArmorContents(this.armor);
		this.player.setGameMode(this.gamemode);
	}
	
	/**
	 * @param armor
	 *          the armor to set
	 */
	public void setArmor(ItemStack[] armor) {
		this.armor = armor;
	}
	
	/**
	 * @param exp
	 *          the exp to set
	 */
	public void setExp(float exp) {
		this.exp = exp;
	}
	
	/**
	 * @param gamemode
	 *          the gamemode to set
	 */
	public void setGamemode(GameMode gamemode) {
		this.gamemode = gamemode;
	}
	
	/**
	 * @param health
	 *          the health to set
	 */
	public void setHealth(double health) {
		this.health = health;
	}
	
	/**
	 * @param humanKit
	 *          the humanKit to set
	 */
	public void setHumanKit(Kit humanKit) {
		this.humanKit = humanKit;
	}
	
	/**
	 * @param hunger
	 *          the hunger to set
	 */
	public void setHunger(int hunger) {
		this.hunger = hunger;
	}
	
	/**
	 * @param iMenus
	 *          the iMenus to set
	 */
	public void setiMenus(IMenus iMenus) {
		this.iMenus = iMenus;
	}
	
	/**
	 * @param inventory
	 *          the inventory to set
	 */
	public void setInventory(ItemStack[] inventory) {
		this.inventory = inventory;
	}
	
	/**
	 * @param iScoreboard
	 *          the iScoreboard to set
	 */
	public void setiScoreboard(IScoreboard iScoreboard) {
		this.iScoreboard = iScoreboard;
	}
	
	/**
	 * Set a kit
	 * 
	 * @param team
	 * @param kit
	 */
	public void setKit(Team team, Kit kit) {
		if (team == Team.Human)
			setHumanKit(kit);
		else
			setInfectedKit(kit);
	}
	
	/**
	 * @param level
	 *          the level to set
	 */
	public void setLevel(int level) {
		this.level = level;
	}
	
	/**
	 * @param lobby
	 *          the lobby to set
	 */
	public void setLobby(Lobby lobby) {
		this.lobby = lobby;
	}
	
	/**
	 * @param origionalLocation
	 *          the origionalLocation to set
	 */
	public void setOrigionalLocation(Location origionalLocation) {
		this.origionalLocation = origionalLocation;
	}
	
	/**
	 * @param player
	 *          the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}
	
	/**
	 * @param team
	 *          the team to set
	 */
	public void setTeam(Team team) {
		this.team = team;
	}
	
	/**
	 * @param vote
	 *          the vote to set
	 */
	public void setVote(Arena vote) {
		this.vote = vote;
	}
	
	/**
	 * @param InfectedKit
	 *          the InfectedKit to set
	 */
	public void setInfectedKit(Kit InfectedKit) {
		this.InfectedKit = InfectedKit;
	}
	
	/**
	 * Undisguise the player
	 */
	public void unDisguise() {
		McInfected.getDisguiseManager().unDisguise(this.player);
	}
	
	/**
	 * @return the lastDamager
	 */
	public IPlayer getLastDamager() {
		return lastDamager;
	}
	
	/**
	 * @param lastDamager
	 *          the lastDamager to set
	 */
	public void setLastDamager(IPlayer lastDamager) {
		this.lastDamager = lastDamager;
	}
	
	/**
	 * @return the stats
	 */
	public Stats getStats() {
		return stats;
	}
	
	/**
	 * @param stats
	 *          the stats to set
	 */
	public void setStats(Stats stats) {
		this.stats = stats;
	}
	
}
