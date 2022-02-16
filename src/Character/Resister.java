package Character;

import ServerPlugin.main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Resister implements Listener {
    private main plugin;

    public Resister(main plugin){
        this.plugin = plugin;
    }

    //저항하는자

    HashMap<UUID,Long> cooldown = new HashMap<>();
    HashMap<UUID,Boolean> isCancelAble = new HashMap<>();

    public static ItemStack Icon = new ItemStack(Material.NETHERITE_CHESTPLATE);
    public static ItemStack Weapon = new ItemStack(Material.NETHERITE_SWORD);
    public static ItemStack Skill = new ItemStack(Material.ENCHANTED_BOOK);
    public static ArrayList<ItemStack> Armors = new ArrayList<ItemStack>();

    public static void setItem(){
        ItemMeta IconMeta = Icon.getItemMeta();
        IconMeta.setDisplayName(ChatColor.GOLD+"리지스터");
        Icon.setItemMeta(IconMeta);
        //Weapon
        ItemMeta WeaponMeta = Weapon.getItemMeta();
        WeaponMeta.setDisplayName(ChatColor.DARK_RED+""+ChatColor.BOLD+"피를 흘리는 검");
        WeaponMeta.addEnchant(Enchantment.DAMAGE_ALL,4,true);
        Weapon.setItemMeta(WeaponMeta);
        //Skill
        ItemMeta SkillMeta = Skill.getItemMeta();
        SkillMeta.setDisplayName(ChatColor.WHITE+"["+ChatColor.GREEN+""+ChatColor.BOLD+"S"+ChatColor.WHITE+"]"+ChatColor.RED+"최후의 저항");
        ArrayList<String> SkillLore = new ArrayList<>();
        SkillLore.add(ChatColor.WHITE+"저항 3과 넉백 캔슬,재생 2 가 부여된다.");
        SkillLore.add(ChatColor.WHITE+"스킬을 쓴 뒤 탈진하여 구속과 실명이 걸린다.");
        SkillLore.add("skills");
        SkillMeta.setLore(SkillLore);
        Skill.setItemMeta(SkillMeta);
        //Armors
        Armors.add(new ItemStack(Material.NETHERITE_HELMET));
        Armors.add(new ItemStack(Material.NETHERITE_CHESTPLATE));
        Armors.add(new ItemStack(Material.NETHERITE_LEGGINGS));
        Armors.add(new ItemStack(Material.NETHERITE_BOOTS));

        ArrayList<ItemMeta> ArmorsMeta = new ArrayList<>();
        ArmorsMeta.add(Armors.get(0).getItemMeta());
        ArmorsMeta.add(Armors.get(1).getItemMeta());
        ArmorsMeta.add(Armors.get(2).getItemMeta());
        ArmorsMeta.add(Armors.get(3).getItemMeta());

        ArmorsMeta.get(0).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4,true);
        ArmorsMeta.get(1).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4,true);
        ArmorsMeta.get(2).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4,true);
        ArmorsMeta.get(3).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL,4,true);

        Armors.get(0).setItemMeta(ArmorsMeta.get(0));
        Armors.get(1).setItemMeta(ArmorsMeta.get(1));
        Armors.get(2).setItemMeta(ArmorsMeta.get(2));
        Armors.get(3).setItemMeta(ArmorsMeta.get(3));

    }
    public static void LetMeResister(Player player){
        player.getInventory().addItem(Weapon);
        player.getInventory().addItem(Skill);
        player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF,10));
        player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE,2));
        player.getInventory().setHelmet(Armors.get(0));
        player.getInventory().setChestplate(Armors.get(1));
        player.getInventory().setLeggings(Armors.get(2));
        player.getInventory().setBoots(Armors.get(3));

        if(CharacterManager.Player_Character.get(player.getUniqueId()) != null){
            CharacterManager.Player_Character.remove(player.getUniqueId());
            CharacterManager.Player_Character.put(player.getUniqueId(),"Resister");
        } else if(CharacterManager.Player_Character.get(player.getUniqueId()) ==null){
            CharacterManager.Player_Character.put(player.getUniqueId(),"Resister");
        }

        player.sendMessage("You're Resister now.");
    }
    @EventHandler
    public void onClick(PlayerInteractEvent e){
        Player player = e.getPlayer();
        if(player.getInventory().getItemInMainHand() == null) return;
        if(player.getInventory().getItemInMainHand().getItemMeta() == null) return;
        if(player.getInventory().getItemInMainHand().getItemMeta().getDisplayName()==null) return;
        if( player.getInventory().getItemInMainHand() != null && e.getAction().equals(Action.RIGHT_CLICK_AIR)||e.getAction().equals(Action.RIGHT_CLICK_BLOCK)
                &&player.getInventory().getItemInMainHand().getItemMeta() != null&& player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.WHITE+"["+ChatColor.GREEN+""+ChatColor.BOLD+"S"+ChatColor.WHITE+"]"+ChatColor.RED+"최후의 저항")) {
        if(!player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.WHITE+"["+ChatColor.GREEN+""+ChatColor.BOLD+"S"+ChatColor.WHITE+"]"+ChatColor.RED+"최후의 저항")) return;
                if (cooldown.containsKey(player.getUniqueId())) {
                    if (cooldown.get(player.getUniqueId()) > System.currentTimeMillis()) {
                        long BarriertimeLeft = (cooldown.get(player.getUniqueId()) - System.currentTimeMillis()) / 1000;
                        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "쿨타임이 " + BarriertimeLeft + "초 남았습니다"));
                        player.getWorld().playEffect(player.getLocation(), Effect.REDSTONE_TORCH_BURNOUT, 3);

                        return;
                    }
                }
                cooldown.put(player.getUniqueId(), System.currentTimeMillis() + (20 * 1000));

                player.getWorld().playSound(player.getLocation(), Sound.ENTITY_DRAGON_FIREBALL_EXPLODE, 3, 1);
                player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20 * 5, 2, true));
                player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20 * 5, 2, true));

                isCancelAble.put(player.getUniqueId(), true);

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "힘이.... 빠진다...");
                        player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20 * 7, 2, true));
                        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 20 * 7, 2, true));
                        isCancelAble.put(player.getUniqueId(), false);

                    }
                }.runTaskLater(plugin, 20 * 5);


            }

    }

    @EventHandler
    public void onDamaged(EntityDamageByEntityEvent e){
        if(e.getEntity() instanceof Player){
            Player player = (Player) e.getEntity();
            if(isCancelAble.get(player.getUniqueId()) == null) return;
            if(isCancelAble.get(player.getUniqueId()) == true && isCancelAble.get(player.getUniqueId()) !=null){
                e.setCancelled(true);
                player.damage(e.getDamage());
            }
            else if(isCancelAble.get(player.getUniqueId()) == false){
                return;
            }
        }
    }


}
