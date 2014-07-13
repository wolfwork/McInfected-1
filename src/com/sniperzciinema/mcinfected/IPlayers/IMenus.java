
package com.sniperzciinema.mcinfected.IPlayers;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import com.sniperzciinema.mcinfected.McInfected;
import com.sniperzciinema.mcinfected.Messanger.Messages;
import com.sniperzciinema.mcinfected.Arenas.Arena;
import com.sniperzciinema.mcinfected.IPlayers.IPlayer.Team;
import com.sniperzciinema.mcinfected.Kits.Kit;
import com.sniperzciinema.mcinfected.Utils.IconMenu;
import com.sniperzciinema.mcinfected.Utils.StringUtil;
import com.sniperzciinema.mcinfected.Utils.StringUtil.RandomChatColor;


public class IMenus {
	
	private IPlayer		iPlayer;
	private IconMenu	kitsHuman;
	private IconMenu	kitsTeam;
	
	private IconMenu	kitsZombie;
	
	public IMenus(IPlayer iPlayer)
	{
		this.iPlayer = iPlayer;
	}
	
	public IconMenu getKitsHuman() {
		if (this.kitsHuman == null)
		{
			final Team team = Team.Human;
			this.kitsHuman = new IconMenu(
					RandomChatColor.getColor(ChatColor.GOLD, ChatColor.GREEN, ChatColor.BLUE, ChatColor.RED, ChatColor.DARK_AQUA, ChatColor.YELLOW) + "Human Classes",
					((McInfected.getKitManager().getKits(team).size() / 9) * 9) + 18, new IconMenu.OptionClickEventHandler()
					{
						
						@Override
						public void onOptionClick(IconMenu.OptionClickEvent event) {
							if (ChatColor.stripColor(event.getName()).equalsIgnoreCase("Return"))
								Bukkit.getScheduler().scheduleSyncDelayedTask(McInfected.getPlugin(), new Runnable()
								{
									
									@Override
									public void run() {
										getKitsTeam().open();
										
									}
								}, 2);
							else
							{
								IMenus.this.iPlayer.getPlayer().sendMessage(McInfected.getMessanger().getMessage(false, Messages.Menu__Kit__Chosen, "<kit>", event.getName(), "<team>", "Human"));
								IMenus.this.iPlayer.setKit(team, McInfected.getKitManager().getKit(ChatColor.stripColor(event.getName())));
							}
						}
					}, McInfected.getPlugin(), this.iPlayer.getPlayer());
			int i = 0;
			for (Kit kit : McInfected.getKitManager().getKits(team))
			{
				if (this.iPlayer.getPlayer().hasPermission("McInfected.Kits.Human.*") || this.iPlayer.getPlayer().hasPermission("McInfected.Kits.Human." + kit.getName()))
				{
					ItemStack item = kit.getIcon();
					
					StringBuilder desc = new StringBuilder();
					
					for (String line : kit.getDescription())
						desc.append(line + "<split>");
					
					this.kitsHuman.setOption(i, item, kit.getName(), ChatColor.GREEN + McInfected.getMessanger().getMessage(false, Messages.Menu__Kit__Choose), "", StringUtil.addColor(desc.toString()), "");
					i++;
				}
				this.kitsHuman.setOption(i + (9 - i) + 4, new ItemStack(Material.NETHER_STAR), "Return", McInfected.getMessanger().getMessage(false, Messages.Menu__Kit__Return));
			}
		}
		return this.kitsHuman;
	}
	
	public IconMenu getKitsTeam() {
		if (this.kitsTeam == null)
		{
			this.kitsTeam = new IconMenu(
					RandomChatColor.getColor(ChatColor.GOLD, ChatColor.GREEN, ChatColor.BLUE, ChatColor.RED, ChatColor.DARK_AQUA, ChatColor.YELLOW) + "Choose a Team",
					9, new IconMenu.OptionClickEventHandler()
					{
						
						@Override
						public void onOptionClick(final IconMenu.OptionClickEvent event) {
							Bukkit.getScheduler().scheduleSyncDelayedTask(McInfected.getPlugin(), new Runnable()
							{
								
								@Override
								public void run() {
									if (ChatColor.stripColor(event.getName()).equals("Human"))
										getKitsHuman().open();
									else if (ChatColor.stripColor(event.getName()).equals("Zombie"))
										getKitsZombie().open();
								}
							}, 2);
						}
					}, McInfected.getPlugin(), this.iPlayer.getPlayer());
			
			ItemStack zombie = new ItemStack(Material.SKULL_ITEM);
			zombie.setDurability((short) 2);
			
			ItemStack human = new ItemStack(Material.SKULL_ITEM);
			human.setDurability((short) 3);
			
			this.kitsTeam.setOption(3, zombie, ChatColor.RED + "Zombie", McInfected.getMessanger().getMessage(false, Messages.Menu__Team__Choose, "<team>", ChatColor.RED + "Zombie"));
			this.kitsTeam.setOption(5, human, ChatColor.GREEN + "Human", McInfected.getMessanger().getMessage(false, Messages.Menu__Team__Choose, "<team>", ChatColor.GREEN + "Human"));
		}
		
		return this.kitsTeam;
	}
	
	public IconMenu getKitsZombie() {
		if (this.kitsZombie == null)
		{
			final Team team = Team.Zombie;
			this.kitsZombie = new IconMenu(
					RandomChatColor.getColor(ChatColor.GOLD, ChatColor.GREEN, ChatColor.BLUE, ChatColor.RED, ChatColor.DARK_AQUA, ChatColor.YELLOW) + "Zombie Classes",
					((McInfected.getKitManager().getKits(team).size() / 9) * 9) + 18, new IconMenu.OptionClickEventHandler()
					{
						
						@Override
						public void onOptionClick(IconMenu.OptionClickEvent event) {
							if (ChatColor.stripColor(event.getName()).equalsIgnoreCase("Return"))
								Bukkit.getScheduler().scheduleSyncDelayedTask(McInfected.getPlugin(), new Runnable()
								{
									
									@Override
									public void run() {
										getKitsTeam().open();
										
									}
								}, 2);
							else
							{
								IMenus.this.iPlayer.getPlayer().sendMessage(McInfected.getMessanger().getMessage(false, Messages.Menu__Kit__Chosen, "<kit>", event.getName(), "<team>", "Zombie"));
								IMenus.this.iPlayer.setKit(team, McInfected.getKitManager().getKit(ChatColor.stripColor(event.getName())));
							}
						}
					}, McInfected.getPlugin(), this.iPlayer.getPlayer());
			int i = 0;
			for (Kit kit : McInfected.getKitManager().getKits(team))
			{
				if (this.iPlayer.getPlayer().hasPermission("McInfected.Kits.Zombie.*") || this.iPlayer.getPlayer().hasPermission("McInfected.Kits.Zombie." + kit.getName()))
				{
					ItemStack item = kit.getIcon();
					
					StringBuilder desc = new StringBuilder();
					
					for (String line : kit.getDescription())
						desc.append(line + "<split>");
					
					this.kitsZombie.setOption(i, item, kit.getName(), ChatColor.GREEN + McInfected.getMessanger().getMessage(false, Messages.Menu__Kit__Choose), "", StringUtil.addColor(desc.toString()), "");
					i++;
				}
				this.kitsZombie.setOption(i + (9 - i) + 4, new ItemStack(Material.NETHER_STAR), "Return", McInfected.getMessanger().getMessage(false, Messages.Menu__Kit__Return));
			}
		}
		return this.kitsZombie;
	}
	
	public IconMenu getVote() {
		
		IconMenu vote = new IconMenu(
				RandomChatColor.getColor(ChatColor.GOLD, ChatColor.BLUE, ChatColor.RED, ChatColor.DARK_AQUA, ChatColor.YELLOW) + "Vote for an Arena",
				((McInfected.getLobby().getArenas().size() / 9) * 9) + 9, new IconMenu.OptionClickEventHandler()
				{
					
					@Override
					public void onOptionClick(IconMenu.OptionClickEvent event) {
						Arena arena;
						if (ChatColor.stripColor(event.getName()).equalsIgnoreCase("Random"))
						{
							int i;
							Random r = new Random();
							i = r.nextInt(McInfected.getLobby().getValidArenas().size());
							arena = McInfected.getLobby().getValidArenas().get(i);
						}
						else
							arena = McInfected.getLobby().getArena(ChatColor.stripColor(event.getName()));
						arena.setVotes(arena.getVotes() + 1);
						IMenus.this.iPlayer.setVote(arena);
						
						for (IPlayer iPlayer : McInfected.getLobby().getIPlayers())
						{
							iPlayer.getPlayer().sendMessage(McInfected.getMessanger().getMessage(true, Messages.Game__Voted, "<player>", iPlayer.getPlayer().getName(), "<arena>", arena.getName()));
							iPlayer.getiScoreboard().update();
						}
						
					}
				}, McInfected.getPlugin(), this.iPlayer.getPlayer());
		int place = 0;
		for (Arena arena : McInfected.getLobby().getValidArenas())
		{
			vote.setOption(place, arena.getIcon(), "" + RandomChatColor.getColor() + ChatColor.BOLD + ChatColor.UNDERLINE + arena.getName(), "", McInfected.getMessanger().getMessage(false, Messages.Menu__Vote__Choose), "", ChatColor.GREEN + "Votes: " + arena.getVotes(), "", ChatColor.AQUA + "--------------------------", ChatColor.AQUA + "Creator: " + ChatColor.WHITE + arena.getCreator());
			place++;
		}
		vote.setOption(place, new ItemStack(Material.MAP), "§aR§ba§cn§dd§eo§fm", "", McInfected.getMessanger().getMessage(false, Messages.Menu__Vote__Random));
		
		return vote;
	}
}
