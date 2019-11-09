package org.bukkit.command.defaults;

import java.util.Arrays;
import java.util.StringJoiner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class PluginsCommand extends BukkitCommand {
    public PluginsCommand(String name) {
        super(name);
        this.description = "Gets a list of plugins running on the server";
        this.usageMessage = "/plugins";
        this.setPermission("bukkit.command.plugins");
        this.setAliases(Arrays.asList("pl"));
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!testPermission(sender)) return true;

        sender.sendMessage("Plugins " + getPluginList());
        return true;
    }

    private String getPluginList() {
        StringJoiner pluginsJoiner = new StringJoiner(ChatColor.WHITE + ", ");
        Plugin[] plugins = Bukkit.getPluginManager().getPlugins();

        for (Plugin plugin : plugins) {
            pluginsJoiner.add((plugin.isEnabled() ? ChatColor.GREEN : ChatColor.RED) + plugin.getDescription().getName());
        }

        return "(" + plugins.length + "): " + pluginsJoiner.toString();
    }

    // Spigot Start
    @Override
    public java.util.List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException
    {
        return java.util.Collections.emptyList();
    }
    // Spigot End
}
