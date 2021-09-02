package net.guildcraft.pickaxeupgrades.EnchantManager;

import net.guildcraft.pickaxeupgrades.PickaxeUpgrades;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Enchantments {
    public int getMaxLevel(String enchant) {
        return PickaxeUpgrades.getInstance().getFileManager().getUpgradesFile().getInt("ENCHANTMENTS."+enchant+".MAXIMUM_LEVEL");
    }
    public String getAllEnchantments() {
        List<String> enchants = new ArrayList<>();
        PickaxeUpgrades.getInstance().getFileManager().getUpgradesFile()
                .getConfigurationSection("ENCHANTMENTS").getKeys(false).forEach(enchant -> {
            enchants.add(enchant);
        });
        return enchants.toString().replace("[", "").replace("]", "");
    }
    public String getDescription(String enchant) {
        return PickaxeUpgrades.getInstance().getFileManager().getUpgradesFile().getString("ENCHANTMENTS."+enchant+".DESCRIPTION");
    }
    public String getEnchantmentName(Enchantment enchant) {
        return PickaxeUpgrades.getInstance().getFileManager().getUpgradesFile().getString("ENCHANTMENTS."+enchant.getName()+".NAME");
    }
    public Integer getCustomLevel(ItemStack stack, String enchantment) {
        AtomicInteger level = new AtomicInteger(0);
        stack.getEnchantments().forEach((enchant, lvl) -> {
            if(enchant.equals(Enchantment.getByName(enchantment))) {
                level.set(lvl);
            }
        });
        return level.get();
    }
    public boolean hasEnchant(ItemStack stack, String enchantment) {
        AtomicBoolean result = new AtomicBoolean(false);
        stack.getEnchantments().forEach((enchant, lvl) -> {
            if(enchant.equals(Enchantment.getByName(enchantment))) {
                result.set(true);
            }
        });
        return result.get();

    }
    public int getEnchantLoreIndex(ItemStack stack, String enchantment) {
        List<String> lore = new ArrayList<>();
        ItemMeta meta = stack.getItemMeta();
        if(meta.getLore() == null) {
            return 0;
        }
        lore = meta.getLore();
        int index = 0;
        for(String line : lore) {
            if(!line.contains(enchantment)) {
                index++;
            }
        }
        return index;
    }
    public void setEnchantmentLore(ItemStack stack, String enchant, int level) {
        int index = getEnchantLoreIndex(stack, enchant);
        ItemMeta meta = stack.getItemMeta();
        if(index == 0) {
            List<String> lore = new ArrayList<>();
            if(level > 10) {
                lore.add(index, "§7" + enchant + " enchantment.level." + level);
                meta.setLore(lore);
                stack.setItemMeta(meta);
                return;
            }
            lore.add(index, "§7" + enchant + " " + RomanNumerals(level));
            meta.setLore(lore);
            stack.setItemMeta(meta);
            return;
            }
        //item has a lore
        List<String> lore = meta.getLore();
        if(level > 10) {
            lore.set(index, "§7" + enchant + " enchantment.level." + level);
            meta.setLore(lore);
            stack.setItemMeta(meta);
            return;
        }
        lore.set(index, "§7" + enchant + " " + RomanNumerals(level));
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return;

    }
    public void addEnchantmentLore(ItemStack stack, String enchant, int level) {
        if(hasEnchant(stack, enchant)) {
            setEnchantmentLore(stack, enchant, level);
            return;
        }
        ItemMeta meta = stack.getItemMeta();
        List<String> lore = new ArrayList<>();
        if(meta.getLore() != null) {
            lore = meta.getLore();
        }
        int index = 0;
        boolean blank = false;
            while (blank == false) { //Skip to the first blank line in lore
                for(String line : lore) {
                    if(!line.equalsIgnoreCase("")) {
                        index++;
                    }
                }
                blank = true;
        }
        if(level > 10) {
            lore.add(index, "§7" + enchant + " enchantment.level." + level);
            meta.setLore(lore);
            stack.setItemMeta(meta);
            return;
            }
        lore.add(index, "§7" + enchant + " " + RomanNumerals(level));
        meta.setLore(lore);
        stack.setItemMeta(meta);
        return;
        }
    public static String RomanNumerals(int Int) {
        LinkedHashMap<String, Integer> roman_numerals = new LinkedHashMap<String, Integer>();
        roman_numerals.put("M", 1000);
        roman_numerals.put("CM", 900);
        roman_numerals.put("D", 500);
        roman_numerals.put("CD", 400);
        roman_numerals.put("C", 100);
        roman_numerals.put("XC", 90);
        roman_numerals.put("L", 50);
        roman_numerals.put("XL", 40);
        roman_numerals.put("X", 10);
        roman_numerals.put("IX", 9);
        roman_numerals.put("V", 5);
        roman_numerals.put("IV", 4);
        roman_numerals.put("I", 1);
        String res = "";
        for(Map.Entry<String, Integer> entry : roman_numerals.entrySet()){
            int matches = Int/entry.getValue();
            res += repeat(entry.getKey(), matches);
            Int = Int % entry.getValue();
        }
        return res;
    }
    public static String repeat(String s, int n) {
        if(s == null) {
            return null;
        }
        final StringBuilder sb = new StringBuilder();
        for(int i = 0; i < n; i++) {
            sb.append(s);
        }
        return sb.toString();
        }
    }
