package com.brandonjja.bridge.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import com.brandonjja.bridge.BridgeMain;
import com.brandonjja.bridge.Bridger;

public class DamageListener implements Listener {
	
	@EventHandler
	public void onDamage(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		Player player = (Player) e.getEntity();
		Bridger p = BridgeMain.get(player.getName());
		if (p.isBridging()) {
			e.setCancelled(true);
		}
	}
}
