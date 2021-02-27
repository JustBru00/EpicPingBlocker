package com.justbru00.epic.pingblocker;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * 
 * @author Justin Brubaker
 *
 */
public class Messager {

	public static String color(String uncolored) {
		return ChatColor.translateAlternateColorCodes('&', uncolored);
	}
	
	public static void msgConsole(String msg) {
		if (EpicPingBlockerPlugin.console != null) {
			EpicPingBlockerPlugin.console.sendMessage(EpicPingBlockerPlugin.prefix + Messager.color(msg));
		} else {
			EpicPingBlockerPlugin.logger.info(ChatColor.stripColor(Messager.color(msg)));
		}
	}

	public static void msgPlayer(String msg, Player player) {
		player.sendMessage(EpicPingBlockerPlugin.prefix + Messager.color(msg));
	}

	public static void msgSender(String msg, CommandSender sender) {
		sender.sendMessage(EpicPingBlockerPlugin.prefix + Messager.color(msg));
	}
}
