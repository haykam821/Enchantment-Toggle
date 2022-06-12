package io.github.haykam821.enchantmenttoggle.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;

import io.github.haykam821.enchantmenttoggle.EnchantmentToggle;
import io.github.haykam821.enchantmenttoggle.component.EnchantmentToggleComponent;
import io.github.haykam821.enchantmenttoggle.component.EnchantmentToggleComponentInitializer;
import io.github.haykam821.enchantmenttoggle.ui.EnchantmentToggleUi;
import net.minecraft.command.argument.EnchantmentArgumentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.util.registry.RegistryKey;

public class EnchantmentToggleCommand {
	public static final DynamicCommandExceptionType NOT_TOGGLEABLE_ENCHANTMENT_EXCEPTION = new DynamicCommandExceptionType(name -> {
		return Text.translatable("command.enchantmenttoggle.not_toggleable", name);
	});

	private EnchantmentToggleCommand() {
		return;
	}

	public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
		LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal(EnchantmentToggle.MOD_ID)
			.executes(EnchantmentToggleCommand::executeShow)
			.then(CommandManager.argument("enchantment", EnchantmentArgumentType.enchantment())
				.executes(EnchantmentToggleCommand::executeToggle));

		dispatcher.register(builder);
	}

	public static int executeShow(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		EnchantmentToggleUi ui = new EnchantmentToggleUi(context.getSource().getPlayer());
		ui.open();

		return Command.SINGLE_SUCCESS;
	}

	public static int executeToggle(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		Enchantment enchantment = EnchantmentArgumentType.getEnchantment(context, "enchantment");

		RegistryKey<Enchantment> key = Registry.ENCHANTMENT.getKey(enchantment).orElse(null);
		RegistryEntry<Enchantment> entry = Registry.ENCHANTMENT.entryOf(key);

		Text name = Text.translatable(enchantment.getTranslationKey());
		if (!EnchantmentToggle.isToggleable(entry)) {
			throw NOT_TOGGLEABLE_ENCHANTMENT_EXCEPTION.create(name);
		}

		EnchantmentToggleComponent component = EnchantmentToggleComponentInitializer.forPlayer(context.getSource().getPlayer());
		boolean active = component.toggle(entry);

		String translationKey = "command.enchantmenttoggle.success." + (active ? "active" : "inactive");
		context.getSource().sendFeedback(Text.translatable(translationKey, name), false);

		return Command.SINGLE_SUCCESS;
	}
}
