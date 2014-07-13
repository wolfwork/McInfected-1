
package com.sniperzciinema.mcinfected.IPlayers;

import java.sql.SQLException;
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
	private int					kills	= -1, deaths = -1, killStreak = -1;
	private int					score	= -1;
	private int					time	= -1;
	private int					wins	= -1, losses = -1;
	private int					level;
	private Lobby				lobby;
	private Location		origionalLocation;
	private Player			player;
	
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
		setDeaths(getDeaths() - 1);
		
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
	 * @return the Deaths
	 */
	public int getDeaths() {
		if (this.deaths == -1)
			if (McInfected.getSettings().isMySQLEnabled())
				try
				{
					this.deaths = McInfected.getMySQLManager().getInt("McInfected", "Deaths", this.player.getUniqueId());
				}
				catch (SQLException e)
				{
					this.deaths = 0;
				}
			else
				this.deaths = McInfected.getFileManager().getPlayers().getInt(this.player.getUniqueId() + ".Deaths");
		return this.deaths;
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
	 * @return the Kills
	 */
	public int getKills() {
		if (this.kills == -1)
			if (McInfected.getSettings().isMySQLEnabled())
				try
				{
					this.kills = McInfected.getMySQLManager().getInt("McInfected", "Kills", this.player.getUniqueId());
				}
				catch (SQLException e)
				{
					this.kills = 0;
				}
			else
				this.kills = McInfected.getFileManager().getPlayers().getInt(this.player.getUniqueId() + ".Kills");
		
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
					this.killStreak = McInfected.getMySQLManager().getInt("McInfected", "KillStreak", this.player.getUniqueId());
				}
				catch (SQLException e)
				{
					this.killStreak = 0;
				}
			else
				this.killStreak = McInfected.getFileManager().getPlayers().getInt(this.player.getUniqueId() + ".KillStreak");
		return this.killStreak;
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
	 * @return the Losses
	 */
	public int getLosses() {
		if (this.losses == -1)
			if (McInfected.getSettings().isMySQLEnabled())
				try
				{
					this.losses = McInfected.getMySQLManager().getInt("McInfected", "Losses", this.player.getUniqueId());
				}
				catch (SQLException e)
				{
					this.losses = 0;
				}
			else
				this.losses = McInfected.getFileManager().getPlayers().getInt(this.player.getUniqueId() + ".Losses");
		return this.losses;
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
	 * @return the Score
	 */
	public int getScore() {
		if (this.score == -1)
			if (McInfected.getSettings().isMySQLEnabled())
				try
				{
					this.score = McInfected.getMySQLManager().getInt("McInfected", "Score", this.player.getUniqueId());
				}
				catch (SQLException e)
				{
					this.score = 0;
				}
			else
				this.score = McInfected.getFileManager().getPlayers().getInt(this.player.getUniqueId() + ".Score");
		
		return this.score;
	}
	
	/**
	 * @return the team
	 */
	public Team getTeam() {
		return this.team;
	}
	
	/**
	 * @return the Time
	 */
	public long getTime() {
		if (this.time == -1)
			if (McInfected.getSettings().isMySQLEnabled())
				try
				{
					this.time = McInfected.getMySQLManager().getInt("McInfected", "Time", this.player.getUniqueId());
				}
				catch (SQLException e)
				{
					this.time = 0;
				}
			else
				this.time = McInfected.getFileManager().getPlayers().getInt(this.player.getUniqueId() + ".Time");
		return this.time;
	}
	
	/**
	 * @return the vote
	 */
	public Arena getVote() {
		return this.vote;
	}
	
	/**
	 * @return the Wins
	 */
	public int getWins() {
		if (this.wins == -1)
			if (McInfected.getSettings().isMySQLEnabled())
				try
				{
					this.wins = McInfected.getMySQLManager().getInt("McInfected", "Wins", this.player.getUniqueId());
				}
				catch (SQLException e)
				{
					this.wins = 0;
				}
			else
				this.wins = McInfected.getFileManager().getPlayers().getInt(this.player.getUniqueId() + ".Wins");
		return this.wins;
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
		setKills(this.kills + 1);
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
	 * @param Deaths
	 *          the Deaths to set
	 */
	public void setDeaths(int Deaths) {
		this.deaths = Deaths;
		if (McInfected.getSettings().isMySQLEnabled())
			try
			{
				McInfected.getMySQLManager().update("McInfected", "Deaths", this.deaths, this.player.getUniqueId());
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		else
			McInfected.getFileManager().getPlayers().set(this.player.getUniqueId() + ".Deaths", this.deaths);
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
	 * @param Kills
	 *          the Kills to set
	 */
	public void setKills(int kills) {
		this.kills = kills;
		
		if (McInfected.getSettings().isMySQLEnabled())
			try
			{
				McInfected.getMySQLManager().update("McInfected", "Kills", this.kills, this.player.getUniqueId());
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		else
			McInfected.getFileManager().getPlayers().set(this.player.getUniqueId() + ".Kills", this.kills);
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
				McInfected.getMySQLManager().update("McInfected", "KillStreak", this.killStreak, this.player.getUniqueId());
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		else
			McInfected.getFileManager().getPlayers().set(this.player.getUniqueId() + ".KillStreak", this.killStreak);
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
	 * @param Losses
	 *          the Losses to set
	 */
	public void setLosses(int Losses) {
		this.losses = Losses;
		if (McInfected.getSettings().isMySQLEnabled())
			try
			{
				McInfected.getMySQLManager().update("McInfected", "Losses", this.losses, this.player.getUniqueId());
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		else
			McInfected.getFileManager().getPlayers().set(this.player.getUniqueId() + ".Losses", this.losses);
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
	 * @param Score
	 *          the Score to set
	 */
	public void setScore(int score) {
		this.score = score;
		
		if (McInfected.getSettings().isMySQLEnabled())
			try
			{
				McInfected.getMySQLManager().update("McInfected", "Score", this.score, this.player.getUniqueId());
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		else
			McInfected.getFileManager().getPlayers().set(this.player.getUniqueId() + ".Score", this.score);
	}
	
	/**
	 * @param team
	 *          the team to set
	 */
	public void setTeam(Team team) {
		this.team = team;
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
				McInfected.getMySQLManager().update("McInfected", "Time", this.time, this.player.getUniqueId());
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		else
			McInfected.getFileManager().getPlayers().set(this.player.getUniqueId() + ".Time", this.time);
	}
	
	/**
	 * @param vote
	 *          the vote to set
	 */
	public void setVote(Arena vote) {
		this.vote = vote;
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
				McInfected.getMySQLManager().update("McInfected", "Wins", this.wins, this.player.getUniqueId());
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		else
			McInfected.getFileManager().getPlayers().set(this.player.getUniqueId() + ".Wins", this.wins);
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
	
}
