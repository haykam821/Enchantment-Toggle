package io.github.haykam821.enchantmenttoggle.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.haykam821.enchantmenttoggle.component.EnchantmentToggleComponentInitializer;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;

@Mixin(ExperienceOrbEntity.class)
public class ExperienceOrbEntityMixin {
	@Inject(method = "repairPlayerGears", at = @At("HEAD"), cancellable = true)
	private void preventMending(PlayerEntity player, int amount, CallbackInfoReturnable<Integer> ci) {
		if (!EnchantmentToggleComponentInitializer.isActive(player, Enchantments.MENDING)) {
			ci.setReturnValue(amount);
		}
	}
}
