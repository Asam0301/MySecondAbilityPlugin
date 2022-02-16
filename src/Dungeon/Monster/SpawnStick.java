package Dungeon.Monster;

import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.monster.EntitySlime;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.UUID;

public class SpawnStick implements Listener {
    public static HashMap<UUID, Location> EachSlime = new HashMap<>();
    @EventHandler
    public void onClick(PlayerInteractEvent e){
        Player player = e.getPlayer();
        if(player.getInventory().getItemInMainHand() !=null&&player.getInventory().getItemInMainHand().getType() == Material.STICK && e.getClickedBlock() != null && e.getAction()
        == Action.RIGHT_CLICK_BLOCK&&player.isOp()){
            if(e.getClickedBlock().getType() == Material.SLIME_BLOCK){
                Spawn(e.getClickedBlock());
            }
        }
    }
    @EventHandler
    public void onDeath(EntityDeathEvent e){
        Entity entity = e.getEntity();
        if(entity.getType() == EntityType.SLIME){
            Death(entity);
        }
    }
    public void Spawn(Block block){
        if(block.getType() == Material.SLIME_BLOCK){
            Location loc = new Location(block.getWorld(),block.getX(),block.getY() + 1,block.getZ());
            slime slime = new slime(loc);
            WorldServer world = ((CraftWorld)block.getWorld()).getHandle();
            world.addEntity(slime);
            EachSlime.put(slime.getUniqueID(),block.getLocation());

        }

    }
    public void Death(Entity entity){
        if(entity.getType() == EntityType.SLIME) {
            if(EachSlime.get(entity.getUniqueId()) != null) {
                Block block = EachSlime.get(entity.getUniqueId()).getBlock();
                Spawn(block);
                EachSlime.remove(entity.getUniqueId());
            }
        }
    }

}
