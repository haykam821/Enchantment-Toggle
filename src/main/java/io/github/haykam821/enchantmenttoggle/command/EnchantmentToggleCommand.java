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
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.RegistryEntryArgumentType;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

public class EnchantmentToggleCommand {
	public static final DynamicCommandExceptionType NOT_TOGGLEABLE_ENCHANTMENT_EXCEPTION = new DynamicCommandExceptionType(name -> {
		return Text.translatable("command.enchantmenttoggle.not_toggleable", name);
	});

	private EnchantmentToggleCommand() {
		return;
	}

	public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
		LiteralArgumentBuilder<ServerCommandSource> builder = CommandManager.literal(EnchantmentToggle.MOD_ID)
			.executes(EnchantmentToggleCommand::executeShow)
			.then(CommandManager.argument("enchantment", RegistryEntryArgumentType.registryEntry(registryAccess, RegistryKeys.ENCHANTMENT))
				.executes(EnchantmentToggleCommand::executeToggle));

		dispatcher.register(builder);
	}

	public static int executeShow(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		EnchantmentToggleUi ui = new EnchantmentToggleUi(context.getSource().getPlayer());
		ui.open();

		return Command.SINGLE_SUCCESS;
	}

	public static int executeToggle(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
		RegistryEntry<Enchantment> entry = RegistryEntryArgumentType.getRegistryEntry(context, "enchantment", RegistryKeys.ENCHANTMENT);
		Enchantment enchantment = entry.value();

		Text name = Text.translatable(enchantment.getTranslationKey());
		if (!EnchantmentToggle.isToggleable(enchantment)) {
			throw NOT_TOGGLEABLE_ENCHANTMENT_EXCEPTION.create(name);
		}

		EnchantmentToggleComponent component = EnchantmentToggleComponentInitializer.forPlayer(context.getSource().getPlayer());
		boolean active = component.toggle(enchantment);

		String translationKey = "command.enchantmenttoggle.success." + (active ? "active" : "inactive");
		context.getSource().sendFeedback(Text.translatable(translationKey, name), false);

		return Command.SINGLE_SUCCESS;
	}
}
