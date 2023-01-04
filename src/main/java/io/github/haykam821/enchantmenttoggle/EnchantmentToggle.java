package io.github.haykam821.enchantmenttoggle;

import io.github.haykam821.enchantmenttoggle.command.EnchantmentToggleCommand;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class EnchantmentToggle implements ModInitializer {
	public static final String MOD_ID = "enchantmenttoggle";

	private static final Identifier TOGGLEABLE_ENCHANTMENTS_ID = new Identifier(MOD_ID, "toggleable_enchantments");
	public static final TagKey<Enchantment> TOGGLEABLE_ENCHANTMENTS = TagKey.of(RegistryKeys.ENCHANTMENT, TOGGLEABLE_ENCHANTMENTS_ID);

	@Override
	public void onInitialize() {
		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			EnchantmentToggleCommand.register(dispatcher, registryAccess);
		});
	}

	public static boolean isToggleable(Enchantment enchantment) {
		return Registries.ENCHANTMENT.getEntry(enchantment).isIn(EnchantmentToggle.TOGGLEABLE_ENCHANTMENTS);
	}
}
