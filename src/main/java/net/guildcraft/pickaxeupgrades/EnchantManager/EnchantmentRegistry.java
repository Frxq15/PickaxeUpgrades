package net.guildcraft.pickaxeupgrades.EnchantManager;

import net.guildcraft.pickaxeupgrades.PickaxeUpgrades;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class EnchantmentRegistry {
    public List<Enchantment> enchants = new ArrayList<>();

    public void unregisterEnchants() {
        enchants.forEach(enchant ->{
            try {
                Field keyField = Enchantment.class.getDeclaredField("byKey");

                keyField.setAccessible(true);
                @SuppressWarnings("unchecked")
                HashMap<NamespacedKey, Enchantment> byKey = (HashMap<NamespacedKey, Enchantment>) keyField.get(null);

                if(byKey.containsKey(enchant.getKey())) {
                    byKey.remove(enchant.getKey());
                }
                Field nameField = Enchantment.class.getDeclaredField("byName");

                nameField.setAccessible(true);
                @SuppressWarnings("unchecked")
                HashMap<String, Enchantment> byName = (HashMap<String, Enchantment>) nameField.get(null);

                if(byName.containsKey(enchant.getName())) {
                    byName.remove(enchant.getName());
                }
            } catch (Exception ignored) { }
        });
    }
    public void registerEnchantment(Enchantment enchantment) {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
            Enchantment.registerEnchantment(enchantment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void register(Enchantment enchant) {
        boolean registered = Arrays.stream(Enchantment.values()).collect(Collectors.toList()).contains(enchant);
        if(!registered) {
            registerEnchantment(enchant);
        }
    }
    public void registration() {
        getEnchants ge = PickaxeUpgrades.getInstance().getEnchantmentsList();
        enchants.add(ge.getTestEnchant());
        enchants.forEach(enchant -> {
            register(enchant);
        });
    }
}
