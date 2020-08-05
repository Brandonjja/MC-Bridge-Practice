package com.brandonjja.bridge;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.brandonjja.bridge.listeners.BlockListener;
import com.brandonjja.bridge.listeners.DamageListener;
import com.brandonjja.bridge.listeners.PlayerConnectionListener;
import com.brandonjja.bridge.listeners.PlayerMoveListener;

public class BridgeMain extends JavaPlugin {
	private static Plugin plugin;
	private static Map<String, Bridger> map = new HashMap<>();
	
	@Override
	public void onEnable() {
		plugin = this;
		getCommand("startbridge").setExecutor(new Commands());
		getCommand("stopbridge").setExecutor(new Commands());
		getCommand("mostblocks").setExecutor(new Commands());
		getCommand("replay").setExecutor(new Commands());
		getCommand("bridge").setExecutor(new Commands());
		getCommand("start").setExecutor(new Commands());
		getCommand("bridgestop").setExecutor(new Commands());
		getCommand("bridgestart").setExecutor(new Commands());
		
		registerEvents(this, new BlockListener());
		registerEvents(this, new PlayerConnectionListener());
		registerEvents(this, new PlayerMoveListener());
		registerEvents(this, new DamageListener());
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			add(p.getName(), new Bridger(p.getName()));
		}
		
		//this.getServer().getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public void onDisable() {
		plugin = null;
	}
	
	public static Plugin getPlugin() {
		return plugin;
	}
	
	public static void add(String name, Bridger b) {
		map.put(name, b);
	}
	
	public static void remove(String name) {
		map.remove(name);
	}
	
	public static Bridger get(String name) {
		return map.get(name);
	}
	
	public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
		for (Listener listener : listeners) {
			Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
		}
	}
}
