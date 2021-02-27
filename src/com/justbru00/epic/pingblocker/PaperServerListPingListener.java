package com.justbru00.epic.pingblocker;

import java.util.List;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;

public class PaperServerListPingListener implements Listener {

	private EpicPingBlockerPlugin instance;

	public PaperServerListPingListener(EpicPingBlockerPlugin _instance) {
		instance = _instance;
	}

	@EventHandler
	public void onPaperServerListPing(PaperServerListPingEvent e) {
		String ipAddress = e.getAddress().getHostAddress();

		if (ipAddress == null || ipAddress.equals("")) {
			return;
		}

		List<String> blockedIps = instance.getConfig().getStringList("blocked_ips");
		
		if (blockedIps == null || blockedIps.size() == 0) {
			return;
		}
		
		if (blockedIps.contains(ipAddress)) {
			e.setCancelled(true);
		}		
	}

}
