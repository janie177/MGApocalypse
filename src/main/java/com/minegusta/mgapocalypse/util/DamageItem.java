package com.minegusta.mgapocalypse.util;

public class DamageItem {
    /**
     * Set the damage of an item.
     *
     * @return A random new damage value for tools and armour.
     */
    public static short damage(short s) {
        int start = (int) s;
        int end = start * RandomNumber.get(5, 9) / 10;
        return (short) end;
    }
}
