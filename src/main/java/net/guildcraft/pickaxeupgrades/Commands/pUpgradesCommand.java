package net.guildcraft.pickaxeupgrades.Commands;

import net.guildcraft.pickaxeupgrades.Objects.Pickaxe;
import net.guildcraft.pickaxeupgrades.PickaxeUpgrades;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class pUpgradesCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            PickaxeUpgrades.getInstance().log("This command cannot be executed from console.");
            return true;
        }
        Player p = (Player) sender;
        if(args.length == 0) {
            ItemStack pick = p.getInventory().getItemInMainHand();
            ItemMeta meta = pick.getItemMeta();
            meta.addEnchant(Enchantment.getByKey(PickaxeUpgrades.getInstance().getEnchantmentsList().getExplosionEnchant().getKey()), 23, true);
            pick.setItemMeta(meta);
            PickaxeUpgrades.getInstance().getEnchantmentsManager().addEnchantmentLore(pick, "test", 23);
            Bukkit.broadcastMessage(PickaxeUpgrades.getInstance().getEnchantmentsManager().getCustomLevel(pick, "test")+"");
        }
        if(args.length == 1) {
            String type = args[0];
            switch(type.toLowerCase()) {
                case "history":
                    PickaxeUpgrades.getInstance().getTransactionManager(p).getLogs(p);
                    return true;
                default:
                    p.sendMessage(PickaxeUpgrades.colourize("Invalid subcommand specified, use /pugrades help"));
                    return true;
            }
        }
        return true;
    }
}
