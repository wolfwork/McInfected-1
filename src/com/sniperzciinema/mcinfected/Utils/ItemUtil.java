
package com.sniperzciinema.mcinfected.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;

import com.shampaggon.crackshot.CSUtility;


public class ItemUtil {
	
	/**
	 * Get the itemstack from the item code
	 * 
	 * @param itemCode
	 * @return the ItemStack
	 */
	@SuppressWarnings("deprecation")
	public static ItemStack getItemStack(String itemCode) {
		ItemStack stack = new ItemStack(Material.AIR, 1);
		if (itemCode != null)
			if (itemCode.contains(" "))
			{
				String[] line = itemCode.split(" ");
				// Loop through every part of the code
				for (String data : line)
					
					if (data.startsWith("crackshot"))
					{
						String name = data.split(":")[1];
						CSUtility cs = new CSUtility();
						stack = cs.generateWeapon(name);
					}
					// Item Variables
					else if (data.startsWith("id") || data.startsWith("item"))
					{
						
						Material mat = null;
						String item = data.split(":")[1];
						try
						{
							mat = Material.getMaterial(Integer.valueOf(item));
						}
						catch (NumberFormatException e)
						{
							if (Material.getMaterial(item) != null)
								mat = Material.getMaterial(item);
							else
								mat = Material.AIR;
						}
						stack.setType(mat);
					}
					
					// Amount Variables
					else if (data.startsWith("amount") || data.startsWith("quantity"))
						stack.setAmount(Integer.parseInt(data.split(":")[1]));
					
					// Durability Variables
					else if (data.startsWith("data") || data.startsWith("durability") || data.startsWith("damage"))
						stack.setDurability(Short.parseShort(data.split(":")[1]));
					
					// Enchantment variables
					else if (data.startsWith("enchantment") || data.startsWith("enchant"))
					{
						String s = data.split(":")[1];
						
						Enchantment enchantment;
						try
						{
							enchantment = Enchantment.getById(Integer.parseInt(s.split("-")[0]));
						}
						catch (NumberFormatException e)
						{
							enchantment = Enchantment.getByName(s.split("-")[0].toUpperCase());
						}
						// Level Stated
						if (s.contains("-"))
							stack.addUnsafeEnchantment(enchantment, Integer.parseInt(s.split("-")[1]));
						// No Level Stated
						else
							stack.addUnsafeEnchantment(enchantment, 1);
						
					}
					
					// Name Variables
					else if (data.startsWith("name") || data.startsWith("title"))
					{
						ItemMeta im = stack.getItemMeta();
						im.setDisplayName(StringUtil.addColor(data.split(":")[1]));
						stack.setItemMeta(im);
					}
					
					// Owner Variables
					else if (data.startsWith("owner") || data.startsWith("player"))
					{
						SkullMeta im = (SkullMeta) stack.getItemMeta();
						im.setOwner(data.split(":")[1]);
						stack.setItemMeta(im);
					}
					
					// Color Variables(Leather Only)
					else if (data.startsWith("color") || data.startsWith("colour"))
						try
						{
							LeatherArmorMeta im = (LeatherArmorMeta) stack.getItemMeta();
							String[] s = data.replaceAll("color:", "").replaceAll("colour", "").split(",");
							int red = Integer.parseInt(s[0]);
							int green = Integer.parseInt(s[1]);
							int blue = Integer.parseInt(s[2]);
							im.setColor(Color.fromRGB(red, green, blue));
							stack.setItemMeta(im);
						}
						catch (ClassCastException notLeather)
						{
						}
					// Potion Effect Variables(Potions Only)
					else if (data.startsWith("potion"))
					{
						String[] s = data.replaceAll("potion:", "").split(",");
						PotionEffectType type = PotionEffectType.SPEED;
						try
						{
							type = PotionEffectType.getById((Integer.valueOf(s[0])));
						}
						catch (NumberFormatException e)
						{
							if (PotionEffectType.getByName(s[0].toUpperCase()) != null)
								type = PotionEffectType.getByName(s[0].toUpperCase());
						}
						Potion potion = new Potion(PotionType.getByEffect(type));
						potion.setSplash((s.length == 4) && s[3].equalsIgnoreCase("true") ? true : false);
						potion.apply(stack);
						PotionMeta pm = (PotionMeta) stack.getItemMeta();
						
						int level = Integer.parseInt(s[1]) - 1;
						int time = Integer.parseInt(s[2]) * 20;
						pm.addCustomEffect(new PotionEffect(type, time, level), false);
					}
					
					// Lore Variables
					else if (data.startsWith("lore") || data.startsWith("desc") || data.startsWith("description"))
					{
						String s = data.split(":")[1];
						List<String> lores = new ArrayList<String>();
						for (String lore : s.split("\\|"))
							lores.add(StringUtil.addColor(lore.replace('_', ' ')));
						ItemMeta meta = stack.getItemMeta();
						meta.setLore(lores);
						stack.setItemMeta(meta);
					}
			}
			
			// Crackshot Variables
			else if (itemCode.startsWith("crackshot") || itemCode.startsWith("gun"))
			{
				String name = itemCode.split(":")[1];
				CSUtility cs = new CSUtility();
				stack = cs.generateWeapon(name);
			}
			// Item Variables
			else if (itemCode.startsWith("id") || itemCode.startsWith("item"))
				return new ItemStack(Material.getMaterial(Integer.parseInt(itemCode.split(":")[1])));
		
		return stack;
	}
	
	/** Loop through a list of these Item Codes and make a ItemStack[] */
	public static ArrayList<ItemStack> getItemStackList(List<String> list) {
		ArrayList<ItemStack> items = new ArrayList<ItemStack>();
		if ((list != null) && !list.isEmpty())
			for (String string : list)
				items.add(getItemStack(string));
		
		return items;
	}
	
	// TODO: Add Crackshot, Potions and Colors
	@SuppressWarnings("deprecation")
	public static String getItemStackToString(ItemStack i) {
		String itemCode = "id:0";
		
		if ((i.getTypeId() != 0) && (i != null))
		{
			
			itemCode = "id:" + String.valueOf(i.getTypeId());
			
			if (i.getDurability() != 0)
				itemCode = itemCode + " data:" + i.getDurability();
			if (i.getAmount() > 1)
				itemCode = itemCode + " amount:" + i.getAmount();
			for (Entry<Enchantment, Integer> ench : i.getEnchantments().entrySet())
			{
				itemCode = itemCode + " enchantment:" + ench.getKey().getId();
				if (ench.getValue() > 1)
					itemCode = itemCode + "-" + ench.getValue();
			}
			if (i.getItemMeta().getDisplayName() != null)
				itemCode = itemCode + " name:" + i.getItemMeta().getDisplayName().replaceAll(" ", "_").replaceAll("§", "&");
			
			if (i.getItemMeta().hasLore())
			{
				itemCode = itemCode + " lore:";
				for (String string : i.getItemMeta().getLore())
					itemCode = itemCode + string.replaceAll(" ", "_").replaceAll("§", "&") + "|";
				itemCode.substring(0, itemCode.length() - 2);
			}
			if (i.getType().toString().toLowerCase().contains("leather") && (((LeatherArmorMeta) i.getItemMeta()).getColor() != null))
			{
				LeatherArmorMeta im = (LeatherArmorMeta) i.getItemMeta();
				itemCode = itemCode + " color:" + im.getColor().getRed() + "," + im.getColor().getGreen() + "," + im.getColor().getBlue();
			}
		}
		return itemCode;
	}
	
}
