
package com.sniperzciinema.mcinfected.Kits;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import com.sniperzciinema.mcinfected.McInfected;
import com.sniperzciinema.mcinfected.IPlayers.IPlayer.Team;
import com.sniperzciinema.mcinfected.Utils.ItemUtil;
import com.sniperzciinema.mcinfected.Utils.PotionUtil;


public class Kit {
	
	private List<String>						description;
	
	private String									disguise;
	
	private ItemStack								helmet, chestplate, leggings, boots;
	
	private ItemStack								icon;
	private ArrayList<ItemStack>		inventory;
	private String									name;
	private ArrayList<PotionEffect>	potions;
	private Team										team;
	
	public Kit(String name, Team team)
	{
		// Set name and team
		setName(name);
		setTeam(team);
		
		// Load Description
		if (McInfected.getFileManager().getKits().getStringList(team.toString() + "." + getName() + ".Description") != null)
			this.description = McInfected.getFileManager().getKits().getStringList(team.toString() + "." + getName() + ".Description");
		
		// Load Icon
		if (McInfected.getFileManager().getKits().getString(team.toString() + "." + getName() + ".Icon") != null)
			setIcon(ItemUtil.getItemStack(McInfected.getFileManager().getKits().getString(team.toString() + "." + getName() + ".Icon")));
		
		// Load Inventory
		if (McInfected.getFileManager().getKits().getStringList(team.toString() + "." + getName() + ".Inventory") != null)
			this.inventory = ItemUtil.getItemStackList(McInfected.getFileManager().getKits().getStringList(team.toString() + "." + getName() + ".Inventory"));
		
		// Load Armor
		if (McInfected.getFileManager().getKits().getString(team.toString() + "." + getName() + ".Armor.Helmet") != null)
			setHelmet(ItemUtil.getItemStack(McInfected.getFileManager().getKits().getString(team.toString() + "." + getName() + ".Armor.Helmet")));
		if (McInfected.getFileManager().getKits().getString(team.toString() + "." + getName() + ".Armor.Chestplate") != null)
			setChestplate(ItemUtil.getItemStack(McInfected.getFileManager().getKits().getString(team.toString() + "." + getName() + ".Armor.Chestplate")));
		if (McInfected.getFileManager().getKits().getString(team.toString() + "." + getName() + ".Armor.Leggings") != null)
			setLeggings(ItemUtil.getItemStack(McInfected.getFileManager().getKits().getString(team.toString() + "." + getName() + ".Armor.Leggings")));
		if (McInfected.getFileManager().getKits().getString(team.toString() + "." + getName() + ".Armor.Boots") != null)
			setBoots(ItemUtil.getItemStack(McInfected.getFileManager().getKits().getString(team.toString() + "." + getName() + ".Armor.Boots")));
		
		// Load Potions
		if (McInfected.getFileManager().getKits().getStringList(team.toString() + "." + getName() + ".Potions") != null)
			this.potions = PotionUtil.getPotionEffectList(McInfected.getFileManager().getKits().getStringList(team.toString() + "." + getName() + ".Potions"));
		
		// Load Disguise
		if (McInfected.getFileManager().getKits().getString(team.toString() + "." + getName() + ".Disguise") != null)
			this.disguise = McInfected.getFileManager().getKits().getString(team.toString() + "." + getName() + ".Disguise");
		
	}
	
	/**
	 * @return the boots
	 */
	public ItemStack getBoots() {
		return this.boots;
	}
	
	/**
	 * @return the chestplate
	 */
	public ItemStack getChestplate() {
		return this.chestplate;
	}
	
	/**
	 * @return the description
	 */
	public List<String> getDescription() {
		return this.description;
	}
	
	/**
	 * @return the disguise
	 */
	public String getDisguise() {
		return this.disguise;
	}
	
	/**
	 * @return the helmet
	 */
	public ItemStack getHelmet() {
		return this.helmet;
	}
	
	/**
	 * @return the icon
	 */
	public ItemStack getIcon() {
		return this.icon;
	}
	
	/**
	 * @return the inventory
	 */
	public ArrayList<ItemStack> getInventory() {
		return this.inventory;
	}
	
	/**
	 * @return the leggings
	 */
	public ItemStack getLeggings() {
		return this.leggings;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return the potions
	 */
	public ArrayList<PotionEffect> getPotions() {
		return this.potions;
	}
	
	/**
	 * @return the team
	 */
	public Team getTeam() {
		return this.team;
	}
	
	/**
	 * @param boots
	 *          the boots to set
	 */
	public void setBoots(ItemStack boots) {
		this.boots = boots;
	}
	
	/**
	 * @param chestplate
	 *          the chestplate to set
	 */
	public void setChestplate(ItemStack chestplate) {
		this.chestplate = chestplate;
	}
	
	/**
	 * @param description
	 *          the description to set
	 */
	public void setDescription(List<String> description) {
		this.description = description;
	}
	
	/**
	 * @param disguise
	 *          the disguise to set
	 */
	public void setDisguise(String disguise) {
		this.disguise = disguise;
	}
	
	/**
	 * @param helmet
	 *          the helmet to set
	 */
	public void setHelmet(ItemStack helmet) {
		this.helmet = helmet;
	}
	
	/**
	 * @param icon
	 *          the icon to set
	 */
	public void setIcon(ItemStack icon) {
		this.icon = icon;
	}
	
	/**
	 * @param inventory
	 *          the inventory to set
	 */
	public void setInventory(ArrayList<ItemStack> inventory) {
		this.inventory = inventory;
	}
	
	/**
	 * @param leggings
	 *          the leggings to set
	 */
	public void setLeggings(ItemStack leggings) {
		this.leggings = leggings;
	}
	
	/**
	 * @param name
	 *          the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * @param potions
	 *          the potions to set
	 */
	public void setPotions(ArrayList<PotionEffect> potions) {
		this.potions = potions;
	}
	
	/**
	 * @param team
	 *          the team to set
	 */
	public void setTeam(Team team) {
		this.team = team;
	}
	
}
