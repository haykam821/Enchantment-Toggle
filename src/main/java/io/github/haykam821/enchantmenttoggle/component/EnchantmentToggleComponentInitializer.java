package io.github.haykam821.enchantmenttoggle.component;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import io.github.haykam821.enchantmenttoggle.EnchantmentToggle;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class EnchantmentToggleComponentInitializer implements EntityComponentInitializer {
	private static final Identifier ENCHANTMENT_TOGGLE_ID = new Identifier(EnchantmentToggle.MOD_ID, "enchantment_toggle");
	public static final ComponentKey<EnchantmentToggleComponent> ENCHANTMENT_TOGGLE = ComponentRegistryV3.INSTANCE.getOrCreate(ENCHANTMENT_TOGGLE_ID, EnchantmentToggleComponent.class);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerForPlayers(ENCHANTMENT_TOGGLE, EnchantmentToggleComponent::new, RespawnCopyStrategy.ALWAYS_COPY);
	}

	public static EnchantmentToggleComponent forPlayer(PlayerEntity player) {
		return EnchantmentToggleComponentInitializer.ENCHANTMENT_TOGGLE.get(player);
	}

	public static boolean isActive(PlayerEntity player, Enchantment enchantment) {
		EnchantmentToggleComponent component = EnchantmentToggleComponentInitializer.forPlayer(player);
		return component.isActive(enchantment);
	}
}
