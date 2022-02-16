package Dungeon.Monster;

import net.minecraft.network.chat.ChatComponentText;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.monster.EntitySlime;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class slime extends EntitySlime {

    public slime(Location loc){
        super(EntityTypes.aD,((CraftWorld) loc.getWorld()).getHandle());

        this.setPosition(loc.getX(),loc.getY(),loc.getZ());
        this.setSize(1,true);
        this.setHealth(3f);

    }





    public static ItemStack SlimeBall;
    public static void init(){
        ItemStack item = new ItemStack(Material.SLIME_BALL);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.GREEN+""+ChatColor.BOLD+"슬라임볼");
        List<String> lore = new ArrayList();
        lore.add(ChatColor.WHITE+"[!]뉴비들이 잡는 몬스터의 전리품.");
        lore.add(ChatColor.WHITE+"[!]전리품 상점에서 교환 할 수 있다.");
        meta.setLore(lore);
        item.setItemMeta(meta);



        SlimeBall = item;
    }
}
