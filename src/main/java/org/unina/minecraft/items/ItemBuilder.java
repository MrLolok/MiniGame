package org.unina.minecraft.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ItemBuilder {
    private Material material;
    private String name;
    private List<String> lore;
    private List<ItemFlag> flags;
    private Map<Enchantment, Integer> enchantments;
    private int amount = 1;

    public ItemBuilder setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public ItemBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        return setLore(Arrays.asList(lore));
    }

    public ItemBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder setItemFlags(List<ItemFlag> flags) {
        this.flags = flags;
        return this;
    }

    public ItemBuilder setItemFlags(ItemFlag... flags) {
        return setItemFlags(Arrays.asList(flags));
    }

    public ItemBuilder setEnchantments(Map<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
        return this;
    }

    public ItemStack build() {
        if (material == null)
            throw new IllegalArgumentException("Material cannot be null");
        ItemStack item = new ItemStack(material, amount);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        if (name != null)
            meta.setDisplayName(name);
        if (lore != null)
            meta.setLore(lore);
        if (flags != null)
            for (ItemFlag flag : flags)
                meta.addItemFlags(flag);
        if (enchantments != null)
            for (Map.Entry<Enchantment, Integer> enchantment : enchantments.entrySet())
                meta.addEnchant(enchantment.getKey(), enchantment.getValue(), true);
        return item;
    }
}
