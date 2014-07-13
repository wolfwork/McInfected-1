
package com.sniperzciinema.mcinfected.Disguises;

import org.bukkit.entity.Player;

import pgDev.bukkit.DisguiseCraft.api.DisguiseCraftAPI;
import pgDev.bukkit.DisguiseCraft.disguise.Disguise;
import pgDev.bukkit.DisguiseCraft.disguise.DisguiseType;

import com.sniperzciinema.mcinfected.McInfected;
import com.sniperzciinema.mcinfected.IPlayers.IPlayer;
import com.sniperzciinema.mcinfected.Utils.StringUtil;


public class DisguiseCraft extends Disguises {
	
	private DisguiseCraftAPI	dcAPI	= pgDev.bukkit.DisguiseCraft.DisguiseCraft.getAPI();
	
	public DisguiseCraft()
	{
		super("Disguise Craft");
		for (DisguiseType disguise : DisguiseType.values())
			this.disguiseList.add(disguise.toString());
	}
	
	@Override
	public void disguise(Player player) {
		IPlayer iPlayer = McInfected.getLobby().getIPlayer(player);
		
		if (!isDisguised(player))
			if (DisguiseType.fromString(StringUtil.getCapitalized(iPlayer.getKit(iPlayer.getTeam()).getDisguise())) != null)
			{
				Disguise disguise = new Disguise(this.dcAPI.newEntityID(),
						DisguiseType.fromString(StringUtil.getCapitalized(iPlayer.getKit(iPlayer.getTeam()).getDisguise()))).addSingleData("noarmor");
				this.dcAPI.disguisePlayer(player, disguise);
			}
			else
				this.dcAPI.disguisePlayer(player, new Disguise(this.dcAPI.newEntityID(), DisguiseType.Zombie).addSingleData("noarmor"));
		else
		{
			unDisguise(player);
			disguise(player);
		}
	}
	
	@Override
	public boolean isDisguised(Player player) {
		return this.dcAPI.isDisguised(player);
	}
	
	@Override
	public void unDisguise(Player player) {
		this.dcAPI.undisguisePlayer(player);
	}
}
