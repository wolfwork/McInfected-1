
package com.sniperzciinema.mcinfected.Utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;


public class PotionUtil {
	
	@SuppressWarnings("deprecation")
	public static PotionEffect getPotionEffect(String potionCode) {
		PotionEffectType type = PotionEffectType.SPEED;
		int power = 1;
		int time = 1;
		if (potionCode != null)
		{
			String[] s = potionCode.split(",");
			
			try
			{
				type = PotionEffectType.getById((Integer.valueOf(s[0])));
			}
			catch (NumberFormatException e)
			{
				if (PotionEffectType.getByName(s[0]) != null)
					type = PotionEffectType.getByName(s[0]);
			}
			time = Integer.valueOf(s[1]);
			power = Integer.valueOf(s[2]);
		}
		return new PotionEffect(type, time * 20, power - 1);
	}
	
	public static ArrayList<PotionEffect> getPotionEffectList(List<String> potionCodes) {
		ArrayList<PotionEffect> effects = new ArrayList<PotionEffect>();
		if ((potionCodes != null) && !potionCodes.isEmpty())
			for (String code : potionCodes)
				effects.add(getPotionEffect(code));
		
		return effects;
	}
}
