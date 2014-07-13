
package com.sniperzciinema.mcinfected;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;

import org.bukkit.entity.Player;

import com.sniperzciinema.mcinfected.Arenas.Arena;
import com.sniperzciinema.mcinfected.IPlayers.IPlayer;
import com.sniperzciinema.mcinfected.IPlayers.IPlayer.Team;
import com.sniperzciinema.mcinfected.Utils.Coord;
import com.sniperzciinema.mcinfected.Utils.StringUtil;


public class Lobby {
	
	public enum GameState
	{
		Game, GameOver, Infecting, InLobby, PreGame, Voting;
	}
	
	private ArrayList<Arena>		arenas;
	
	private GameState						gamestate;
	
	private Coord								location;
	private ArrayList<IPlayer>	players;
	
	private Arena								playingIn;
	private Timers							timers;
	
	public Lobby()
	{
		if (McInfected.getFileManager().getArenas().getString("Lobby Location") != null)
			this.location = new Coord(McInfected.getFileManager().getArenas().getString("Lobby Location"));
		
		this.timers = new Timers(this);
		
		this.players = new ArrayList<IPlayer>();
		
		this.arenas = new ArrayList<Arena>();
		loadArenasFromFile();
		
		setGamestate(GameState.InLobby);
	}
	
	/**
	 * @param iPlayer
	 *          the iPlayer to add
	 */
	public void addIPlayer(IPlayer iPlayer) {
		this.players.add(iPlayer);
	}
	
	/**
	 * @param player
	 *          the player to add
	 */
	public IPlayer addIPlayer(Player player) {
		IPlayer iPlayer = new IPlayer(this, player);
		addIPlayer(iPlayer);
		return iPlayer;
	}
	
	/**
	 * @param arena
	 *          the arena to be created
	 */
	public void createArena(String arena) {
		arena = StringUtil.getCapitalized(arena);
		this.arenas.add(new Arena(arena));
	}
	
	/**
	 * @param arenaName
	 * @return the arena by the name
	 */
	public Arena getArena(String arenaName) {
		arenaName = StringUtil.getCapitalized(arenaName);
		for (Arena arena : this.arenas)
			if (arena.getName().equalsIgnoreCase(arenaName))
				return arena;
		
		return null;
	}
	
	/**
	 * @return the arenas
	 */
	public ArrayList<Arena> getArenas() {
		return this.arenas;
	}
	
	/**
	 * @return the gamestate
	 */
	public GameState getGameState() {
		return this.gamestate;
	}
	
	/**
	 * @return a list of humans
	 */
	public ArrayList<IPlayer> getHumans() {
		ArrayList<IPlayer> humans = new ArrayList<IPlayer>();
		for (IPlayer player : this.players)
			if (player.getTeam() == Team.Human)
				humans.add(player);
		return humans;
	}
	
	public ArrayList<Arena> getInValidArenas() {
		ArrayList<Arena> invalidArenas = new ArrayList<Arena>();
		
		for (Arena arena : this.arenas)
			if (arena.getSpawns().isEmpty())
				invalidArenas.add(arena);
		
		return invalidArenas;
	}
	
	/**
	 * @param player
	 * @return the iPlayer from the player
	 */
	public IPlayer getIPlayer(Player player) {
		for (IPlayer iPlayer : this.players)
			if (iPlayer.getPlayer() == player)
				return iPlayer;
		return null;
	}
	
	/**
	 * @return the iPlayers
	 */
	public ArrayList<IPlayer> getIPlayers() {
		return this.players;
	}
	
	/**
	 * @return the location
	 */
	public Coord getLocation() {
		return this.location;
	}
	
	/**
	 * @return the playingIn
	 */
	public Arena getPlayingIn() {
		return this.playingIn;
	}
	
	/**
	 * @return the timers
	 */
	public Timers getTimers() {
		return this.timers;
	}
	
	/**
	 * @return the valid arenas
	 */
	public ArrayList<Arena> getValidArenas() {
		ArrayList<Arena> validArenas = new ArrayList<Arena>();
		
		for (Arena arena : this.arenas)
			if (!arena.getSpawns().isEmpty())
				validArenas.add(arena);
		
		return validArenas;
	}
	
	/**
	 * @return a list of Infecteds
	 */
	public ArrayList<IPlayer> getInfecteds() {
		ArrayList<IPlayer> Infecteds = new ArrayList<IPlayer>();
		for (IPlayer player : this.players)
			if (player.getTeam() == Team.Infected)
				Infecteds.add(player);
		return Infecteds;
	}
	
	public boolean isArena(String arenaName) {
		arenaName = StringUtil.getCapitalized(arenaName);
		return getArena(arenaName) != null;
	}
	
	/**
	 * @param player
	 * @return if the player is playing
	 */
	public boolean isIPlayer(Player player) {
		return getIPlayer(player) != null;
	}
	
	/**
	 * @return if gamestate is "Game", "GameOver", "Infecting"
	 */
	public boolean isStarted() {
		return (this.gamestate == GameState.Game) || (this.gamestate == GameState.GameOver) || (this.gamestate == GameState.Infecting);
	}
	
	/**
	 * Load all the arenas from a the Config.yml
	 */
	public void loadArenasFromFile() {
		if (McInfected.getFileManager().getArenas().getConfigurationSection("") != null)
			for (String arenaName : McInfected.getFileManager().getArenas().getConfigurationSection("").getKeys(true))
				if (!arenaName.contains(".") && !arenaName.contains("Lobby Location"))
				{
					McInfected.getPlugin().getLogger().log(Level.INFO, "Loaded Arena: " + arenaName);
					createArena(arenaName);
				}
		
	}
	
	/**
	 * @param iPlayer
	 *          the iPlayer to remove
	 */
	public void removeIPlayer(IPlayer iPlayer) {
		this.players.remove(iPlayer);
	}
	
	/**
	 * @param iPlayer
	 *          the player to remove
	 */
	public void removeIPlayer(Player player) {
		this.players.remove(getIPlayer(player));
	}
	
	/**
	 * @param gamestate
	 *          the gamestate to set
	 */
	public void setGamestate(GameState gamestate) {
		this.gamestate = gamestate;
	}
	
	/**
	 * Set the lobbys location
	 * 
	 * @param coord
	 */
	public void setLocation(Coord coord) {
		McInfected.getFileManager().getArenas().set("Lobby Location", coord.asString());
		McInfected.getFileManager().saveArenas();
		this.location = coord;
	}
	
	/**
	 * @param playingIn
	 *          the playingIn to set
	 */
	public void setPlayingIn(Arena playingIn) {
		this.playingIn = playingIn;
	}
	
	/**
	 * Teleport the iPlayer to the lobby's location
	 * 
	 * @param iPlayer
	 */
	public void teleport(IPlayer iPlayer) {
		iPlayer.getPlayer().teleport(this.location.asLocation());
	}
	
	/**
	 * Teleport all iPlayers to the lobby's location
	 */
	public void teleportAll() {
		for (IPlayer iPlayer : getIPlayers())
			iPlayer.getPlayer().teleport(this.location.asLocation());
	}
	
	/**
	 * Teleport the player to the selected arena
	 * 
	 * @param player
	 * @param arena
	 */
	public void teleportToArena(IPlayer iPlayer, Arena arena) {
		Random r = new Random(System.currentTimeMillis());
		int i = r.nextInt(arena.getSpawns(iPlayer.getTeam()).size());
		iPlayer.getPlayer().teleport(arena.getSpawns(iPlayer.getTeam()).get(i).asLocation());
	}
	
}
