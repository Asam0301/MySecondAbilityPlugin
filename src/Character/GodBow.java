package Character;

import ServerPlugin.main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

public class GodBow implements Listener {

    private main plugin;
    public GodBow(main plugin){
        this.plugin = plugin;
    }

    public static ItemStack Icon = new ItemStack(Material.BOW);
    public static ItemStack Weapon = new ItemStack(Material.BOW);
    public static ItemStack Feather = new ItemStack(Material.FEATHER);
    public static ItemStack Helmet = new ItemStack(Material.LEATHER_HELMET);
    public static ItemStack Chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
    public static ItemStack Leggings = new ItemStack(Material.LEATHER_LEGGINGS);
    public static ItemStack Boots = new ItemStack(Material.LEATHER_BOOTS);
    public static ArrayList<ItemStack> Potions = new ArrayList<>();


    public HashMap<UUID,Integer> HitCount = new HashMap<>();
    public HashMap<UUID,Long> FeatherCooldown = new HashMap<>();


    public static void setItem(){
        ItemMeta IconMeta = Icon.getItemMeta();
        IconMeta.setDisplayName(ChatColor.AQUA+"신궁");
        Icon.setItemMeta(IconMeta);

        ItemMeta WeaponMeta = Weapon.getItemMeta();
        WeaponMeta.setDisplayName(ChatColor.WHITE+"["+ChatColor.GOLD+""+ChatColor.BOLD+"W"+ChatColor.WHITE+"]신궁");
        ArrayList<String> WeaponLore = new ArrayList<>();
        WeaponLore.add(ChatColor.WHITE+"주몽이 가지고 싶어 환장하던 활.");
        WeaponLore.add(ChatColor.WHITE+"4번 적중시 적에게 위더 디버프를 건다.");
        WeaponMeta.setLore(WeaponLore);
        WeaponMeta.addEnchant(Enchantment.ARROW_DAMAGE,5,true);
        WeaponMeta.addEnchant(Enchantment.ARROW_INFINITE,1,true);
        Weapon.setItemMeta(WeaponMeta);

        ItemMeta FeatherMeta = Feather.getItemMeta();
        FeatherMeta.setDisplayName(ChatColor.WHITE+"["+ChatColor.GREEN+ChatColor.BOLD+"S"+ChatColor.WHITE+"]사뿐한 도약");
        ArrayList<String> FeatherLore = new ArrayList<>();
        FeatherLore.add(ChatColor.WHITE+"우클릭시 날아오르고 천천히 낙하한다.");
        FeatherLore.add(ChatColor.RED+"쿨타임 15초");
        FeatherMeta.setLore(FeatherLore);
        Feather.setItemMeta(FeatherMeta);

        for (int i = 0; i < 8; i++) {
            //7번 반복{
            Potions.add(new ItemStack(Material.SPLASH_POTION));
        }
        ArrayList<PotionMeta> PotionsMeta = new ArrayList<>();
        for (int j = 0; j < 8; j++) {
            PotionsMeta.add((PotionMeta) Potions.get(j).getItemMeta());
            PotionsMeta.get(j).setDisplayName("§f[§b§lP§r§f]포션꾸러미");
        }
        PotionsMeta.get(0).setBasePotionData(new PotionData(PotionType.SPEED));
        PotionsMeta.get(1).setBasePotionData(new PotionData(PotionType.INSTANT_HEAL));
        PotionsMeta.get(2).setBasePotionData(new PotionData(PotionType.SLOWNESS));
        PotionsMeta.get(3).setBasePotionData(new PotionData(PotionType.POISON));
        PotionsMeta.get(4).setBasePotionData(new PotionData(PotionType.JUMP));
        PotionsMeta.get(5).setBasePotionData(new PotionData(PotionType.INVISIBILITY));
        PotionsMeta.get(6).setBasePotionData(new PotionData(PotionType.WEAKNESS));

        for (int k = 0; k <8 ; k++) {
            Potions.get(k).setItemMeta(PotionsMeta.get(k));

        }



    }


    public static void LetMeGodBow(Player p){
        p.getInventory().addItem(Weapon);
        p.getInventory().addItem(new ItemStack(Material.ARROW));
        p.getInventory().addItem(Feather);
        p.getInventory().setHelmet(Helmet);
        p.getInventory().setChestplate(Chestplate);
        p.getInventory().setLeggings(Leggings);
        p.getInventory().setBoots(Boots);

        Random random = new Random();
        int i = random.nextInt(7);
        p.getInventory().addItem(Potions.get(i));

        p.sendMessage("You're GodBow now.");
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(p.getInventory().getItemInMainHand() ==null) return;
        if(p.getInventory().getItemInMainHand().getItemMeta() == null)return;
        if(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName() == null)return;
        if(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.WHITE+"["+ChatColor.GREEN+ChatColor.BOLD+"S"+ChatColor.WHITE+"]사뿐한 도약")) {
            if (FeatherCooldown.containsKey(p.getUniqueId())) {
                if (FeatherCooldown.get(p.getUniqueId()) > System.currentTimeMillis()) {
                    long timeLeft = (FeatherCooldown.get(p.getUniqueId()) - System.currentTimeMillis()) / 1000;
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.RED + "쿨타임이 " + timeLeft + "초 남았습니다"));
                    return;
                }
            }
            FeatherCooldown.put(p.getUniqueId(), System.currentTimeMillis() + (15* 1000));
            Vector vector = new Vector(p.getLocation().getDirection().getX(), p.getLocation().getDirection().getY() + 2, p.getLocation().getDirection().getZ());
            p.setVelocity(vector);
            p.sendMessage("뽀잉");
            p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_FALLING,10*20,2));
        }
    }



    @EventHandler
    public void onShoot(ProjectileHitEvent e){
        if(e.getEntity().getShooter() instanceof Player && e.getHitEntity() instanceof Player){
            Player p = (Player) e.getEntity().getShooter();
            Player hited = (Player) e.getHitEntity();
            if(p.getInventory().getItemInMainHand() == null)return;
            if(p.getInventory().getItemInMainHand().getItemMeta().getDisplayName() ==null)return;
            if(!p.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.WHITE+"["+ChatColor.GOLD+""+ChatColor.BOLD+"W"+ChatColor.WHITE+"]신궁")) return;
            //3타 맞추면 추댐 ㄱㄱ
            if(!HitCount.containsKey(p.getUniqueId())){
                HitCount.put(p.getUniqueId(),1);
            }
            else if(HitCount.get(p.getUniqueId()) == 3){
                e.getEntity().remove();
                HitCount.put(p.getUniqueId(),0);
                hited.addPotionEffect(new PotionEffect(PotionEffectType.WITHER,20*5,1,true));
                p.getWorld().playSound(p.getLocation(), Sound.BLOCK_ENDER_CHEST_OPEN,2,2);

            }
            else {
                HitCount.put(p.getUniqueId(),HitCount.get(p.getUniqueId())+1);

            }
        }
    }
    @EventHandler
    public void onThrow(ProjectileLaunchEvent e){
        if(e.getEntity().getShooter() instanceof  Player){
            Player p = (Player)  e.getEntity().getShooter();
            if(e.getEntityType().equals(EntityType.SPLASH_POTION)){
                if(p.getInventory().contains(Material.FEATHER)){
                    new BukkitRunnable(){
                        @Override
                        public void run() {
                            Random random = new Random();
                            p.getInventory().addItem(Potions.get(random.nextInt(7)));
                        }
                    }.runTaskLater(plugin,20*12);
                }
            }
        }
    }
}
