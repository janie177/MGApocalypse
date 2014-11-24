package com.minegusta.mgapocalypse.items;

import com.google.common.collect.Lists;
import com.minegusta.mgapocalypse.util.DamageItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public enum LootItem
{
    //Material Amount ID Lore Name SpawnWithDamage
    WOODSWORD(Material.WOOD_SWORD, 1, (short) 0, "Good for poking zombies." , "Wooden Weapon", true),
    WOODAXE(Material.WOOD_AXE, 1, (short) 0, "Fight wood with wood." , "Wooden Woodcutting Axe", true),
    STONESWORD(Material.STONE_SWORD, 1, (short) 0, "It's better than wood I guess.." , "Stone Weapon", true),
    STONEAXE(Material.STONE_AXE, 1, (short) 0, "May I axe you a question?" , "Stone Woodcutting Axe", true),
    LEATHERBOOTS(Material.LEATHER_BOOTS, 1, (short) 0, "Expensive tacky Italian leather shoes." , "Man Boots", true),
    LEATHERCHEST(Material.LEATHER_CHESTPLATE, 1, (short) 0, "This will keep you warm." , "Leather ChestPlate", true),
    APPLE(Material.APPLE, 1, (short) 0, "A tasty red apple unless your texturepack changes it.", "Red Apple", false),
    LEATHERLEGS(Material.LEATHER_LEGGINGS, 1, (short) 0, "Man Pants." , "Leather Pants", true),
    LEATHERHELM(Material.LEATHER_HELMET, 1, (short) 0, "This will hide your tacky hair." , "Leather Cap", true),
    SNOWBALL(Material.SNOW_BALL, 1, (short) 0, "Maybe you should throw this away." , "Ball Of Rubbish", false),
    COAL(Material.COAL, 1, (short) 0, "Good for cooking food." , "Coal", false),
    BONE(Material.BONE, 1, (short) 0, "A dusty old bone." , "Bone", false),
    SHEARS(Material.SHEARS, 1, (short) 0, "Good for cutting off bandage.", "Shears", false),
    SKULL(Material.SKULL_ITEM, 1, (short) 0, "I should wear this." , "Skull", false),
    SMOKEGRENADE(Material.SLIME_BALL, 1, (short) 0, "Throw this to cause a distraction for players." , "Smoke Grenade", false),
    BANDAGE(Material.PAPER, 1, (short) 0, "Right click to heal yourself or others." , "Bandage", false),
    EMPTYBOTTLE(Material.GLASS_BOTTLE, 1, (short) 0, "You can fill this with water." , "Empty Bottle", false),
    ARROW_2(Material.ARROW, 2, (short) 0, "Pointy Shooty! To be used with a bow." , "Pointy Arrow", false),
    ARROW_3(Material.ARROW, 3, (short) 0, "Pointy Shooty! To be used with a bow." , "Pointy Arrow", false),
    ARROW_4(Material.ARROW, 4, (short) 0, "Pointy Shooty! To be used with a bow." , "Pointy Arrow", false),
    ARROW_5(Material.ARROW, 5, (short) 0, "Pointy Shooty! To be used with a bow." , "Pointy Arrow", false),
    BOW_WEAK(Material.BOW, 1, (short) 0, "An archer's first choice." , "Sharpshooter's Bow", true),
    BOW_NORMAL_1(Material.BOW, 1, (short) 0, "An archer's first choice." , "Sharpshooter's Bow", true, new int[][]{{50, 1}}),
    BOW_NORMAL_2(Material.BOW, 1, (short) 0, "An archer's first choice." , "Sharpshooter's Bow", true, new int[][]{{49, 1}}),
    BOW_STRONG(Material.BOW, 1, (short) 0, "An archer's first choice." , "Sharpshooter's Bow", true, new int[][]{{48, 1}, {49, 2}}),
    BOWL(Material.BOWL, 1, (short) 0, "It's even emptier than your food bar." , "Wooden Bowl", false),
    CHAINHELM(Material.CHAINMAIL_HELMET, 1, (short) 0, "Decent head wear. Still tacky." , "Chain Helmet", true),
    CHAINCHEST(Material.CHAINMAIL_CHESTPLATE, 1, (short) 0, "A chainmail vest to keep you safe." , "Chainmail Vest", true),
    CHAINLEGS(Material.CHAINMAIL_LEGGINGS, 1, (short) 0, "These don't come in dwarf sizes." , "Chain Leggings", true),
    CHAINBOOTS(Material.CHAINMAIL_BOOTS, 1, (short) 0, "I wonder how they made shoes out of rings.." , "Chain Boots", true),
    MELON(Material.MELON, 1, (short) 0, "I could have made a racist joke here." , "Slice Of Melon", false),
    POTATO(Material.POTATO_ITEM, 1, (short) 0, "Boil 'em mash 'em stick 'em in a stew." , "Tater", false),
    CARROT(Material.CARROT_ITEM, 1, (short) 0, "Cawwots awe fow wabbits." , "Carrot", false),
    REDMUSHROOM(Material.RED_MUSHROOM, 1, (short) 0, "Maybe you will grow if you eat it!" , "Red Shroom", false),
    BROWNMUSHROOM(Material.BROWN_MUSHROOM, 1, (short) 0, "Looks, smells and tastes like dirt." , "Brown Shroom", false),
    CHOCOLATE(Material.INK_SACK, 1, (short) 3, "Use this to make delicious cookies!" , "Chocolate Powder", false),
    CHICKEN(Material.RAW_CHICKEN, 1, (short) 0, "You can smell the salmonella.." , "Uncooked Chicken Meat", false),
    MILK(Material.MILK_BUCKET, 1, (short) 0, "Healthy fresh milk. Nom!" , "Fresh Milk", false),
    FISH(Material.RAW_FISH, 1, (short) 0, "Something's fishy." , "Fish.", false),
    WATERBOTTLE(Material.POTION, 1, (short) 0, "A bottle of water, good for drinking." , "Bottle Of Water", false),
    GRAIN(Material.WHEAT, 1, (short) 0, "Can be used to cook various foods." , "Grain", false),
    COOKIE(Material.COOKIE, 1, (short) 0, "Because an apocalypse without cookies would suck." , "Chocolate Chip Cookie", false),
    ZOMBIEMEAT(Material.ROTTEN_FLESH, 1, (short) 0, "It's basically human flesh, but with more worms." , "Zombie Meat", false),
    BUTTON(Material.STONE_BUTTON, 1, (short) 0, "I can place this to open doors!" , "Button", false),
    WOODHOE(Material.WOOD_HOE, 1, (short) 0, "Use this to farm crops or melons." , "Wooden Scythe", true),
    WOODSPADE(Material.WOOD_SPADE, 1, (short) 0, "Use this to dig certain blocks." , "Wooden Spade", true),
    STICK(Material.STICK, 1, (short) 0, "Will stick with you till the end." , "Wooden Stick", false),
    FLINT(Material.FLINT, 1, (short) 0, "Quite sharp. Would make an excellent arrow." , "Sharp Flint", false),
    FEATHER(Material.FEATHER, 2, (short) 0, "Used as arrow-tail" , "Feather", false),
    WOODPICKAXE(Material.WOOD_PICKAXE, 1, (short) 0, "Use this to mine certain blocks." , "Wooden Pickaxe", true),
    IRONSWORD_WEAK(Material.IRON_SWORD, 1, (short) 0, "A sharp blade." , "Iron Sword", true),
    IRONSWORD_NORMAL_1(Material.IRON_SWORD, 1, (short) 0, "A sharp blade." , "Iron Sword", true, new int[][]{{16, 1}}),
    IRONSWORD_NORMAL_2(Material.IRON_SWORD, 1, (short) 0, "A sharp blade." , "Iron Sword", true, new int[][]{{17, 2}}),
    IRONSWORD_NORMAL_3(Material.IRON_SWORD, 1, (short) 0, "A sharp blade." , "Iron Sword", true, new int[][]{{19, 1}}),
    IRONSWORD_STRONG_1(Material.IRON_SWORD, 1, (short) 0, "A sharp blade." , "Iron Sword", true, new int[][]{{16, 1}, {19,1}}),
    IRONSWORD_STRONG_2(Material.IRON_SWORD, 1, (short) 0, "A sharp blade." , "Iron Sword", true, new int[][]{{17,2}, {34, 2}}),
    GOLDBOOTS(Material.GOLD_BOOTS, 1, (short) 0, "Fancy boots for fancy people." , "Golden Shoes", true),
    GOLDHELM(Material.GOLD_HELMET, 1, (short) 0, "This will reflect your tacky hair better!" , "Golden Helmet", true),
    GOLDCHEST(Material.GOLD_CHESTPLATE, 1, (short) 0, "Aurum." , "Golden ChestPlate", true),
    GOLDLEGS(Material.GOLD_LEGGINGS, 1, (short) 0, "HammerTime." , "Hammer Pants", true),
    IRONHELM_WEAK(Material.IRON_HELMET, 1, (short) 0, "A sturdy helmet for protection." , "Iron Helmet", true),
    IRONCHEST_WEAK(Material.IRON_CHESTPLATE, 1, (short) 0, "A heavy chestplate for good protection." , "Iron ChestPlate", true),
    IRONLEGS_WEAK(Material.IRON_LEGGINGS, 1, (short) 0, "Iron armour to protect your butt." , "Iron Leggings", true),
    IRONBOOTS_WEAK(Material.IRON_BOOTS, 1, (short) 0, "Strong boots for strong people." , "Iron Boots", true),
    IRONHELM_STRONG(Material.IRON_HELMET, 1, (short) 0, "A sturdy helmet for protection." , "Iron Helmet", true, new int[][]{{0,1}}),
    IRONCHEST_STRONG(Material.IRON_CHESTPLATE, 1, (short) 0, "A heavy chestplate for good protection." , "Iron ChestPlate", true, new int[][]{{0,1}}),
    IRONLEGS_STRONG(Material.IRON_LEGGINGS, 1, (short) 0, "Iron armour to protect your butt." , "Iron Leggings", true, new int[][]{{0,1}}),
    IRONBOOTS_STRONG(Material.IRON_BOOTS, 1, (short) 0, "Strong boots for strong people." , "Iron Boots", true, new int[][]{{0,1}}),
    GRENADE(Material.ENDER_PEARL, 1, (short) 0, "Throw this to make your problems go away." , "Grenade", false),
    IRONBAR(Material.IRON_INGOT, 1, (short) 0, "This can be used to craft or repair." , "Iron Bar", false),
    DIAMONDSWORD(Material.DIAMOND_SWORD, 1, (short) 0, "The strongest sword around." , "Diamond Sword", true),
    DIAMOND(Material.DIAMOND, 1, (short) 0, "A rare crafting material." , "Shiny Diamond", false),
    HEALINGPOTION_1(Material.POTION, 1, (short) 8197, "It's not a placebo I swear!" , "Potion Of Healing", false),
    HEALINGPOTION_2(Material.POTION, 1, (short) 8229, "It's not a placebo I swear!" , "Potion Of Healing", false),
    HEALINGPOTION_3(Material.POTION, 1, (short) 16389, "It's not a placebo I swear!" , "Potion Of Healing", false),
    HEALINGPOTION_4(Material.POTION, 1, (short) 16421, "It's not a placebo I swear!" , "Potion Of Healing", false),
    HEALINGPOTION_5(Material.POTION, 1, (short) 8193, "It's not a placebo I swear!" , "Potion Of Healing", false),
    HEALINGPOTION_6(Material.POTION, 1, (short) 16385, "It's not a placebo I swear!" , "Potion Of Healing", false),
    HEALINGPOTION_7(Material.POTION, 1, (short) 8225, "It's not a placebo I swear!" , "Potion Of Healing", false);


    private Material m;
    private int amount;
    private short damage;
    private String lore;
    private String name;
    private boolean durability;
    private int[][] enchantments = null;



    private LootItem(Material m, int amount, short damage, String lore, String name, boolean durability)
    {
        this.m = m;
        this.amount = amount;
        this.damage = damage;
        this. lore = lore;
        this.name = name;
        this.durability = durability;
    }

    private LootItem(Material m, int amount, short damage, String lore, String name, boolean durability, int[][] enchantments)
    {
        this.m = m;
        this.damage = damage;
        this.amount = amount;
        this. lore = lore;
        this.name = name;
        this.durability = durability;
        this.enchantments = enchantments;
    }



    public ItemStack build()
    {
        return new ItemStack(m, amount, damage)
        {
            {
                ItemMeta meta = getItemMeta();
                meta.setDisplayName(ChatColor.YELLOW + name);
                List<String> newLore = Lists.newArrayList();
                newLore.add(ChatColor.GREEN + lore);
                meta.setLore(newLore);
                setItemMeta(meta);
                if(enchantments != null)
                {
                    for(int[] i : enchantments)
                    {
                        addEnchantment(Enchantment.getById(i[0]), i[1]);
                    }
                }
                if(durability)
                {
                    setDurability(DamageItem.damage(getType().getMaxDurability()));
                }
            }
        };
    }
}
