
package com.sniperzciinema.mcinfected.IPlayers;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.sniperzciinema.mcinfected.McInfected;
import com.sniperzciinema.mcinfected.Arenas.Arena;


public class IScoreboard implements Listener {
	
	public enum ScoreboardType
	{
		GAME, STATS
	}
	
	private IPlayer					iPlayer;
	
	private ScoreboardType	scoreboardType;
	
	public IScoreboard()
	{
	}
	
	public IScoreboard(IPlayer iPlayer)
	{
		this.iPlayer = iPlayer;
		this.scoreboardType = ScoreboardType.GAME;
		showRegular();
	}
	
	/**
	 * @return the iPlayer
	 */
	public IPlayer getiPlayer() {
		return this.iPlayer;
	}
	
	/**
	 * @return the scoreboardType
	 */
	public ScoreboardType getScoreboardType() {
		return this.scoreboardType;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerToggleSneak(PlayerToggleSneakEvent e) {
		if (McInfected.getLobby().isIPlayer(e.getPlayer()))
			McInfected.getLobby().getIPlayer(e.getPlayer()).getiScoreboard().switchBoardType();
		
	}
	
	/**
	 * Show the regular scoreboard
	 */
	@SuppressWarnings("deprecation")
	private void showRegular() {
		this.scoreboardType = ScoreboardType.GAME;
		
		// Create a new scoreboard
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard sb = manager.getNewScoreboard();
		Objective ob = sb.registerNewObjective("Infected", "dummy");
		ob.setDisplaySlot(DisplaySlot.SIDEBAR);
		
		if (!McInfected.getLobby().isStarted())
		{
			ob.setDisplayName("§E§l§nVote For An Arena!");
			
			ob.getScore("§1").setScore(99);
			int arenas = 1;
			for (Arena arena : McInfected.getLobby().getValidArenas())
			{
				Score score;
				if (arena == McInfected.getLobby().getPlayingIn())
					score = ob.getScore("§e§l>" + arena.getName().substring(0, Math.min(11, arena.getName().length())));
				else
					score = ob.getScore("§e§o" + arena.getName().substring(0, Math.min(12, arena.getName().length())));
				
				if ((arenas > 14) && (arena.getVotes() != 0))
					for (OfflinePlayer op : sb.getPlayers())
						if (ob.getScore(op.getName()).getScore() == 0)
						{
							sb.resetScores(op.getName());
							break;
							
						}
				score.setScore(1);
				score.setScore(arena.getVotes());
				arenas++;
			}
		}
		else
		{
			ob.setDisplayName("§E§l§nTeams");
			ob.getScore("§1").setScore(8);
			ob.getScore("§a§lHumans:").setScore(7);
			ob.getScore("§a§f" + McInfected.getLobby().getHumans().size()).setScore(6);
			ob.getScore("§2").setScore(5);
			ob.getScore("§3").setScore(4);
			ob.getScore("§4§lInfecteds:").setScore(3);
			ob.getScore("§c§f" + McInfected.getLobby().getInfecteds().size()).setScore(2);
			ob.getScore("§4").setScore(1);
		}
		
		this.iPlayer.getPlayer().setScoreboard(sb);
	}
	
	/**
	 * Show the stats board
	 */
	public void showStats() {
		this.scoreboardType = ScoreboardType.STATS;
		
		// Create a new scoreboard
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard sb = manager.getNewScoreboard();
		Objective ob = sb.registerNewObjective("Infected", "dummy");
		ob.setDisplaySlot(DisplaySlot.SIDEBAR);
		ob.setDisplayName("§E§l§nStats");
		ob.getScore("§1").setScore(-1);
		ob.getScore("§a§lKills:").setScore(-2);
		ob.getScore("§a§f" + this.iPlayer.getStats().getKills()).setScore(-3);
		ob.getScore("§4§lDeaths:").setScore(-4);
		ob.getScore("§b§f" + this.iPlayer.getStats().getDeaths()).setScore(-5);
		ob.getScore("§2").setScore(-6);
		ob.getScore("§e§lWins:").setScore(-7);
		ob.getScore("§c§f" + this.iPlayer.getStats().getWins()).setScore(-8);
		ob.getScore("§b§lLosses:").setScore(-9);
		ob.getScore("§d§f" + this.iPlayer.getStats().getLosses()).setScore(-10);
		ob.getScore("§3").setScore(-11);
		ob.getScore("§6§lKillStreak:").setScore(-12);
		ob.getScore("§e§f" + this.iPlayer.getStats().getKillStreak()).setScore(-13);
		ob.getScore("§4").setScore(-14);
		
		this.iPlayer.getPlayer().setScoreboard(sb);
	}
	
	/**
	 * Toggles the scoreboard they're seeing
	 */
	public void switchBoardType() {
		if (this.scoreboardType == ScoreboardType.GAME)
			showStats();
		else
			showRegular();
	}
	
	/**
	 * Update the scoreboard for the player
	 */
	public void update() {
		if (this.scoreboardType == ScoreboardType.GAME)
			showRegular();
		else
			showStats();
	}
}
