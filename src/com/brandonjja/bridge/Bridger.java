package com.brandonjja.bridge;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class Bridger {
	//private String name;
	private boolean bridging, replaying = false;
	private List<Block> blocks, replay;
	private Location beforeBridgeLocation, afterBridgeLocation;
	private int bridgeY, teleportAtY;
	private int blocksPlaced = 0, mostBlocks = 0;
	
	public int furthestX = 0, furthestZ = 0;
	
	public static int offSet = 3;
	
	
	public Bridger(String name) {
		//this.name = name;
		this.bridging = false;
		blocks = new ArrayList<>();
		replay = new ArrayList<>();
	}
	
	public void clearBlocks() {
		for (Block b : blocks) {
			b.setType(Material.AIR);
		}
		blocks.clear();
	}
	
	public boolean isBridging() {
		return this.bridging;
	}
	
	public void setBeforeBridgeLocation(Location loc) {
		this.beforeBridgeLocation = loc;
		setAfterBridgeLocation(loc);
	}
	
	public Location getBeforeBridgeLocation() {
		return this.beforeBridgeLocation;
	}
	
	private void setAfterBridgeLocation(Location loc) {
		//Location l = new Location(loc.getWorld(), loc.getX(), loc.getY() + 20, loc.getZ());
		World w = loc.getWorld();
		Location l = new Location(loc.getWorld(), w.getSpawnLocation().getX(), 20, w.getSpawnLocation().getZ() + offSet);
		//loc.setY(loc.getY() + 20);
		this.afterBridgeLocation = l;
		this.bridgeY = l.getBlockY();
		this.teleportAtY = this.bridgeY - 2;
	}
	
	public Location getBridgeLocation() {
		return this.afterBridgeLocation;
	}
	
	public int getTeleportY() {
		return this.teleportAtY;
	}
	
	@SuppressWarnings("unused")
	private int calcX(Player player) {
		return Math.abs(Math.abs(player.getLocation().getBlockX()) - Math.abs(this.afterBridgeLocation.getBlockX()));
	}
	
	@SuppressWarnings("unused")
	private int calcZ(Player player) {
		return Math.abs(Math.abs(player.getLocation().getBlockZ()) - Math.abs(this.afterBridgeLocation.getBlockZ()));
	}
	
	private double calcDiag(Player player, int x, int z) {
		//return Math.sqrt(Math.pow(x, 2) + Math.pow(x, 2));
		return Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2));
	}
	
	public void addBlock(Block b) {
		blocks.add(b);
	}
	
	public void resetBridge(Player player) {
		int x = furthestX;//calcX(player);
		int z = furthestZ;//calcZ(player);
		player.teleport(getBridgeLocation());
		
		if (!this.replaying) {
			this.replay.clear();
		}
		
		for (Block b : blocks) {
			replay.add(b);
		}
		
		clearBlocks();
		//player.sendMessage(ChatColor.YELLOW + "________________________");
		player.sendMessage(ChatColor.YELLOW + "--------------");
		player.sendMessage(ChatColor.YELLOW + "Blocks Placed: " + this.getBlocks());
		
		if (x == 0 && z == 0) {
			this.furthestX = 0;
			this.furthestZ = 0;
			return;
		}
		
		if (x == 0) {
			player.sendMessage(ChatColor.YELLOW + "Z Distance: " + z);
		} else if (z == 0) {
			player.sendMessage(ChatColor.YELLOW + "X Distance: " + x);
		} else {
			player.sendMessage(ChatColor.YELLOW + "X Distance: " + x);
			player.sendMessage(ChatColor.YELLOW + "Z Distance: " + z);
			if (x == z) {
				player.sendMessage(ChatColor.YELLOW + "Diagonal Dis: " + x);
			} else {
				player.sendMessage(ChatColor.YELLOW + "Diagonal Dis: " + (int) calcDiag(player, x, z));
			}
		}
		
		this.furthestX = 0;
		this.furthestZ = 0;
	}
	
	public void incBlocks() {
		this.blocksPlaced++;
		if (this.blocksPlaced > this.mostBlocks) {
			this.mostBlocks = this.blocksPlaced;
		}
	}
	
	private int getBlocks() {
		int b = this.blocksPlaced;
		this.blocksPlaced = 0;
		return b;
	}
	
	@SuppressWarnings("deprecation")
	public void teleport(Player p) {
		Block b = p.getWorld().getBlockAt(afterBridgeLocation.getBlockX(), this.bridgeY - 1, afterBridgeLocation.getBlockZ());
		//ItemStack glass = new ItemStack(Material.STAINED_GLASS);
		//glass.setDurability((short) 15);
		b.setTypeIdAndData(Material.STAINED_GLASS.getId(), DyeColor.BLACK.getData(), true);
		//b.setType(glass.getType());
		//b.setType(Material.STAINED_GLASS);
		p.teleport(afterBridgeLocation);
	}
	
	public void stopBridge(Player p) {
		Block b = p.getWorld().getBlockAt(afterBridgeLocation.getBlockX(), this.bridgeY - 1, afterBridgeLocation.getBlockZ());
		b.setType(Material.AIR);
		p.teleport(this.beforeBridgeLocation);
		clearBlocks();
	}
	
	public void setBridging(boolean b) {
		this.bridging = b;
	}
	
	public int getMostBlocks() {
		return this.mostBlocks;
	}
	
	public int getNumOfPlaced() {
		return this.blocksPlaced;
	}
	
	public int replay() {
		this.replaying = true;
		for (Block b : replay) {
			b.setType(Material.BEDROCK);
		}
		return this.replay.size();
	}
	
	public boolean isReplaying() {
		return this.replaying;
	}
	
	public List<Block> getReplayList() {
		return this.replay;
	}
	
	public void setReplay(boolean b) {
		this.replaying = b;
	}
	
	public void clearReplay() {
		this.replay.clear();
	}
	
	public boolean canReplay() {
		if (this.replay.size() >= 1) {
			return true;
		}
		return false;
	}
	
}
