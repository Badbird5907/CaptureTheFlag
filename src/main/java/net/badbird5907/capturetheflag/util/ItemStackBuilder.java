package net.badbird5907.capturetheflag.util;

import net.badbird5907.capturetheflag.CaptureTheFlag;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Stream.of;
import static org.bukkit.Bukkit.getPluginManager;

/**
 * This is a chainable builder for {@link ItemStack}s in {@link Bukkit}
 * <br>
 * Example Usage:<br>
 * {@code ItemStack is = new ItemStackBuilder(Material.LEATHER_HELMET).amount(2).data(4).durability(4).enchantment(Enchantment.ARROW_INFINITE).enchantment(Enchantment.LUCK, 2).name(ChatColor.RED + "the name").lore(ChatColor.GREEN + "line 1").lore(ChatColor.BLUE + "line 2").color(Color.MAROON).build();}
 *
 * @author MiniDigger
 * @version 1.2
 */
public class ItemStackBuilder implements Listener {
    private static final JavaPlugin plugin = CaptureTheFlag.getInstance();
    private static final HashMap<String, PotionEffect> effects = new HashMap<>();
    private static boolean listener = false;
    private final ItemStack is;

    /**
     * Inits the builder with the given {@link Material}
     *
     * @param mat the {@link Material} to start the builder from
     * @since 1.0
     */
    public ItemStackBuilder(final Material mat) {
        is = new ItemStack(mat);
    }

    /**
     * Inits the builder with the given {@link ItemStack}
     *
     * @param is the {@link ItemStack} to start the builder from
     * @since 1.0
     */
    public ItemStackBuilder(final ItemStack is) {
        this.is = is;
    }

    /**
     * Changes the amount of the {@link ItemStack}
     *
     * @param amount the new amount to set
     * @return this builder for chaining
     * @since 1.0
     */
    public ItemStackBuilder amount(final int amount) {
        is.setAmount(amount);
        return this;
    }

    /**
     * Changes the display name of the {@link ItemStack}
     *
     * @param name the new display name to set
     * @return this builder for chaining
     * @since 1.0
     */
    public ItemStackBuilder name(final String name) {
        final ItemMeta meta = is.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        is.setItemMeta(meta);
        return this;
    }

    /**
     * Adds a new line to the lore of the {@link ItemStack}
     *
     * @param name name
     *             the new line to add
     * @return this builder for chaining
     * @since 1.0
     */
    public ItemStackBuilder lore(final String name) {
        final ItemMeta meta = is.getItemMeta();
        assert meta != null;
        List<String> lore = meta.getLore();
        if (lore == null) lore = new ArrayList<String>();
        lore.add(name);
        meta.setLore(lore);
        is.setItemMeta(meta);
        return this;
    }

    /**
     * Adds a new line to the lore of the {@link ItemStack}
     *
     * @param name name
     *             the new line to add
     * @return this builder for chaining
     * @since 1.0
     */
	/*
	public ItemStackBuilder lore(final ArrayList<String> name) {
		final ItemMeta meta = is.getItemMeta();
		assert meta != null;
		for (String a : meta.getLore()) {
			name.add(a);
		}
		meta.setLore(name);
		is.setItemMeta(meta);
		return this;
	}
	 */

    /**
     * Changes the durability of the {@link ItemStack}
     *
     * @param durability the new durability to set
     * @return this builder for chaining
     * @since 1.0
     */
    public ItemStackBuilder durability(final int durability) {
        is.setDurability((short) durability);
        return this;
    }

    /**
     * Changes the data of the {@link ItemStack}
     *
     * @param data the new data to set
     * @return this builder for chaining
     * @since 1.0
     */
    @SuppressWarnings("deprecation")
    public ItemStackBuilder data(final int data) {
        is.setData(new MaterialData(is.getType(), (byte) data));
        return this;
    }

    /**
     * Adds an {@link Enchantment} with the given level to the {@link ItemStack}
     *
     * @param enchantment the enchantment to add
     * @param level       the level of the enchantment
     * @return this builder for chaining
     * @since 1.0
     */
    public ItemStackBuilder enchantment(final Enchantment enchantment, final int level) {
        is.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    /**
     * Adds an {@link Enchantment} with the level 1 to the {@link ItemStack}
     *
     * @param enchantment the enchantment to add
     * @return this builder for chaining
     * @since 1.0
     */
    public ItemStackBuilder enchantment(final Enchantment enchantment) {
        is.addUnsafeEnchantment(enchantment, 1);
        return this;
    }

    /**
     * Changes the {@link Material} of the {@link ItemStack}
     *
     * @param material material
     *                 the new material to set
     * @return this builder for chaining
     * @since 1.0
     */
    public ItemStackBuilder type(final Material material) {
        is.setType(material);
        return this;
    }

    /**
     * Clears the lore of the {@link ItemStack}
     *
     * @return this builder for chaining
     * @since 1.0
     */
    public ItemStackBuilder clearLore() {
        final ItemMeta meta = is.getItemMeta();
        assert meta != null;
        meta.setLore(new ArrayList<>());
        is.setItemMeta(meta);
        return this;
    }

    /**
     * Clears the list of {@link Enchantment}s of the {@link ItemStack}
     *
     * @return this builder for chaining
     * @since 1.0
     */
    public ItemStackBuilder clearEnchantments() {
        is.getEnchantments().keySet().forEach(is::removeEnchantment);
        return this;
    }

    /**
     * Sets the {@link Color} of a part of leather armor
     *
     * @param color the {@link Color} to use
     * @return this builder for chaining
     * @since 1.1
     */
    public ItemStackBuilder color(Color color) {
        if (of(Material.LEATHER_BOOTS, Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET, Material.LEATHER_LEGGINGS).anyMatch(material -> is.getType() == material)) {
            LeatherArmorMeta meta = (LeatherArmorMeta) is.getItemMeta();
            assert meta != null;
            meta.setColor(color);
            is.setItemMeta(meta);
            return this;
        }
        throw new IllegalArgumentException("color() only applicable for leather armor!");
    }

    /**
     * Adds a effects to the item. The effects gets applied to player when
     * <s>wearing the item</s> (later) or consuming it
     *
     * @param type      the {@link PotionEffectType} to apply
     * @param duration  the duration in ticks (-1 for endless)
     * @param amplifier the amplifier of the effect
     * @param ambient   ambient effect
     *                  ambient status
     * @return this builder for chaining
     * @since 1.2
     */
    public ItemStackBuilder effect(PotionEffectType type, int duration, int amplifier, boolean ambient) {
        effect(new PotionEffect(type, duration, amplifier, ambient));
        return this;
    }

    /**
     * Adds a effects to the item. The effects gets applied to player when
     * <s>wearing the item</s> (later) or consuming it
     *
     * @param effect the effect to apply
     * @return this builder for chaining
     * @since 1.2
     */
    public ItemStackBuilder effect(PotionEffect effect) {
        if (!listener) {
            getPluginManager().registerEvents(this, plugin);
            listener = true;
        }
        String name = requireNonNull(is.getItemMeta()).getDisplayName();
        while (effects.containsKey(name)) name += "#";
        effects.put(name, effect);
        return this;
    }

    /**
     * Adds a effects to the item. The effects gets applied to player when
     * <s>wearing the item</s> (later) or consuming it
     *
     * @param type      the {@link PotionEffectType} to apply
     * @param duration  the duration in ticks (-1 for endless)
     * @param amplifier the amplifier of the effect
     * @return this builder for chaining
     * @since 1.2
     */
    public ItemStackBuilder effect(PotionEffectType type, int duration, int amplifier) {
        effect(new PotionEffect(type, (duration == -1) ? 1000000 : duration, amplifier));
        return this;
    }

    /**
     * Adds a effects to the item. The effects gets applied to player when
     * <s>wearing the item</s> (later) or consuming it
     *
     * @param type     the {@link PotionEffectType} to apply
     * @param duration the duration (-1 for endless)
     * @return this builder for chaining
     * @since 1.2
     */
    public ItemStackBuilder effect(PotionEffectType type, int duration) {
        effect(new PotionEffect(type, (duration == -1) ? 1000000 : duration, 1));
        return this;
    }

    /**
     * Builds the {@link ItemStack}
     *
     * @return the created {@link ItemStack}
     * @since 1.0
     */
    public ItemStack build() {
        return is;
    }

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent e) {
        if (e.getItem().hasItemMeta()) {
            @SuppressWarnings("unchecked") HashMap<String, PotionEffect> copy = (HashMap<String, PotionEffect>) effects.clone();
            String name = requireNonNull(e.getItem().getItemMeta()).getDisplayName();
            while (copy.containsKey(name)) {
                e.getPlayer().addPotionEffect(copy.get(name), true);
                copy.remove(name);
                name += "#";
            }
        }
    }

    @EventHandler
    public void onItemApply(InventoryClickEvent e) {
        // TODO add effects when item is applied
    }

}