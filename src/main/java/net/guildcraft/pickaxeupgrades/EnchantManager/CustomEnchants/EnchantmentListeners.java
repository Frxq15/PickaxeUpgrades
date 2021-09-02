package net.guildcraft.pickaxeupgrades.EnchantManager.CustomEnchants;

import net.guildcraft.pickaxeupgrades.Objects.Pickaxe;
import net.guildcraft.pickaxeupgrades.PickaxeUpgrades;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class EnchantmentListeners implements Listener {
    @EventHandler
    public void onTNT(EntityDamageEvent e) {
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
                //p.getWorld().spawn(p.getLocation(), TNTPrimed.class);
                float size = fc.getInt("ENCHANTMENTS.EXPLOSION.LEVEL_EXPLOSION_SIZES." + lvl);
                p.getWorld().createExplosion(p.getLocation(), size);
            }
        }
    }
}
