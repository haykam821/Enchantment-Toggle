package io.github.haykam821.enchantmenttoggle.ui.layer;

import eu.pb4.sgui.api.gui.layered.Layer;
import io.github.haykam821.enchantmenttoggle.ui.EnchantmentToggleUi;

public abstract class AbstractEnchantmentLayer extends Layer {
	protected final EnchantmentToggleUi ui;

	public AbstractEnchantmentLayer(EnchantmentToggleUi ui, int width, int height) {
		super(height, width);
		this.ui = ui;
	}

	public abstract void update();
}