package com.obsidian.knightsmp.commands.TabCompleters;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class PasswordTabCompleter implements TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (args.length == 1) {
            return Arrays.asList("set", "verify", "reset");
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
            return Arrays.asList("<your password>");
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("verify")) {
            return Arrays.asList("<your set password>");
        }
        if (args.length == 2 && args[0].equalsIgnoreCase("reset")) {
            return Arrays.asList("<old password>");
        }
        if (args.length == 3 && args[0].equalsIgnoreCase("reset")) {
            return Arrays.asList("<new password>");
        }
        return null;
    }
}
