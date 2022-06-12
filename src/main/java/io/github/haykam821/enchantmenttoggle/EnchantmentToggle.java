package io.github.haykam821.enchantmenttoggle;

import io.github.haykam821.enchantmenttoggle.command.EnchantmentToggleCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;

public class EnchantmentToggle implements ModInitializer {
	public static final String MOD_ID = "enchantmenttoggle";

	private static final Identifier TOGGLEABLE_ENCHANTMENTS_ID = new Identifier(MOD_ID, "toggleable_enchantments");
	public static final TagKey<Enchantment> TOGGLEABLE_ENCHANTMENTS = TagKey.of(Registry.ENCHANTMENT_KEY, TOGGLEABLE_ENCHANTMENTS_ID);

	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			EnchantmentToggleCommand.register(dispatcher);
		});
	}

	public static boolean isToggleable(RegistryEntry<Enchantment> entry) {
		return entry.isIn(EnchantmentToggle.TOGGLEABLE_ENCHANTMENTS);
	}
}
