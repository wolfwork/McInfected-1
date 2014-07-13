
package com.sniperzciinema.mcinfected.Disguises;

import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;

import org.bukkit.entity.Player;

import com.sniperzciinema.mcinfected.McInfected;
import com.sniperzciinema.mcinfected.IPlayers.IPlayer;


public class LibsDisguises extends Disguises {
	
	public LibsDisguises()
	{
		super("LibsDisguises");
		for (DisguiseType disguise : DisguiseType.values())
			this.disguiseList.add(disguise.toString());
	}
	
	@Override
	public void disguise(Player player) {
		
		IPlayer iPlayer = McInfected.getLobby().getIPlayer(player);
		
		if (!isDisguised(player))
			if (DisguiseType.valueOf(iPlayer.getKit(iPlayer.getTeam()).getDisguise().toUpperCase()) != null)
			{
				MobDisguise disguise = new MobDisguise(DisguiseType.valueOf(iPlayer.getKit(iPlayer.getTeam()).getDisguise().toUpperCase()), true);
				DisguiseAPI.disguiseToAll(player, disguise);
			}
			else
				DisguiseAPI.disguiseToAll(player, new MobDisguise(DisguiseType.ZOMBIE, true));
		else
		{
			unDisguise(player);
			disguise(player);
		}
	}
	
	@Override
	public boolean isDisguised(Player p) {
		return DisguiseAPI.isDisguised(p);
	}
	
	@Override
	public void unDisguise(Player p) {
		DisguiseAPI.undisguiseToAll(p);
	}
	
}
