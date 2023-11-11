package com.obsidian.knightsmp.commands.TabCompleters;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class BanPlayerTabCompleter implements TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (strings.length == 2){
            return Arrays.asList("<ban reason>");
        }

        if (strings.length == 3){
            return Arrays.asList("<expiry>");
        }

        if (strings.length == 4){
            return Arrays.asList("<permanent {true/false}>");
        }
        return null;
    }
}
