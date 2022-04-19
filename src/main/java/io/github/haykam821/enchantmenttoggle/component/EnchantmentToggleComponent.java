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
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;

public class EnchantmentToggleComponent implements Component {
	private static final String INACTIVE_ENCHANTMENTS_KEY = "InactiveEnchantments";

	public final Set<RegistryEntry<Enchantment>> inactiveEnchantments = new HashSet<>();

	public EnchantmentToggleComponent(PlayerEntity player) {
		return;
	}

	public boolean isActive(RegistryEntry<Enchantment> entry) {
		return !this.inactiveEnchantments.contains(entry) || !EnchantmentToggle.isToggleable(entry);
	}

	/**
	 * @return whether the enchantment is active
	 */
	public boolean toggle(RegistryEntry<Enchantment> entry) {
		if (EnchantmentToggle.isToggleable(entry)) {
			if (!this.inactiveEnchantments.remove(entry)) {
				this.inactiveEnchantments.add(entry);
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

			RegistryKey<Enchantment> key = RegistryKey.of(Registry.ENCHANTMENT_KEY, id);
			this.inactiveEnchantments.add(Registry.ENCHANTMENT.entryOf(key));
		}
	}

	@Override
	public void writeToNbt(NbtCompound nbt) {
		NbtList list = new NbtList();
		for (RegistryEntry<Enchantment> entry : this.inactiveEnchantments) {
			if (entry.getKey().isPresent()) {
				Identifier id = entry.getKey().get().getValue();
				list.add(NbtString.of(id.toString()));
			}
		}

		nbt.put(INACTIVE_ENCHANTMENTS_KEY, list);
	}
}
