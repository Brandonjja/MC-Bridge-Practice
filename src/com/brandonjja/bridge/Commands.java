package com.brandonjja.bridge;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class Commands implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String c, String[] args) {
		if (!(sender instanceof Player)) {
			return false;
		}
		
		Player player = (Player) sender;
		String name = player.getName();
		
		Bridger p = BridgeMain.get(name);
		
		if (args.length == 1) {
			if ((c.equalsIgnoreCase("bridge") && args[0].equalsIgnoreCase("start")) || (c.equalsIgnoreCase("start") && args[0].equalsIgnoreCase("bridge"))) {
				if (p.isBridging()) {
					player.sendMessage(ChatColor.YELLOW + "You are already in Bridge Mode.");
					return true;
				}
				
				Location loc = player.getLocation();
				p.setBeforeBridgeLocation(loc);
				p.teleport(player);
				p.setBridging(true);
				// white purple blue green
				
				ItemStack yellow = new ItemStack(Material.WOOL, 32);
				yellow.setDurability((short) 4);
				
				ItemStack green = new ItemStack(Material.WOOL, 32);
				green.setDurability((short) 5);
				
				ItemStack aqua = new ItemStack(Material.WOOL, 32);
				aqua.setDurability((short) 3);
				
				ItemStack purple = new ItemStack(Material.WOOL, 32);
				purple.setDurability((short) 10);
				
				ItemStack blue = new ItemStack(Material.WOOL, 32);
				blue.setDurability((short) 11);
				
				ItemStack red = new ItemStack(Material.WOOL, 32);
				red.setDurability((short) 14);
				
				
				player.getInventory().addItem(new ItemStack(Material.WOOD, 32));
				player.getInventory().addItem(new ItemStack(Material.LOG, 32));
				player.getInventory().addItem(new ItemStack(Material.STONE, 32));
				//player.getInventory().addItem(new ItemStack(Material.WOOL, 32));
				
				player.getInventory().addItem(yellow);
				player.getInventory().addItem(green);
				player.getInventory().addItem(aqua);
				player.getInventory().addItem(purple);
				player.getInventory().addItem(blue);
				player.getInventory().addItem(red);
				
				player.setHealth(20);
				player.setFoodLevel(20);
				
				Bridger.offSet += 3;
				
				return true;
			}
		}
		
		if (c.equalsIgnoreCase("startbridge") || c.equalsIgnoreCase("bridgestart")) {
			if (p.isBridging()) {
				player.sendMessage(ChatColor.YELLOW + "You are already in Bridge Mode.");
				return true;
			}
			
			Location loc = player.getLocation();
			p.setBeforeBridgeLocation(loc);
			p.teleport(player);
			p.setBridging(true);
			// white purple blue green
			
			ItemStack yellow = new ItemStack(Material.WOOL, 32);
			yellow.setDurability((short) 4);
			
			ItemStack green = new ItemStack(Material.WOOL, 32);
			green.setDurability((short) 5);
			
			ItemStack aqua = new ItemStack(Material.WOOL, 32);
			aqua.setDurability((short) 3);
			
			ItemStack purple = new ItemStack(Material.WOOL, 32);
			purple.setDurability((short) 10);
			
			ItemStack blue = new ItemStack(Material.WOOL, 32);
			blue.setDurability((short) 11);
			
			ItemStack red = new ItemStack(Material.WOOL, 32);
			red.setDurability((short) 14);
			
			
			player.getInventory().addItem(new ItemStack(Material.WOOD, 32));
			player.getInventory().addItem(new ItemStack(Material.LOG, 32));
			player.getInventory().addItem(new ItemStack(Material.STONE, 32));
			//player.getInventory().addItem(new ItemStack(Material.WOOL, 32));
			
			player.getInventory().addItem(yellow);
			player.getInventory().addItem(green);
			player.getInventory().addItem(aqua);
			player.getInventory().addItem(purple);
			player.getInventory().addItem(blue);
			player.getInventory().addItem(red);
			
			player.setHealth(20);
			player.setFoodLevel(20);
			
			Bridger.offSet += 3;
			
			return true;
		}
		
		if (args.length == 1) {
			if (c.equalsIgnoreCase("bridge") && args[0].equalsIgnoreCase("stop")) {
				if (!p.isBridging()) {
					player.sendMessage(ChatColor.YELLOW + "You not in Bridge Mode.");
					return true;
				}
				p.stopBridge(player);
				//player.teleport(p.getBeforeBridgeLocation());
				p.setBridging(false);
				player.getInventory().clear();
				
				Bridger.offSet -= 3;
				
				return true;
			}
		}
		
		if (c.equalsIgnoreCase("stopbridge") || c.equalsIgnoreCase("bridgestop")) {
			if (!p.isBridging()) {
				player.sendMessage(ChatColor.YELLOW + "You not in Bridge Mode.");
				return true;
			}
			p.stopBridge(player);
			//player.teleport(p.getBeforeBridgeLocation());
			p.setBridging(false);
			player.getInventory().clear();
			
			Bridger.offSet -= 3;
			
			return true;
		}
		
		if (c.equalsIgnoreCase("mostblocks")) {
			player.sendMessage(ChatColor.YELLOW + "Most Blocks Bridged: " + p.getMostBlocks());
			return true;
		}
		
		if (c.equalsIgnoreCase("replay")) {
			if (!p.isBridging()) {
				return true;
			}
			
			if (!p.canReplay()) {
				player.sendMessage(ChatColor.RED + "No bridge to replay.");
				return true;
			}
			
			if (p.isReplaying()) {
				player.sendMessage(ChatColor.RED + "Already displaying your last bridge.");
				return true;
			}
			if (p.getNumOfPlaced() > 0) {
				player.sendMessage(ChatColor.YELLOW + "Please restart your bridge to use /replay");
				return true;
			}
			int blocks = p.replay();
			player.sendMessage(ChatColor.GREEN + "Displaying your last bridge (" + blocks + " blocks placed).");
			player.sendMessage(ChatColor.GREEN + "Place a block to restart bridging.");
			return true;
		}
		
		
		
		return false;
	}
	
}
