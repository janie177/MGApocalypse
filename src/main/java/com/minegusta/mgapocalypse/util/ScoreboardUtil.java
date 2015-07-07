package com.minegusta.mgapocalypse.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

public class ScoreboardUtil {

    private static Scoreboard board;
    private static Objective objective;

    public static void setBoard() {
        board = Bukkit.getScoreboardManager().getNewScoreboard();
        objective = board.registerNewObjective("wastelandhealth", "health");
        objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        objective.setDisplayName(ChatColor.RED + "Health");

        for (PlayerStatus status : PlayerStatus.values()) {
            Team team = board.registerNewTeam(status.getName());
            team.setPrefix(status.getColor() + status.getName() + " ");
            team.setDisplayName(status.getColor() + status.getName() + " ");
            team.setNameTagVisibility(NameTagVisibility.ALWAYS);
            team.setCanSeeFriendlyInvisibles(false);
        }
    }

    public static void addScoreBoard(Player p, PlayerStatus status) {
        Team team = board.getTeam(status.getName());
        team.addPlayer(p);
        p.setScoreboard(board);
    }

    public static void removeScoreBoard(Player p) {
        p.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
    }
}
