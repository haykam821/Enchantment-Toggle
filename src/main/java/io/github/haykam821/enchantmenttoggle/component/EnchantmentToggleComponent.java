package io.github.haykam821.enchantmenttoggle.component;

import java.util.HashSet;
import java.util.Set;

import dev.onyxstudios.cca.api.v3.component.Component;
import io.github.haykam821.enchantmenttoggle.EnchantmentToggle;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class EnchantmentToggleComponent implements Component {
	private static final String INACTIVE_ENCHANTMENTS_KEY = "InactiveEnchantments";

	public final Set<Enchantment> inactiveEnchantments = new HashSet<>();

	public EnchantmentToggleComponent(PlayerEntity player) {
		return;
	}

	public boolean isActive(Enchantment enchantment) {
		return !this.inactiveEnchantments.contains(enchantment) || !EnchantmentToggle.isToggleable(enchantment);
	}

	/**
	 * @return whether the enchantment is active
	 */
	public boolean toggle(Enchantment enchantment) {
		if (EnchantmentToggle.isToggleable(enchantment)) {
			if (!this.inactiveEnchantments.remove(enchantment)) {
				this.inactiveEnchantments.add(enchantment);
				return false;
			}
		}

		return true;
	}

	@Override
	public void readFromNbt(NbtCompound nbt) {
		this.inactiveEnchantments.clear();

		NbtList list = nbt.getList(INACTIVE_ENCHANTMENTS_KEY, NbtElement.STRING_TYPE);
		for (int index = 0; index < list.size(); index += 1) {
			Identifier id = Identifier.tryParse(list.getString(index));
			if (id == null) continue;

			this.inactiveEnchantments.add(Registries.ENCHANTMENT.get(id));
		}
	}

	@Override
	public void writeToNbt(NbtCompound nbt) {
		NbtList list = new NbtList();
		for (Enchantment enchantment : this.inactiveEnchantments) {
			Identifier id = Registries.ENCHANTMENT.getId(enchantment);
			list.add(NbtString.of(id.toString()));
		}

		nbt.put(INACTIVE_ENCHANTMENTS_KEY, list);
	}
}
