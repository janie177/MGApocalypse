package com.minegusta.mgapocalypse.traps;

import com.minegusta.mgapocalypse.traps.instances.*;
import com.minegusta.mgapocalypse.traps.instances.WhirlWindTrap;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public enum Trap {

    PROJECTILE(new ProjectileTrap()),
    EXPLODE(new ExplodeTrap()),
    FIRE(new FireTrap()),
    POTION(new PotionTrap()),
    GIANT(new GiantTrap()),
    UPOTION(new UPotion()),
    UFIRE(new UFire()),
    UPROJECTILE(new UProjectileTrap()),
    UEXPLODE(new UExplode()),
    WOLFPET(new WolfPet()),
    WHIRLWIND(new WhirlWindTrap()),
    ENTITYRAIN(new EntityRainTrap()),
    SOUND(new SoundTrap()),
    CLEAR(new ClearTrap()),
    MOBTRAP(new MobTrap());

    private ITrap trap;

    private Trap(ITrap trap)
    {
        this.trap = trap;
    }

    public boolean apply(Player p, Sign sign)
    {
        return trap.apply(p, sign);
    }

    public String getMessage()
    {
        return trap.getMessage();
    }

    public int getCooldownTime()
    {
        return trap.getCooldownTime();
    }

    public boolean isCooledDown(Location l)
    {
        return trap.isCooledDown(l);
    }

    public void startCooldown(Location l)
    {
        trap.startCooldown(l);
    }

}
