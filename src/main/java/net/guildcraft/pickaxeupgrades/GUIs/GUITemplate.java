package net.guildcraft.pickaxeupgrades.GUIs;

import net.guildcraft.gctokenmanager.GCTokenManager;
import net.guildcraft.gctokenmanager.SQLManagement.PlayerData;
import net.guildcraft.pickaxeupgrades.Objects.Pickaxe;
import net.guildcraft.pickaxeupgrades.PickaxeUpgrades;
import org.bukkit.Bukkit;
import org.bukkit.Instrument;
import org.bukkit.Material;
import org.bukkit.Note;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class GUITemplate {
    public static Map<UUID, GUITemplate> inventoriesByUUID = new HashMap<>();
    public static Map<UUID, UUID> openInventories = new HashMap<>();

    private final Inventory inventory;
    private Map<Integer, GUIAction> actions;
    private UUID uuid;

    public GUITemplate(int rows,String invname) {
        uuid = UUID.randomUUID();
        inventory = Bukkit.createInventory(null, 9 * rows, invname);
        actions = new HashMap<>();
        inventoriesByUUID.put(getUUID(), this);

    }

    public Inventory getInventory() {
        return inventory;
    }

    public interface GUIAction {
        void click(Player player);
    }

    protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(PickaxeUpgrades.colourize(Arrays.asList(lore)));
        item.setItemMeta(meta);
        return item;
    }

    protected ItemStack createGuiItem(final Material material, byte subId, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1, subId);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        item.setItemMeta(meta);
        return item;
    }

    public void setItem(int slot, ItemStack stack, GUIAction action){
        inventory.setItem(slot, stack);
        if (action != null){
            actions.put(slot, action);
        }
    }

    public void setPickaxe(int slot, ItemStack stack, GUIAction action){
        inventory.setItem(slot, stack);
        if (action != null){
            actions.put(slot, action);
        }
    }

    public void setItem(int slot, ItemStack stack){
        setItem(slot, stack, null);
    }

    public void setPickaxe(int slot){
        ItemStack pick = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta pickmeta = pick.getItemMeta();
        pickmeta.addEnchant(Enchantment.DURABILITY, 1, true);
        pickmeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        setItem(slot, pick, null);
    }
    public void setPlayerPickaxe(Player p, int slot){
        ItemStack pick = p.getInventory().getItemInMainHand();
        setItem(slot, pick, null);
    }

    public void open(Player player) {
        player.playNote(player.getLocation(), Instrument.PIANO, Note.natural(1, Note.Tone.A));
        player.openInventory(inventory);
        openInventories.put(player.getUniqueId(), getUUID());
    }

    public UUID getUUID() {
        return uuid;
    }

    public static Map<UUID, GUITemplate> getInventoriesByUUID() {
        return inventoriesByUUID;
    }

    public static Map<UUID, UUID> getOpenInventories() {
        return openInventories;
    }

    public Map<Integer, GUIAction> getActions() {
        return actions;
    }

    public void delete() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            UUID u = openInventories.get(p.getUniqueId());
            if (u.equals(getUUID())) {
                p.closeInventory();
            }
        }
        inventoriesByUUID.remove(getUUID());
    }

    protected ItemStack createEnchantedGuiItem(final Material material, final String name, final String... lore) {
        final ItemStack item = new ItemStack(material, 1);
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        meta.addEnchant(Enchantment.DURABILITY, 1, false);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }
    public ItemStack getItemFromConfig(String path, Player p) {
        FileConfiguration fc = PickaxeUpgrades.getInstance().getConfig();
        List<String> lore = new ArrayList<>();
        ItemStack item = new ItemStack(Material.valueOf(fc.getString(path+".MATERIAL")));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(PickaxeUpgrades.formatMsg(path+".NAME"));
        PlayerData pd = PlayerData.getPlayerData(p.getUniqueId());
        for(String line : fc.getStringList(path+".LORE")) {
            line = line.replace("%tokens%", pd.getTokens()+"");
            lore.add(line);
        }
        meta.setLore(PickaxeUpgrades.colourize(lore));
        if(fc.getBoolean(path+".GLOW")) {
            meta.addEnchant(Enchantment.DURABILITY, 1, false);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(meta);
        return item;
    }
}
