
package com.sniperzciinema.mcinfected.Utils;

import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;

import com.sniperzciinema.mcinfected.McInfected;
import com.sniperzciinema.mcinfected.Messanger.Messages;


public class StringUtil {
	
	public static class RandomChatColor {
		
		/**
		 * @param chatColors
		 *          - Only put things here if you want to choose from these colors
		 * @return the random color
		 */
		public static ChatColor getColor(ChatColor... chatColors) {
			Random r = new Random();
			ChatColor[] colors;
			if (chatColors.length == 0)
				colors = ChatColor.values();
			else
				colors = chatColors;
			int i = r.nextInt(colors.length);
			while (!colors[i].isColor())
				i = r.nextInt(colors.length);
			ChatColor rc = colors[i];
			return rc;
		}
		
		/**
		 * @param chatColors
		 *          - Only put things here if you want to choose from these
		 *          formats
		 * @return the random format
		 */
		public static ChatColor getFormat(ChatColor... chatColors) {
			Random r = new Random();
			ChatColor[] colors;
			if (chatColors.length == 0)
				colors = ChatColor.values();
			else
				colors = chatColors;
			int i = r.nextInt(colors.length);
			while (!colors[i].isFormat())
				i = r.nextInt(colors.length);
			ChatColor rc = colors[i];
			return rc;
		}
	}
	
	/**
	 * @param string
	 * @return a string with color codes
	 */
	public static String addColor(String string) {
		
		return ChatColor.translateAlternateColorCodes('&', string.replaceAll("&x", "&" + String.valueOf(RandomChatColor.getColor().getChar())).replaceAll("&y", "&" + String.valueOf(RandomChatColor.getFormat().getChar())));
	}
	
	/**
	 * Combine all the args and make a single string starting at the arg
	 * 
	 * @param args
	 * @return
	 */
	public static String combineArgs(String[] args, int startAt) {
		String[] arguments = new String[args.length - startAt];
		for (int j = 0, i = startAt; i != args.length; i++, j++)
			arguments[j] = args[i];
		return StringUtils.join(arguments, " ");
	}
	
	/**
	 * @param string
	 * @return capitalize the first letter of every word, and make all other letters lower case
	 */
	public static String getCapitalized(String line) {
		if (line.contains(" "))
		{
			
			String[] words = line.split(" ");
			line = "";
			
			for (String string : words)
			{
				string = string.toLowerCase();
				line = line + string.replaceFirst(String.valueOf(string.charAt(0)), String.valueOf(string.charAt(0)).toUpperCase());
			}
		}
		else
			line = line.replaceFirst(String.valueOf(line.charAt(0)), String.valueOf(line.charAt(0)).toUpperCase());
		
		return line;
	}
	
	/**
	 * @param Time
	 * @return a long(Seconds) into a formated time message
	 */
	public static String getTime(Long Time) {
		String times = null;
		Long time = Time;
		Long seconds = time;
		long minutes = seconds / 60;
		seconds %= 60;
		if (seconds == 0)
		{
			if (minutes == 0)
				times = "N/A";
			else if (minutes == 1)
				times = minutes + " " + McInfected.getMessanger().getMessage(false, Messages.Game__Time__Measurements__Minute);
			else
				times = minutes + " " + McInfected.getMessanger().getMessage(false, Messages.Game__Time__Measurements__Minutes);
		}
		else if (minutes == 0)
		{
			if (seconds == 1)
				times = seconds + " " + McInfected.getMessanger().getMessage(false, Messages.Game__Time__Measurements__Second);
			else
				times = seconds + " " + McInfected.getMessanger().getMessage(false, Messages.Game__Time__Measurements__Seconds);
		}
		else
			times = minutes + " " + McInfected.getMessanger().getMessage(false, Messages.Game__Time__Measurements__Minutes) + " " + seconds + " " + McInfected.getMessanger().getMessage(false, Messages.Game__Time__Measurements__Seconds);
		return times;
	}
	
}
