package io.github.haykam821.enchantmenttoggle.ui.element;

import io.github.haykam821.enchantmenttoggle.ui.EnchantmentToggleUi;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public final class ElementUtil {
	private ElementUtil() {
		return;
	}

	protected static void playClickSound(EnchantmentToggleUi ui) {
		ui.getPlayer().playSound(SoundEvents.UI_BUTTON_CLICK, SoundCategory.MASTER, 1, 1);
	}

	protected static void playSelectSound(EnchantmentToggleUi ui) {
		ui.getPlayer().playSound(SoundEvents.ITEM_BUNDLE_REMOVE_ONE, SoundCategory.MASTER, 1, 1.5f);
	}

	protected static void playFilterSound(EnchantmentToggleUi ui) {
		ui.getPlayer().playSound(SoundEvents.UI_STONECUTTER_TAKE_RESULT, SoundCategory.MASTER, 1, 1.2f);
	}

	protected static Text getToolbarName(String translationKey) {
		return Text.translatable(translationKey).formatted(Formatting.YELLOW);
	}

	public static Text getLoreLine(String translationKey, Object... args) {
		return Text.translatable(translationKey, args).formatted(Formatting.GRAY);
	}
}
