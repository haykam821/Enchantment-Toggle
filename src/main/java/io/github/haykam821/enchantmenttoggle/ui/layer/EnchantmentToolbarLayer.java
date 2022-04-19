package io.github.haykam821.enchantmenttoggle.ui.layer;

import eu.pb4.sgui.api.elements.GuiElement;
import io.github.haykam821.enchantmenttoggle.ui.EnchantmentToggleUi;
import io.github.haykam821.enchantmenttoggle.ui.element.BackgroundElement;
import io.github.haykam821.enchantmenttoggle.ui.element.PageElement;

public class EnchantmentToolbarLayer extends AbstractEnchantmentLayer {
	private final GuiElement previousPageElement;
	private final GuiElement nextPageElement;

	public EnchantmentToolbarLayer(EnchantmentToggleUi ui, int width) {
		super(ui, width, 1);

		this.previousPageElement = PageElement.ofPrevious(ui);
		this.nextPageElement = PageElement.ofNext(ui);
	}

	@Override
	public void update() {
		int center = this.getSize() / 2;

		for (int slot = 0; slot < this.getSize(); slot += 1) {
			GuiElement element = BackgroundElement.INSTANCE;
			if (slot == center - 1) {
				element = this.previousPageElement;
			} else if (slot == center + 1) {
				element = this.nextPageElement;
			}

			this.setSlot(slot, element);
		}
	}
}
