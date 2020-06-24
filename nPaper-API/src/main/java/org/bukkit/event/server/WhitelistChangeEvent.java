package org.bukkit.event.server;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class WhitelistChangeEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private final CommandSender sender;
    private String target;
    private final Cause cause;
    private final Action action;
    private boolean cancel;

    public WhitelistChangeEvent(CommandSender sender, String target, Cause cause, Action action) {
        this.sender = sender;
        this.target = target;
        this.cause = cause;
        this.action = action;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public CommandSender getSender() {
        return sender;
    }

    public Action getAction() {
        return action;
    }

    public WhitelistChangeEvent.Cause getCause() {
        return cause;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }

    public enum Cause {
        /**
         * Caused by /whitelist  (use this)
         */
        COMMAND,
        /**
         * Caused by /whitelist when OfflinePlayer#setWhitelist(b, cause) (dont use this (it's just for a check after the COMMAND cause))
         */
        POST_COMMAND,
        /**
         * Caused by OfflinePlayer#setWhitelist(b) (use this)
         */
        PLUGIN
    }

    public enum Action {
        ADD,
        REMOVE
    }
}
