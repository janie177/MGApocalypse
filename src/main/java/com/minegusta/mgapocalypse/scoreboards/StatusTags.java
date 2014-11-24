package com.minegusta.mgapocalypse.scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class StatusTags
{
    public static final ScoreboardManager m = Bukkit.getScoreboardManager();
    public static final Scoreboard sb = m.getNewScoreboard();

    public static void set(Player p, String title, ChatColor color, int amount)
    {
        Objective objective;
        if(sb.getObjective(p.getName()) == null)
        {
            objective = sb.registerNewObjective(p.getName(), "dummy");
        }
        else
        {
            objective = sb.getObjective(p.getName());
        }
        objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        String scoreName = color + title;
        objective.setDisplayName(scoreName);
        Score score = objective.getScore(scoreName);
        score.setScore(amount);
        p.setScoreboard(sb);

    }

    public static void clear(Player p)
    {
        p.setScoreboard(m.getMainScoreboard());
    }

}
