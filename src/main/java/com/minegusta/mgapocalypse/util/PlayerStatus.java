package com.minegusta.mgapocalypse.util;

import org.bukkit.ChatColor;

public enum PlayerStatus
{
    REGULAR(" ", ChatColor.GRAY, "Regular"),
    BANDIT(ChatColor.DARK_RED + "[B]", ChatColor.RED, "Bandit"),
    HEALER(ChatColor.DARK_GREEN + "[H]", ChatColor.GREEN, "Healer");

    private String tag;
    private ChatColor color;
    private String fullname;

    private PlayerStatus(String name, ChatColor color, String fullname)
    {
        this.tag = name;
        this.color = color;
        this.fullname = fullname;
    }

    public ChatColor getColor()
    {
        return color;
    }

    public String getTag()
    {
        return tag;
    }

    public String getFullname()
    {
        return fullname;
    }
}
