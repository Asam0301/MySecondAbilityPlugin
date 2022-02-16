package Dungeon;

import net.minecraft.network.chat.IChatBaseComponent;
import net.minecraft.world.entity.monster.EntitySlime;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Slime;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import Dungeon.Monster.*;
import org.bukkit.event.entity.EntitySpawnEvent;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

public class DropItem implements Listener {

    public static void loadItems(){
        slime.init();

    }
    public static   ItemStack SlimeSpawner;

    @EventHandler
    public void onDeath(EntityDeathEvent e){
        Entity entity = e.getEntity();

        if(entity instanceof Slime){
            e.getDrops().clear();
            entity.getWorld().dropItem(entity.getLocation(),slime.SlimeBall);
            entity.remove();

        }
    }


}
