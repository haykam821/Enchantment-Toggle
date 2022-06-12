package io.github.haykam821.enchantmenttoggle.ui.element;

import eu.pb4.sgui.api.elements.GuiElement;
import eu.pb4.sgui.api.elements.GuiElementBuilder;
import eu.pb4.sgui.api.elements.GuiElementInterface.ClickCallback;
import io.github.haykam821.enchantmenttoggle.ui.EnchantmentToggleUi;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.registry.RegistryEntry;

public final class EnchantmentElement {
	private static final Item ACTIVE_ICON = Items.LIME_DYE;
	private static final Item INACTIVE_ICON = Items.RED_DYE;

	private static final Formatting ACTIVE_FORMATTING = Formatting.GREEN;
	private static final Formatting INACTIVE_FORMATTING = Formatting.RED;

	private EnchantmentElement() {
		return;
	}

	public static GuiElement of(EnchantmentToggleUi ui, RegistryEntry<Enchantment> entry) {
		boolean active = ui.getComponent().isActive(entry);

		Text name = Text.translatable(entry.value().getTranslationKey()).styled(style -> {
			return style.withFormatting(active ? ACTIVE_FORMATTING : INACTIVE_FORMATTING);
		});

		return new GuiElementBuilder(active ? ACTIVE_ICON : INACTIVE_ICON)
			.setName(name)
			.setCallback(EnchantmentElement.createCallback(ui, entry))
			.build();
	}

	private static ClickCallback createCallback(EnchantmentToggleUi ui, RegistryEntry<Enchantment> entry) {
		return (index, type, action, guiInterface) -> {
			if (action == SlotActionType.PICKUP) {
				ui.getComponent().toggle(entry);
				ui.update();

				ElementUtil.playClickSound(ui);
			}
		};
	}
}
