package net.guildcraft.pickaxeupgrades.EnchantManager;

import net.guildcraft.pickaxeupgrades.Objects.Pickaxe;
import net.guildcraft.pickaxeupgrades.PickaxeUpgrades;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class EnchantmentListeners implements Listener {
    @EventHandler
    public void onTNT(EntityDamageEvent e) {
        if(!(e instanceof Player)) {
            return;
        }
        if(e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION) {
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void explosionEnchant(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Pickaxe pick = new Pickaxe(p.getInventory().getItemInMainHand());
        if(pick.hasEnchant("EXPLOSION")) {
            FileConfiguration fc = PickaxeUpgrades.getInstance().getFileManager().getUpgradesFile();
            int lvl = pick.getEnchantLevel("EXPLOSION");
            int chance = fc.getInt("ENCHANTMENTS.EXPLOSION.PER_LEVEL_CHANCES." + lvl);
            double random = Math.random() * 100;
            if (random <= chance) {
                float size = fc.getInt("ENCHANTMENTS.EXPLOSION.LEVEL_EXPLOSION_SIZES." + lvl);
                p.getWorld().createExplosion(p.getLocation(), size, false, true, p);
            }
        }
    }
    @EventHandler
    public void explosionBlockGiver(EntityExplodeEvent e) {
        if(!(e instanceof Player)) {
            return;
        }
        if(e.getLocation().equals(e.getLocation())) {
            Player p = (Player) e.getEntity();
                e.blockList().forEach(block -> {
                ItemStack item = new ItemStack(block.getType());
                p.getInventory().addItem(item);
                block.setType(Material.AIR);
            });
        }
    }
    @EventHandler
    public void speedEnchant(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Pickaxe pick = new Pickaxe(p.getInventory().getItemInMainHand());
        if(pick.hasEnchant("SPEED")) {
            FileConfiguration fc = PickaxeUpgrades.getInstance().getFileManager().getUpgradesFile();
            int lvl = pick.getEnchantLevel("SPEED");
            int chance = fc.getInt("ENCHANTMENTS.SPEED.PER_LEVEL_CHANCES." + lvl);
            double random = Math.random() * 100;
            if (random <= chance) {
                int level = fc.getInt("ENCHANTMENTS.SPEED.LEVEL_SPEED_TYPE." + lvl);
                int time = fc.getInt("ENCHANTMENTS.SPEED.LEVEL_SPEED_TIME." + lvl);
                p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, time * 20, level));
            }
        }
    }
    @EventHandler
    public void hasteEnchant(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Pickaxe pick = new Pickaxe(p.getInventory().getItemInMainHand());
        if(pick.hasEnchant("HASTE")) {
            FileConfiguration fc = PickaxeUpgrades.getInstance().getFileManager().getUpgradesFile();
            int lvl = pick.getEnchantLevel("HASTE");
            int chance = fc.getInt("ENCHANTMENTS.HASTE.PER_LEVEL_CHANCES." + lvl);
            double random = Math.random() * 100;
            if (random <= chance) {
                int level = fc.getInt("ENCHANTMENTS.HASTE.LEVEL_SPEED_TYPE." + lvl);
                int time = fc.getInt("ENCHANTMENTS.HASTE.LEVEL_SPEED_TIME." + lvl);
                p.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, time * 20, level));
            }
        }
    }
}
