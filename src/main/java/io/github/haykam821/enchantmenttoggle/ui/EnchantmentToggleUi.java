package io.github.haykam821.enchantmenttoggle.ui;

import java.util.Optional;

import eu.pb4.sgui.api.gui.layered.LayeredGui;
import io.github.haykam821.enchantmenttoggle.EnchantmentToggle;
import io.github.haykam821.enchantmenttoggle.component.EnchantmentToggleComponent;
import io.github.haykam821.enchantmenttoggle.component.EnchantmentToggleComponentInitializer;
import io.github.haykam821.enchantmenttoggle.ui.layer.EnchantmentGridLayer;
import io.github.haykam821.enchantmenttoggle.ui.layer.EnchantmentToolbarLayer;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntryList;
import net.minecraft.util.registry.RegistryEntryList.Named;

public class EnchantmentToggleUi extends LayeredGui {
	private final EnchantmentGridLayer gridLayer;
	private final EnchantmentToolbarLayer toolbarLayer;

	private final EnchantmentToggleComponent component;
	private final RegistryEntryList<Enchantment> enchantments;

	private int page = 0;

	public EnchantmentToggleUi(ServerPlayerEntity player) {
		super(ScreenHandlerType.GENERIC_9X6, player, false);

		this.gridLayer = new EnchantmentGridLayer(this, this.width, this.height - 1);
		this.addLayer(this.gridLayer, 0, 0);

		this.toolbarLayer = new EnchantmentToolbarLayer(this, this.width);
		this.addLayer(this.toolbarLayer, 0, this.height - 1);

		this.component = EnchantmentToggleComponentInitializer.forPlayer(player);
		this.enchantments = EnchantmentToggleUi.createEnchantments();

		this.setTitle(new TranslatableText("text.enchantmenttoggle.ui.title"));
		this.update();
	}

	public EnchantmentToggleComponent getComponent() {
		return this.component;
	}

	public RegistryEntryList<Enchantment> getEnchantments() {
		return this.enchantments;
	}

	public int getPage() {
		return this.page;
	}

	private void setPage(int page) {
		this.page = page;
		this.update();
	}

	public void setWrappedPage(int page) {
		if (page < 0) {
			page = this.gridLayer.getMaxPage() - page - 1;
		}
		this.setPage(page);
	}

	public void movePage(int offset) {
		this.setPage(this.getPage() + offset);
	}

	private void clampPage() {
		if (page < 0) {
			page = 0;
		}

		int maxPage = this.gridLayer.getMaxPage();
		if (page >= maxPage) {
			page = maxPage - 1;
		}
	}

	public void update() {
		this.clampPage();

		this.gridLayer.update();
		this.toolbarLayer.update();
	}

	private static RegistryEntryList<Enchantment> createEnchantments() {
		Optional<Named<Enchantment>> optional = Registry.ENCHANTMENT.getEntryList(EnchantmentToggle.TOGGLEABLE_ENCHANTMENTS);
		if (optional.isPresent()) {
			return optional.get();
		}

		return RegistryEntryList.of();
	}
}
