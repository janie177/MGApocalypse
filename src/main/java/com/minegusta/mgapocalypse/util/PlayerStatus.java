package com.minegusta.mgapocalypse.util;

import org.bukkit.ChatColor;

public enum PlayerStatus
{
    REGULAR(" ", ChatColor.GRAY, "Regular"),
    BANDIT("[B]", ChatColor.RED, "Bandit"),
    HEALER("[H]", ChatColor.GREEN, "Healer");

    private String name;
    private ChatColor color;
    private String fullname;

    private PlayerStatus(String name, ChatColor color, String fullname)
    {
        this.name = name;
        this.color = color;
        this.fullname = fullname;
    }

    public ChatColor getColor()
    {
        return color;
    }

    public String getName()
    {
        return name;
    }

    public String getFullname()
    {
        return fullname;
    }
}
