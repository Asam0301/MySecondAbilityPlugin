package Character;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Boomer implements Listener {

    public static ItemStack Icon = new ItemStack(Material.TNT);
    public static ItemStack Weapon = new ItemStack(Material.TNT);
    public static ItemStack SpecialAttack = new ItemStack(Material.TNT);
    public static ItemStack Armor = new ItemStack(Material.IRON_CHESTPLATE);

    HashMap<UUID,Long> cooldown = new HashMap<>();

    List<FallingBlock> throwedTNT =  new ArrayList<>();

    public static void setItem(){
        ItemMeta IconMeta = Icon.getItemMeta();
        IconMeta.setDisplayName(ChatColor.RED+"붐버맨");
        Icon.setItemMeta(IconMeta);

        ItemMeta WeaponMeta = Weapon.getItemMeta();
        ItemMeta SpecialAttackMeta = SpecialAttack.getItemMeta();

        WeaponMeta.setDisplayName("["+ ChatColor.GOLD+""+ChatColor.BOLD+"W"+ChatColor.WHITE+"]"+"투척가능 TNT");
        ArrayList<String> WeaponLore = new ArrayList<>();
        WeaponLore.add(ChatColor.WHITE+"우클릭으로 던질 수 있다.");
        WeaponMeta.setLore(WeaponLore);
        Weapon.setItemMeta(WeaponMeta);

        SpecialAttackMeta.setDisplayName("["+ChatColor.GREEN+""+ChatColor.BOLD+"S"+ChatColor.WHITE+"]"+"자폭");
        ArrayList<String> SPlore = new ArrayList<>();
        SPlore.add(ChatColor.WHITE+"우클릭시 "+ChatColor.DARK_RED+"자.폭.한.다.");
        SpecialAttackMeta.setLore(SPlore);
        SpecialAttackMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS,4,true);
        SpecialAttack.setItemMeta(SpecialAttackMeta);

        ItemMeta ArmorMeta = Armor.getItemMeta();
        ArmorMeta.addEnchant(Enchantment.PROTECTION_EXPLOSIONS,100,true);
        Armor.setItemMeta(ArmorMeta);

    }

    public static void LetMeBoomer(Player p){
        p.getInventory().addItem(Weapon);
        p.getInventory().addItem(SpecialAttack);
        p.getInventory().setChestplate(Armor);
        p.sendMessage("You're Boomer now.");
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(p.getInventory().getItemInMainHand() ==null) return;
        if(p.getInventory().getItemInMainHand().getItemMeta() == null)return;
        if(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase("["+ ChatColor.GOLD+""+ChatColor.BOLD+"W"+ChatColor.WHITE+"]"+"투척가능 TNT")){
            if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)){
                if(cooldown.containsKey(p.getUniqueId())) {
                    if (cooldown.get(p.getUniqueId()) > System.currentTimeMillis()) {
                        long timeLeft = (cooldown.get(p.getUniqueId()) - System.currentTimeMillis()) / 1000;
                        return;
                    }
                }
                cooldown.put(p.getUniqueId(), System.currentTimeMillis() + (3* 100)); //0.3초



                FallingBlock tnt = p.getWorld().spawnFallingBlock(p.getEyeLocation(), Material.TNT, (byte) 0);
                tnt.setDropItem(false);
                throwedTNT.add(tnt);
                tnt.setVelocity(p.getLocation().getDirection().multiply(3));
                e.setCancelled(true);
            }

        }
        else if(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase("["+ChatColor.GREEN+""+ChatColor.BOLD+"S"+ChatColor.WHITE+"]"+"자폭")){
            e.setCancelled(true);
            p.getWorld().createExplosion(p.getLocation(),10,false,true);
            p.getInventory().getItemInMainHand().setAmount(0);
        }
    }

    @EventHandler
    public void onFall(EntityChangeBlockEvent e){
        if(e.getEntity() instanceof FallingBlock){
            if(throwedTNT.contains(e.getEntity())){
                e.getEntity().getWorld().createExplosion(e.getEntity().getLocation(),3,false,true);
                e.setCancelled(true);
                e.getEntity().remove();
                throwedTNT.remove(e.getEntity());
            }
        }
    }
    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent e){
        if(!e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase("["+ ChatColor.GOLD+""+ChatColor.BOLD+"W"+ChatColor.WHITE+"]"+"투척가능 TNT")||
        e.getPlayer().getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase("["+ChatColor.GREEN+""+ChatColor.BOLD+"S"+ChatColor.WHITE+"]"+"자폭")) return;
        e.setCancelled(true);
    }
}
