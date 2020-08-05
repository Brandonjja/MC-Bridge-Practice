package com.brandonjja.bridge.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.brandonjja.bridge.BridgeMain;
import com.brandonjja.bridge.Bridger;

public class PlayerMoveListener implements Listener {
	
	private boolean getMovedBlock(PlayerMoveEvent e) {
		if (e.getFrom().getBlockX() != e.getTo().getBlockX() || e.getFrom().getBlockZ() != e.getTo().getBlockZ() || e.getFrom().getBlockY() != e.getTo().getBlockY()) {
			return true;
		}
		return false;
	}
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		if (getMovedBlock(e)) {
			Player player = e.getPlayer();
			Bridger p = BridgeMain.get(player.getName());
			if (!p.isBridging()) {
				return;
			}
			if (e.getTo().getBlockY() <= p.getTeleportY()) {
				p.resetBridge(player);
				//player.teleport(p.getBridgeLocation());
			}
		}
	}
}
