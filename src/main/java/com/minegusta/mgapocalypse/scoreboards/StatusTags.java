package com.minegusta.mgapocalypse.scoreboards;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.concurrent.ConcurrentMap;

public class StatusTags
{
    public static ConcurrentMap<String, Scoreboard> sbMap = Maps.newConcurrentMap();
    public static final ScoreboardManager m = Bukkit.getScoreboardManager();

    public static void set(Player p, String title, ChatColor color, int amount)
    {
        if(!sbMap.containsKey(p.getUniqueId().toString()))
        {
            sbMap.put(p.getUniqueId().toString(), m.getNewScoreboard());
        }

        Scoreboard sb = sbMap.get(p.getUniqueId().toString());
        Objective ob = sb.getObjective(DisplaySlot.BELOW_NAME);
        if(ob == null)
        {
            ob = sb.registerNewObjective("tag", "dummy");
            ob.setDisplayName(color + title);
        }

        Score score = ob.getScore(color + title);
        score.setScore(amount);

        sbMap.put(p.getUniqueId().toString(), sb);
        p.setScoreboard(sb);


    }

    public static void clear(Player p)
    {
        if(sbMap.containsKey(p.getUniqueId().toString())) sbMap.remove(p.getUniqueId().toString());
        p.setScoreboard(m.getMainScoreboard());
    }

}
