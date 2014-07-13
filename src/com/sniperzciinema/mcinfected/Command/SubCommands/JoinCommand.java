
package com.sniperzciinema.mcinfected.Command.SubCommands;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.sniperzciinema.mcinfected.McInfected;
import com.sniperzciinema.mcinfected.Messanger.Messages;
import com.sniperzciinema.mcinfected.Command.SubCommand;
import com.sniperzciinema.mcinfected.Command.FancyMessages.FancyMessage;
import com.sniperzciinema.mcinfected.Events.McInfectedJoinEvent;
import com.sniperzciinema.mcinfected.IPlayers.IPlayer;


public class JoinCommand extends SubCommand {
	
	public JoinCommand()
	{
		super("Join");
	}
	
	@Override
	public void execute(CommandSender sender, String[] args) throws CommandException {
		
		if (!(sender instanceof Player))
			sender.sendMessage(getMessanger().getMessage(true, Messages.Error__Command__Not_A_Player));
		
		else
		{
			McInfectedJoinEvent event = new McInfectedJoinEvent((Player) sender);
			Bukkit.getPluginManager().callEvent(event);
			if (!event.isCancelled())
				if (!sender.hasPermission(getPermission()))
					sender.sendMessage(McInfected.getMessanger().getMessage(true, Messages.Error__Misc__Invalid_Permission));
				
				else if (McInfected.getLobby().getLocation() == null)
					sender.sendMessage(getMessanger().getMessage(true, Messages.Error__Lobby__No_Location));
				
				else if (McInfected.getLobby().getValidArenas().size() == 0)
					sender.sendMessage(getMessanger().getMessage(true, Messages.Error__Arena__No_Valid_Arenas));
				
				else if (McInfected.getLobby().isIPlayer((Player) sender))
					sender.sendMessage(getMessanger().getMessage(true, Messages.Error__Game__Already_In_A_Game));
				
				else
				{
					
					McInfected.getMessanger().broadcast(getMessanger().getMessage(true, Messages.Command__Game_Joined, "<player>", sender.getName()));
					IPlayer iPlayer = McInfected.getLobby().addIPlayer((Player) sender);
					iPlayer.getPlayer().setGameMode(GameMode.ADVENTURE);
					iPlayer.clearEquipment();
					iPlayer.heal();
					McInfected.getLobby().teleport(iPlayer);
					sender.sendMessage(getMessanger().getMessage(true, Messages.Command__Join));
					
					if (!McInfected.getLobby().isStarted())
					{
						if (McInfected.getLobby().getIPlayers().size() == McInfected.getSettings().getAutoStartPlayers())
							McInfected.getLobby().getTimers().startVoting();
					}
					else
						iPlayer.infect();
				}
			
		}
	}
	
	@Override
	public List<String> getAliases() {
		return Arrays.asList(new String[] { "j" });
	}
	
	@Override
	public FancyMessage getFancyMessage() {
		return new FancyMessage(getHelpMessage()).tooltip("Join", " ", "§eJoin an Arena", " ", "§f§l/McInfected Join").suggest("/McInfected Join");
	}
	
	@Override
	public String getHelpMessage() {
		return getMessanger().getMessage(false, Messages.Format__Prefix) + "§7/McInfected §oJoin";
	}
	
	@Override
	public String getPermission() {
		return "McInfected.Join";
	}
	
	@Override
	public List<String> getTabs(String[] args) {
		return Arrays.asList(new String[] { "" });
	}
}
