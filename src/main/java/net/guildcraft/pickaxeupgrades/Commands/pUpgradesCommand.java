package net.guildcraft.pickaxeupgrades.Commands;

import net.guildcraft.pickaxeupgrades.Objects.Pickaxe;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class pUpgradesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        ItemStack i = p.getInventory().getItemInMainHand();
        Pickaxe pick = new Pickaxe(i);
        Bukkit.broadcastMessage(pick.getName());
        Bukkit.broadcastMessage(pick.getEnchantLevel(Enchantment.DIG_SPEED)+"");
        return true;
    }
}
