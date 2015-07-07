package com.minegusta.mgapocalypse.files;

import com.google.common.collect.Maps;
import com.minegusta.mgapocalypse.perks.Perk;
import com.minegusta.mgapocalypse.perks.abilities.Athlete;
import com.minegusta.mgapocalypse.perks.abilities.Health;
import com.minegusta.mgapocalypse.util.Break;
import com.minegusta.mgapocalypse.util.PlayerStatus;
import com.minegusta.mgapocalypse.util.ScoreboardUtil;
import com.minegusta.mgapocalypse.util.WorldCheck;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

public class MGPlayer {
    private String uuid;
    private FileConfiguration conf;
    private double health;
    private int zombieKills;
    private long totalZombieKills;
    private int giantKills;
    private int totalGiantKills;
    private int heals;
    private int totalHeals;
    private int playerKills;
    private int totalPlayerKills;
    private int playTime;
    private int alive;

    private PlayerStatus status;

    private int mostHeals;
    private int mostPlayerKills;
    private int mostGiantKills;

    private int perkPoints;

    private int deaths;
    private int longestKillStreak;
    private int longestAlive;
    private int chestsLooted;
    private int emeraldChestsLooted;
    private int ironChestsLooted;
    private int diamondChestsLooted;
    private long earnedcredits;
    private int hunger;
    private boolean bleeding;
    private boolean infected;
    private int thirst;

    private boolean playing;

    private long lastHealed;
    private long lastHealedOther;
    private long lastBandaged;

    private ConcurrentMap<Perk, Integer> perks = Maps.newConcurrentMap();

    //static

    public static ConcurrentMap<String, Break> breaks = Maps.newConcurrentMap();

    //constructor

    private MGPlayer(String uuid, FileConfiguration f) {
        this.uuid = uuid;
        this.conf = f;

        this.lastHealed = 0;
        this.lastHealedOther = 0;
        this.lastBandaged = 0;

        this.status = PlayerStatus.valueOf(f.getString("status", "REGULAR").toUpperCase());
        this.perkPoints = f.getInt("perkpoints", 0);
        this.health = f.getDouble("health", 20.0);
        this.zombieKills = f.getInt("kills", 0);
        this.totalZombieKills = f.getLong("totalkills", 0);
        this.giantKills = f.getInt("giantkills", 0);
        this.totalGiantKills = f.getInt("totalgiantkills", 0);
        this.heals = f.getInt("heals", 0);
        this.totalHeals = f.getInt("totalheals", 0);
        this.alive = f.getInt("alive", 0);
        this.totalPlayerKills = f.getInt("totalplayerkills", 0);
        this.playerKills = f.getInt("playerkills", 0);
        this.playTime = f.getInt("playtime", 0);
        this.deaths = f.getInt("deaths", 0);
        this.longestKillStreak = f.getInt("longestkillstreak", 0);
        this.longestAlive = f.getInt("longestalive", 0);
        this.chestsLooted = f.getInt("chestslooted", 0);
        this.earnedcredits = f.getInt("earnedcredits", 0);
        this.emeraldChestsLooted = f.getInt("emeraldchestslooted", 0);
        this.diamondChestsLooted = f.getInt("diamondchestslooted", 0);
        this.ironChestsLooted = f.getInt("ironchestslooted", 0);
        this.hunger = f.getInt("hunger", 20);
        this.bleeding = f.getBoolean("bleeding", false);
        this.infected = f.getBoolean("infected", false);
        this.thirst = f.getInt("thirst", 20);
        this.playing = f.getBoolean("playing", false);
        this.mostGiantKills = f.getInt("mostgiantkills", 0);
        this.mostHeals = f.getInt("mostheals", 0);
        this.mostPlayerKills = f.getInt("mostplayerkills", 0);

        if (f.isSet("perks")) {

            for (String s : f.getConfigurationSection("perks").getKeys(false)) {
                int level = f.getInt("perks." + s, 0);

                if (Perk.valueOf(s.toUpperCase()) == null) continue;

                Perk perk = Perk.valueOf(s.toUpperCase());

                perks.put(perk, level);
            }
        }

        updateScoreBoard();
    }

    public static MGPlayer build(String uuid, FileConfiguration f) {
        return new MGPlayer(uuid, f);
    }

    //-// //-// //-// //-// The Methods //-// //-// //-// //-//

    public FileConfiguration getConf() {
        updateConfig();
        return conf;
    }

    public void updatePlayerStats()
    {
        Player p = getPlayer();

        setHealth(p.getHealth());
        setHunger(p.getFoodLevel());
        setThirst(p.getLevel());
    }

    public void updateConfig() {
        if (playing) updatePlayerStats();
        updateScoreBoard();

        conf.set("status", status);
        conf.set("perkpoints", perkPoints);
        conf.set("playing", playing);
        conf.set("health", health);
        conf.set("kills", zombieKills);
        conf.set("totalkills", totalZombieKills);
        conf.set("giantkills", giantKills);
        conf.set("totalgiantkills", totalGiantKills);
        conf.set("heals", heals);
        conf.set("playerkills", playerKills);
        conf.set("playtime", playTime);
        conf.set("deaths", deaths);
        conf.set("longestkillstreak", longestKillStreak);
        conf.set("longestalive", longestAlive);
        conf.set("chestslooted", chestsLooted);
        conf.set("earnedcredits", earnedcredits);
        conf.set("emeraldchestslooted", emeraldChestsLooted);
        conf.set("diamondchestslooted", diamondChestsLooted);
        conf.set("ironchestslooted", ironChestsLooted);
        conf.set("hunger", hunger);
        conf.set("bleeding", bleeding);
        conf.set("infected", infected);
        conf.set("thirst", thirst);
        conf.set("totalheals", totalHeals);
        conf.set("alive", alive);
        conf.set("totalplayerkills", totalPlayerKills);
        conf.set("mostgiantkills", mostGiantKills);
        conf.set("mostheals", mostHeals);
        conf.set("mostplayerkills", mostPlayerKills);

        for (Perk p : perks.keySet()) {
            conf.set("perks." + p.name(), perks.get(p));
        }
    }

    public void saveFile() {
        updateConfig();
        FileManager.save(uuid, conf);
    }

    public void applyConfigStats() {
        Player p = getPlayer();

        Health.setBoost(p);
        Athlete.setBoost(p);

        p.setHealth(getHealth());
        p.setFoodLevel(getHunger());
        p.setLevel(getThirst());
    }

    public void killPlayer() {

        Player p = getPlayer();

        p.setGameMode(GameMode.SURVIVAL);

        setHealth(p.getMaxHealth());
        setHunger(20);
        setThirst(20);
        setBleeding(false);
        setInfected(false);

        p.setHealth(getHealth());
        p.setLevel(getThirst());
        p.setFoodLevel(getHunger());

        if (getZombieKills() > getLongestKillStreak()) {
            setlongestKillStreak(getZombieKills());
        }
        if (getLongestAlive() < getTimeAlive()) {
            setlongestAlive(getTimeAlive());
        }
        if (getGiantKills() > getMostGiantKills()) {
            setMostGiantKills(getGiantKills());
        }
        if (getHeals() > getMostHeals()) {
            setMostHeals(getHeals());
        }
        if (getPlayerKills() > getMostPlayerKills()) {
            setMostPlayerKills(getPlayerKills());
        }

        addTotalGiantKills(getGiantKills());
        addTotalheals(getHeals());
        addTotalPlayersKilled(getPlayerKills());
        addTotalZombieKills(getZombieKills());


        setGiantKills(0);
        setZombieKills(0);
        setHeals(0);
        setPlayerKills(0);
        setTimeAlive(0);
        setPerkPoints(0);

        updateScoreBoard();

        //Clear all perks, auto saves to config.
        conf.set("perks", null);
        resetPerkBoosts();
        perks.clear();

        setPlaying(false);
        p.getInventory().clear();

        saveFile();
    }


    //-// //-// //-// //-// -=-=-=- //-// //-// //-// //-//
    //-// //-// //-// //-// Getters //-// //-// //-// //-//
    //-// //-// //-// //-// -=-=-=- //-// //-// //-// //-//

    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(UUID.fromString(uuid));
    }

    public Player getPlayer() {
        return Bukkit.getPlayer(UUID.fromString(uuid));
    }

    public boolean getIfPlaying() {
        return playing;
    }

    public int getTimeAlive() {
        return alive;
    }

    public int getTotalPlayerKills() {
        return totalPlayerKills;
    }

    public int getTotalHeals() {
        return totalHeals;
    }

    public double getHealth() {
        return health;
    }

    public int getTotalGiantKills() {
        return totalGiantKills;
    }

    public int getPerkPoints() {
        return perkPoints;
    }

    public int getZombieKills() {
        return zombieKills;
    }

    public long getTotalZombieKills() {
        return totalZombieKills;
    }

    public long getLastHealed() {
        return lastHealed;
    }

    public long getLastHealedOther() {
        return lastHealedOther;
    }

    public long getLastBandaged() {
        return lastBandaged;
    }

    public int getGiantKills() {
        return giantKills;
    }

    public int getHeals() {
        return heals;
    }

    public int getPlayerKills() {
        return playerKills;
    }

    public int getPlayTime() {
        return playTime;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getLongestKillStreak() {
        return longestKillStreak;
    }

    public int getLongestAlive() {
        return longestAlive;
    }

    public int getChestsLooted() {
        return chestsLooted;
    }

    public int getMostHeals() {
        return mostHeals;
    }

    public int getMostPlayerKills() {
        return mostPlayerKills;
    }

    public int getMostGiantKills() {
        return mostGiantKills;
    }

    public long getEarnedcredits() {
        return earnedcredits;
    }

    public int getHunger() {
        return hunger;
    }

    public boolean isBleeding() {
        return bleeding;
    }

    public boolean isInfected() {
        return infected;
    }

    public int getThirst() {
        return thirst;
    }

    public int getDiamondChestsLooted() {
        return diamondChestsLooted;
    }

    public int getEmeraldChestsLooted() {
        return emeraldChestsLooted;
    }

    public int getIronChestsLooted() {
        return ironChestsLooted;
    }

    public Perk[] getPerks() {
        return (Perk[]) perks.keySet().toArray();
    }

    public boolean hasPerk(Perk perk) {
        return perks.containsKey(perk);
    }

    public int getPerkLevel(Perk p) {
        if (perks.containsKey(p)) {
            return perks.get(p);
        }
        return 0;
    }

    public void addPerk(Perk p) {
        perks.put(p, 1);
    }

    public void setPerkLevel(Perk p, int level) {
        perks.put(p, level);
    }

    public void perkLevelUp(Perk p) {
        if (perks.containsKey(p)) {
            perks.put(p, perks.get(p) + 1);
        } else {
            perks.put(p, 1);
        }
    }

    public int getPerksBought() {
        int amount = 0;
        for (int i : perks.values()) {
            amount = amount + i;
        }

        return amount;
    }

    //-// //-// //-// //-// -=-=-=- //-// //-// //-// //-//
    //-// //-// //-// //-// Setters //-// //-// //-// //-//
    //-// //-// //-// //-// -=-=-=- //-// //-// //-// //-//

    public void setHealth(double health) {
        this.health = health;
    }

    public void setZombieKills(int kills) {
        this.zombieKills = kills;
    }

    public void addZombieKills(int killsAdded) {
        Player p = getPlayer();
        int credits = killsAdded * 3;
        this.zombieKills = zombieKills + killsAdded;
        p.sendMessage(ChatColor.GOLD + "You now have " + ChatColor.DARK_PURPLE + Integer.toString(getZombieKills()) + ChatColor.GOLD + " zombie kills.");
        p.sendMessage(ChatColor.GOLD + "You earned " + ChatColor.LIGHT_PURPLE + Integer.toString(credits) + ChatColor.GOLD + " credits.");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "addcredits " + p.getName() + " " + credits);
        addEarnedcredits(credits);
        if (getZombieKills() % 3 == 0) {
            addPerkPoints(1);
        }
    }

    public void setTotalZombieKills(int totalKills) {
        this.totalZombieKills = totalKills;
    }

    public void addTotalZombieKills(int addedKills) {
        this.totalZombieKills = totalZombieKills + addedKills;
    }

    public void setGiantKills(int kills) {
        this.giantKills = kills;
    }

    public void setTotalGiantKills(int totalGiantKills) {
        this.totalGiantKills = totalGiantKills;
    }

    public void addTotalGiantKills(int added) {
        totalGiantKills = totalGiantKills + added;
    }

    public void addGiantKills(int killsAdded) {
        this.giantKills = giantKills + killsAdded;
        Player p = getPlayer();
        int credits = killsAdded * 15;
        this.zombieKills = zombieKills + killsAdded;
        p.sendMessage(ChatColor.GOLD + "You now have " + ChatColor.DARK_PURPLE + Integer.toString(getGiantKills()) + ChatColor.GOLD + " giant kills.");
        p.sendMessage(ChatColor.GOLD + "You earned " + ChatColor.LIGHT_PURPLE + Integer.toString(credits) + ChatColor.GOLD + " credits.");
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "addcredits " + p.getName() + " " + credits);
        addEarnedcredits(credits);
        addPerkPoints(killsAdded);
    }

    public void setHeals(int amount) {
        this.heals = amount;
        updateScoreBoard();
    }

    public void addHeals(int amount) {
        this.heals = heals + amount;
        updateScoreBoard();
    }

    public void setPlayerKills(int kills) {
        playerKills = kills;
        updateScoreBoard();
    }

    public void addPlayerKills(int killsAdded) {
        playerKills = playerKills + killsAdded;
        addPerkPoints(killsAdded * 3);
        updateScoreBoard();
    }

    public void setPlayTime(int timePlayed) {
        playTime = timePlayed;
    }

    public void addPlayTime(int timeAdded) {
        playTime = playTime + timeAdded;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public void addDeaths(int deathsAdded) {
        this.deaths = deaths + deathsAdded;
    }

    public void setlongestKillStreak(int longestKillStreak) {
        this.longestKillStreak = longestKillStreak;
        getPlayer().sendMessage(ChatColor.GREEN + "You broke your record for largest killstreak!");
        getPlayer().sendMessage(ChatColor.GREEN + "You killed " + ChatColor.DARK_PURPLE + getLongestAlive() + ChatColor.GREEN + " zombies!");
    }

    public void setlongestAlive(int longestAlive) {
        this.longestAlive = longestAlive;
        getPlayer().sendMessage(ChatColor.GREEN + "You broke your record for longest alive!");
        getPlayer().sendMessage(ChatColor.GREEN + "You lived for " + ChatColor.DARK_PURPLE + getLongestAlive() + ChatColor.GREEN + " minutes!");
    }

    public void setChestsLooted(int looted) {
        chestsLooted = looted;
    }

    public void addChestsLooted(int added) {
        chestsLooted = chestsLooted + added;
    }

    public void setEarnedcredits(int creditsEarned) {
        earnedcredits = creditsEarned;
    }

    public void addEarnedcredits(int creditsAdded) {
        earnedcredits = creditsAdded + earnedcredits;
    }

    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    public void setBleeding(boolean bleeding) {
        this.bleeding = bleeding;
    }

    public void setInfected(boolean infected) {
        this.infected = infected;
    }

    public void setThirst(int thirst) {
        this.thirst = thirst;
    }

    public void setDiamondChestsLooted(int chests) {
        diamondChestsLooted = chests;
    }

    public void setEmeraldChestsLooted(int chests) {
        emeraldChestsLooted = chests;
    }

    public void addDiamondChestsLooted(int chestsAdded) {
        diamondChestsLooted = diamondChestsLooted + chestsAdded;
    }

    public void setIronChestsLooted(int chests) {
        ironChestsLooted = chests;
    }

    public void addIronChestsLooted(int chestsAdded) {
        ironChestsLooted = ironChestsLooted + chestsAdded;
    }

    public void addEmeraldChestsLooted(int chestsAdded) {
        emeraldChestsLooted = emeraldChestsLooted + chestsAdded;
    }

    public void setTimeAlive(int timeAlive) {
        alive = timeAlive;
    }

    public void setTotalPlayerKills(int totalKills) {
        totalPlayerKills = totalKills;
    }

    public void setTotalHeals(int totalHeals) {
        this.totalHeals = totalHeals;
    }

    public void addTimeAlive(int added) {
        alive = alive + added;
    }

    public void addTotalheals(int added) {
        totalHeals = totalHeals + added;
    }

    public void addTotalPlayersKilled(int added) {
        totalPlayerKills = totalPlayerKills + added;
    }

    public void setLastHealed() {
        lastHealed = System.currentTimeMillis();
    }

    public void setLastHealedOther() {
        lastHealedOther = System.currentTimeMillis();
    }

    public void setLastBandaged() {
        lastBandaged = System.currentTimeMillis();
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public void setPerkPoints(int perkPoints) {
        this.perkPoints = perkPoints;
    }

    public void addPerkPoints(int added) {
        this.perkPoints = perkPoints + added;
        getPlayer().sendMessage(ChatColor.YELLOW + "You earned " + ChatColor.LIGHT_PURPLE + added + ChatColor.YELLOW + " Perk Points.");
    }

    public boolean removePerkPoints(int cost) {
        if (cost > getPerkPoints()) return false;
        setPerkPoints(getPerkPoints() - cost);
        return true;
    }

    public void setMostHeals(int amount) {
        this.mostHeals = amount;
        getPlayer().sendMessage(ChatColor.GREEN + "You broke your record for most heals!");
        getPlayer().sendMessage(ChatColor.GREEN + "You healed " + ChatColor.DARK_PURPLE + getMostHeals() + ChatColor.GREEN + " players!");
    }

    public void setMostPlayerKills(int amount) {
        this.mostPlayerKills = amount;
        getPlayer().sendMessage(ChatColor.GREEN + "You broke your record for largest player killstreak!");
        getPlayer().sendMessage(ChatColor.GREEN + "You killed " + ChatColor.DARK_PURPLE + getLongestAlive() + ChatColor.GREEN + " players!");
    }

    public void setMostGiantKills(int amount) {
        this.mostGiantKills = amount;
        getPlayer().sendMessage(ChatColor.GREEN + "You broke your record for largest giant killstreak!");
        getPlayer().sendMessage(ChatColor.GREEN + "You killed " + ChatColor.DARK_PURPLE + getLongestAlive() + ChatColor.GREEN + " giants!");
    }

    public void updateStatus()
    {
        PlayerStatus start = PlayerStatus.valueOf(status.name());

        if(getPlayerKills() > 9) status = PlayerStatus.BANDIT;
        else if (getHeals() > 9) status = PlayerStatus.HEALER;

        if(status != start && start != PlayerStatus.REGULAR)
        {
            getPlayer().sendMessage(status.getColor() + "You are now a " + status.getFullname() + "!");
            getPlayer().sendMessage(status.getColor() + "Other players will see the " + status.getTag() + status.getColor() + " tag near your name.");
        }
    }

    public void updateScoreBoard()
    {
        Player p = getPlayer();

        updateStatus();

        if(!WorldCheck.is(p.getWorld()))
        {
            ScoreboardUtil.removeScoreBoard(p);
        } else ScoreboardUtil.addScoreBoard(p, status);
    }

    public void resetPerkBoosts()
    {
        Player p = getPlayer();
        Health.reset(p);
        Athlete.reset(p);
    }

}
