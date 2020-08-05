package com.brandonjja.bridge.listeners;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import com.brandonjja.bridge.BridgeMain;
import com.brandonjja.bridge.Bridger;

public class BlockListener implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		Player player = e.getPlayer();
		Bridger p = BridgeMain.get(player.getName());
		if (p.isBridging()) {
			
			if (p.isReplaying()) {
				e.setCancelled(true);
				List<Block> replay = p.getReplayList();
				for (Block b : replay) {
					b.setType(Material.AIR);
				}
				p.setReplay(false);
				p.resetBridge(player);
				p.teleport(player);
				p.clearReplay();
				return;
			}
			
			Location loc = e.getBlock().getLocation();
			//int y = loc.getBlockY() - 1;
			if (player.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() - 1, loc.getBlockZ()).getType() == Material.GLASS) {
				e.setCancelled(true);
				return;
			}
			
			if (player.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() - 2, loc.getBlockZ()).getType() == Material.GLASS) {
				e.setCancelled(true);
				return;
			}
			
			if (player.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() - 1, loc.getBlockZ()).getTypeId() == Material.STAINED_GLASS.getId()) {
				e.setCancelled(true);
				return;
			}
			
			if (player.getWorld().getBlockAt(loc.getBlockX(), loc.getBlockY() - 2, loc.getBlockZ()).getTypeId() == Material.STAINED_GLASS.getId()) {
				e.setCancelled(true);
				return;
			}
			
			p.addBlock(e.getBlock());
			p.incBlocks();
			
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
			
			player.getInventory().addItem(new ItemStack(Material.WOOD, 2));
			player.getInventory().removeItem(new ItemStack(Material.WOOD, 2));
			
			player.getInventory().addItem(new ItemStack(Material.STONE, 2));
			player.getInventory().removeItem(new ItemStack(Material.STONE, 2));
			
			player.getInventory().addItem(new ItemStack(Material.LOG, 2));
			player.getInventory().removeItem(new ItemStack(Material.LOG, 2));
			
			player.getInventory().addItem(new ItemStack(Material.WOOL, 2));
			player.getInventory().removeItem(new ItemStack(Material.WOOL, 2));
			
			player.getInventory().addItem(yellow);
			player.getInventory().removeItem(yellow);
			
			player.getInventory().addItem(green);
			player.getInventory().removeItem(green);
			
			player.getInventory().addItem(aqua);
			player.getInventory().removeItem(aqua);
			
			player.getInventory().addItem(purple);
			player.getInventory().removeItem(purple);
			
			player.getInventory().addItem(blue);
			player.getInventory().removeItem(blue);
			
			player.getInventory().addItem(red);
			player.getInventory().removeItem(red);
			
			player.setHealth(20);
			player.setFoodLevel(20);
			
			int x = Math.abs(Math.abs(e.getBlock().getLocation().getBlockX()) - Math.abs(p.getBridgeLocation().getBlockX()));
			if (x > p.furthestX) {
				p.furthestX = x;
			}
			
			int z = Math.abs(Math.abs(e.getBlock().getLocation().getBlockZ()) - Math.abs(p.getBridgeLocation().getBlockZ()));
			if (z > p.furthestZ) {
				p.furthestZ = z;
			}
			
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();
		Bridger p = BridgeMain.get(player.getName());
		if (p.isBridging()) {
			if (e.getBlock().getType() == Material.GLASS || e.getBlock().getType() == Material.STAINED_GLASS) {
				e.setCancelled(true);
			}
		}
	}
}
