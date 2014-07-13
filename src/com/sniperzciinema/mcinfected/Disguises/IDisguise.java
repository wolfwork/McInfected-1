
package com.sniperzciinema.mcinfected.Disguises;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.sniperzciinema.mcinfected.McInfected;
import com.sniperzciinema.mcinfected.IPlayers.IPlayer;

import de.robingrether.idisguise.iDisguise;
import de.robingrether.idisguise.api.DisguiseAPI;
import de.robingrether.idisguise.disguise.DisguiseType;
import de.robingrether.idisguise.disguise.MobDisguise;


public class IDisguise extends Disguises {
	
	private iDisguise		id		= ((iDisguise) Bukkit.getServer().getPluginManager().getPlugin("iDisguise"));
	public DisguiseAPI	idAPI	= this.id.getAPI();
	
	public IDisguise()
	{
		super("iDisguise");
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
				this.idAPI.disguiseToAll(player, disguise);
			}
			else
				this.idAPI.disguiseToAll(player, new MobDisguise(DisguiseType.ZOMBIE, true));
		else
		{
			unDisguise(player);
			disguise(player);
		}
	}
	
	@Override
	public boolean isDisguised(Player player) {
		return this.idAPI.isDisguised(player);
	}
	
	@Override
	public void unDisguise(Player player) {
		this.idAPI.undisguiseToAll(player);
	}
	
}
