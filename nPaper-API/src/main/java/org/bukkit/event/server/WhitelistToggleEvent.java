package org.bukkit.event.server;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class WhitelistToggleEvent extends Event implements Cancellable  {

    private static final HandlerList handlers = new HandlerList();

    private final CommandSender sender;
    private boolean enable;
    private final Cause cause;
    private boolean cancel;

    public WhitelistToggleEvent(final CommandSender sender, boolean enable, Cause cause) {
        this.sender = sender;
        this.enable = enable;
        this.cause = cause;
    }

    /**
     * Get the command sender.
     *
     * @return The sender
     */
    public CommandSender getSender() {
        return sender;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    public boolean willBeEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Cause getCause() {
        return cause;
    }

    public enum Cause {
        /**
         * Caused by /whitelist
         */
        COMMAND,
        /**
         * Caused by Server#setWhitelist()
         */
        PLUGIN
    }
}
