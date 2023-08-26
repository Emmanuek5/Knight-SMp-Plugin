package com.obsidian.knightsmp.commands.TabCompleters;

import com.obsidian.knightsmp.KnightSmp;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class PlayerTabCompleter implements TabCompleter {

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (args.length == 1) {
            return Arrays.asList("info", "restore", "customitems","reset-captcha");
        }

        if (args.length == 2 ) {
            return KnightSmp.playerDataManager.getAllPlayerNames();
        }
        return null;
    }
}
