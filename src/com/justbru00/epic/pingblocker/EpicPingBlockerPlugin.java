package com.justbru00.epic.pingblocker;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class EpicPingBlockerPlugin extends JavaPlugin {

	public static ConsoleCommandSender console = Bukkit.getConsoleSender();
	public static Logger logger = Bukkit.getLogger();
	public static String prefix = Messager.color("&8[&bEpic&fPingBlocker&8] &6");

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (command.getName().equalsIgnoreCase("epicpingblocker")) {
			if (sender.hasPermission("epicpingblocker.use")) {
				// /epicpingblocker <add,remove,list>
				if (args.length >= 1) {
					if (args[0].equalsIgnoreCase("add")) {
						if (args.length == 2) {
							String ipAddress = args[1];
							if (ipAddress.split("[.]").length == 4) {
								List<String> blockList = getConfig().getStringList("blocked_ips");

								if (blockList == null || blockList.size() == 0) {
									// Empty List
									blockList = new ArrayList<String>();
								}

								blockList.add(ipAddress);

								getConfig().set("blocked_ips", blockList);
								saveConfig();
								Messager.msgSender(String.format(
										"&aAdded the IP address '%s' to the blocked IP list successfully.", ipAddress),
										sender);
								return true;
							} else {
								Messager.msgSender(
										"&cThe provided IP address doesn't appear to be valid. Check that you have entered it correctly.",
										sender);
								return true;
							}
						} else {
							Messager.msgSender("&cIncorrect arguments. /epicpingblocker add <IP_Address>", sender);
							return true;
						}
					} else if (args[0].equalsIgnoreCase("remove")) {
						if (args.length == 2) {
							String ipAddress = args[1];
							if (ipAddress.split("[.]").length == 4) {
								List<String> blockList = getConfig().getStringList("blocked_ips");

								if (blockList == null || blockList.size() == 0) {
									// Empty List
									Messager.msgSender("&cThe blocked IP addresses list is empty.", sender);
									return true;
								}

								if (blockList.contains(ipAddress)) {
									blockList.remove(ipAddress);
									getConfig().set("blocked_ips", blockList);
									saveConfig();
									Messager.msgSender(String.format(
											"&aSuccessfully removed the IP address '%s' from the blocked IP addresses list.",
											ipAddress), sender);
									return true;
								} else {
									Messager.msgSender("&cThe blocked IP addresses list doesn't contain that IP.",
											sender);
									return true;
								}
							} else {
								Messager.msgSender(
										"&cThe provided IP address doesn't appear to be valid. Check that you have entered it correctly.",
										sender);
								return true;
							}
						} else {
							Messager.msgSender("&cIncorrect arguments. /epicpingblocker remove <IP_Address>", sender);
							return true;
						}
					} else if (args[0].equalsIgnoreCase("list")) {
						List<String> blockList = getConfig().getStringList("blocked_ips");

						if (blockList == null || blockList.size() == 0) {
							Messager.msgSender("&6The blocked IP addresses list is empty.", sender);
							return true;
						} else {
							Messager.msgSender("&6Blocked IP Addresses:", sender);
							for (String s : blockList) {
								Messager.msgSender("&6- " + s, sender);
							}
							return true;
						}
					} else {
						Messager.msgSender(
								"&cSorry you must provide at least one argument for this command. /epicpingblocker <add,remove,list>",
								sender);
						return true;
					}
				} else {
					Messager.msgSender(
							"&cSorry you must provide at least one argument for this command. /epicpingblocker <add,remove,list>",
							sender);
					return true;
				}
			} else {
				Messager.msgSender("&cSorry you don't have permission.", sender);
				return true;
			}
		}

		return false;
	}

	@Override
	public void onDisable() {

	}

	@Override
	public void onEnable() {
		saveDefaultConfig();

		if (Bukkit.getVersion().contains("Spigot")) {
			Messager.msgConsole("&cThis plugin will only run on paperspigot! Disabling myself now.");
			Bukkit.getPluginManager().disablePlugin(this);
		} else {
			// PaperSpigot server.
			Messager.msgConsole(
					"&aEnabling EpicPingBlocker version " + getDescription().getVersion() + " created by JustBru00.");
			Bukkit.getPluginManager().registerEvents(new PaperServerListPingListener(this), this);
		}
	}

}
