package io.github.haykam821.enchantmenttoggle.ui.layer;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;

import io.github.haykam821.enchantmenttoggle.ui.EnchantmentToggleUi;
import io.github.haykam821.enchantmenttoggle.ui.element.EnchantmentElement;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.MathHelper;

public class EnchantmentGridLayer extends AbstractEnchantmentLayer {
	public static final Comparator<RegistryEntry<Enchantment>> COMPARATOR = Comparator.comparing(entry -> {
		Optional<RegistryKey<Enchantment>> maybeKey = ((RegistryEntry<Enchantment>) entry).getKey();
		if (maybeKey.isPresent()) {
			return maybeKey.get().getValue();
		}

		return null;
	});

	public EnchantmentGridLayer(EnchantmentToggleUi ui, int width, int height) {
		super(ui, width, height);
	}

	@Override
	public void update() {
		this.clearSlots();

		int slot = 0;
		for (Enchantment enchantment : this.getPageEntries()) {
			if (slot >= this.getSize()) {
				break;
			}

			this.setSlot(slot, EnchantmentElement.of(this.ui, enchantment));
			slot += 1;
		}
	}

	private Iterable<Enchantment> getPageEntries() {
		return this.ui.getEnchantments().stream()
			.sorted(COMPARATOR)
			.skip(this.getSize() * this.ui.getPage())
			.map(RegistryEntry::value)
			.collect(Collectors.toList());
	}

	public int getMaxPage() {
		int count = this.ui.getEnchantments().size();
		int size = this.getSize();

		return MathHelper.ceil(count / (float) size);
	}
}
