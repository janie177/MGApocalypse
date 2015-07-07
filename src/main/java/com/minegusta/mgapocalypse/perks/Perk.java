package com.minegusta.mgapocalypse.perks;

import com.minegusta.mgapocalypse.perks.abilities.*;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public enum Perk {
    SLAYER(9, new Slayer()), //Do more damage with swords
    BOWMAN(11, new Bowman()), //Do more damage with bows
    HEALER(13, new Healer()), //Heal more health
    RESISTANCE(15, new Resistance()), //Less change to bleed or get sick
    LOOTER(17, new Looter()), //Find more loot in chests
    AXEMAN(19, new AxeMan()), //Axes do more damage
    CONSUMER(21, new Consumer()), //Food heals more food bar
    HYDRATION(23, new Hydration()), //More total water
    HEALTH(25, new Health()), //Get more hearths health
    STEALTH(27, new Stealth()), //Zombies are less attracted to you
    FARMER(29, new Farmer()), //Crops have a chance to give more harvest
    ATHLETE(31, new Athlete()), //Run a little faster
    RIDER(33, new Rider()), //buy a horse with saddle one-time
    AIRDROP(35, new AirDrop()); //Buy supplies one-time


    private IPerk perk;
    private int slot;

    private Perk(int slot, IPerk perk) {
        this.slot = slot;
        this.perk = perk;
    }

    public int getSlot() {
        return slot;
    }

    public IPerk getIPerk() {
        return perk;
    }

    public IPerk.Type getType() {
        return perk.getType();
    }

    public void apply(Player p) {
        perk.apply(p);
    }

    public String getName() {
        return perk.getName();
    }

    public String[] description(int level) {
        return perk.getDescription(level);
    }

    public Material getMaterial() {
        return perk.getMaterial();
    }

    public int getDataValue() {
        return perk.getDataValue();
    }

    public int getMaxLevel() {
        return perk.getMaxLevel();
    }

    public int getCost() {
        return perk.getCost();
    }
}
