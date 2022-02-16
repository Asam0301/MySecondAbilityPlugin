package Character;

import ServerPlugin.main;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;


public class Thor implements Listener {

    private main plugin;
    public Thor(main plugin){
        this.plugin = plugin;
    }

    public static ItemStack Passive = new ItemStack(Material.ENCHANTED_BOOK);
    public static ItemStack Weapon = new ItemStack(Material.IRON_AXE);
    public static ItemStack Icon = new ItemStack(Material.LEGACY_SKULL_ITEM);

    public static ArrayList<ItemStack> Armors = new ArrayList<ItemStack>();

    private boolean isCritical(Player player)
    {
        return
                player.getFallDistance() > 0.0F &&
                        !player.isOnGround() &&
                        !player.isInsideVehicle() &&
                        !player.hasPotionEffect(PotionEffectType.BLINDNESS) &&
                        player.getLocation().getBlock().getType() != Material.LADDER &&
                        player.getLocation().getBlock().getType() != Material.VINE;
    }





    public static void setItem(){
        //Passive
        ItemMeta PassiveMeta = Passive.getItemMeta();
        PassiveMeta.setDisplayName(ChatColor.AQUA+""+ChatColor.BOLD+"[P]"+ChatColor.WHITE+"천둥의 신");
        List<String> PassiveLore = new ArrayList<>();
        PassiveLore.add(ChatColor.WHITE+"번개를 자유자재로 사용 할 수 있다.");
        PassiveLore.add(ChatColor.WHITE+"신급 피지컬을 보유 하고있다.");
        PassiveLore.add("skill");
        PassiveMeta.setLore(PassiveLore);
        Passive.setItemMeta(PassiveMeta);
        //Weapon
        ItemMeta WeaponMeta = Weapon.getItemMeta();
        WeaponMeta.setDisplayName(ChatColor.GOLD+""+ChatColor.BOLD+"[W]"+ChatColor.WHITE+"묠니르");
        List<String> WeaponLore = new ArrayList<>();
        WeaponLore.add(ChatColor.GREEN+"우주 최고 대장장이"+ChatColor.WHITE+"의 정수를 담은 무기.");
        WeaponLore.add(ChatColor.WHITE+"좌클릭:번개가 내리친다.");
        WeaponLore.add(ChatColor.WHITE+"우클릭:망치를 던진다.");
        WeaponLore.add(ChatColor.WHITE+"크리티컬:번개가 내리침 + 폭발한다.");
        WeaponLore.add("skill");
        WeaponMeta.setLore(WeaponLore);
        Weapon.setItemMeta(WeaponMeta);
        //Armors
        Armors.add(new ItemStack(Material.NETHERITE_HELMET));
        Armors.add(new ItemStack(Material.NETHERITE_CHESTPLATE));
        Armors.add(new ItemStack(Material.NETHERITE_LEGGINGS));
        Armors.add(new ItemStack(Material.NETHERITE_BOOTS));
        //Icon
        SkullMeta IconMeta = (SkullMeta) Icon.getItemMeta();
        IconMeta.setDisplayName(ChatColor.AQUA +"천둥의 신 "+ChatColor.WHITE+"토르");
        List<String> IconLore = new ArrayList<>();
        IconLore.add(ChatColor.WHITE+"무기:묠니르");
        IconLore.add(ChatColor.WHITE+"갑옷:네더라이트 풀셋");
        IconMeta.setLore(IconLore);
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer("Thorin Oakenshield");
        IconMeta.setOwningPlayer(offlinePlayer);
        Icon.setItemMeta(IconMeta);

    }
    public static void LetMeThor(Player player){
        player.getInventory().addItem(Passive);
        player.getInventory().addItem(Weapon);
        player.getInventory().setHelmet(Armors.get(0));
        player.getInventory().setChestplate(Armors.get(1));
        player.getInventory().setLeggings(Armors.get(2));
        player.getInventory().setBoots(Armors.get(3));

        if(CharacterManager.Player_Character.get(player.getUniqueId()) != null){
            CharacterManager.Player_Character.remove(player.getUniqueId());
            CharacterManager.Player_Character.put(player.getUniqueId(),"Thor");
        } else if(CharacterManager.Player_Character.get(player.getUniqueId()) ==null){
            CharacterManager.Player_Character.put(player.getUniqueId(),"Thor");
        }

        player.sendMessage("You're Thor now.");

    }



    //스킬
    @EventHandler
    public void onAttack(EntityDamageByEntityEvent e) {
        if(e.getDamager() instanceof Player){
            Player player = (Player) e.getDamager();
            if(player.getInventory().getItemInMainHand().getItemMeta() == null) return;
            if(player.getInventory().getItemInMainHand() != null&&player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+""+ChatColor.BOLD+"[W]"+ChatColor.WHITE+"묠니르")) {
                if(isCritical(player) == true){
                    e.getEntity().getWorld().strikeLightningEffect(e.getEntity().getLocation());
                    e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER,1,1);
                    e.getEntity().getWorld().createExplosion(e.getEntity().getLocation(),2);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5*20, 1));
                    if(e.getEntity() instanceof Damageable){
                        ((Damageable) e.getEntity()).damage(5);
                    }

                } else {
                    e.getEntity().getWorld().strikeLightningEffect(e.getEntity().getLocation());
                    e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1, 1);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 5 * 20, 1));
                    if (e.getEntity() instanceof Damageable) {
                        ((Damageable) e.getEntity()).damage(5);
                    }
                }
            }
        }
    }




    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if(e.getAction().equals(Action.RIGHT_CLICK_AIR)||e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Player player = e.getPlayer();
            if (player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().getItemMeta() != null && player.getInventory().getItemInMainHand() != null && player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD + "" + ChatColor.BOLD + "[W]" + ChatColor.WHITE + "묠니르")) {
            if(!player.getInventory().getItemInMainHand().getItemMeta().getDisplayName().equalsIgnoreCase(ChatColor.GOLD+""+ChatColor.BOLD+"[W]"+ChatColor.WHITE+"묠니르")) return;

                    ArmorStand as = (ArmorStand) player.getWorld().spawnEntity(player.getLocation().add(0, 0.5, 0), EntityType.ARMOR_STAND);

                    as.setVisible(false);
                    as.setArms(true);
                    as.setGravity(false);

                    as.setSmall(true);
                    as.setMarker(true);
                    as.setItemInHand(new ItemStack(Material.IRON_AXE));
                    as.setRightArmPose(new EulerAngle(Math.toRadians(90), Math.toRadians(0), Math.toRadians(0)));


                    player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);

                    Location dest = player.getLocation().add(player.getLocation().getDirection().multiply(10));
                    Vector vector = dest.subtract(player.getLocation()).toVector();
                    new BukkitRunnable() {
                        int distance = 30;
                        int i = 0;

                        @Override
                        public void run() {

                            EulerAngle rot = as.getRightArmPose();
                            EulerAngle rotnew = rot.add(20, 0, 0);
                            as.setRightArmPose(rotnew);

                            as.teleport(as.getLocation().add(vector.normalize()));

                            if (as.getTargetBlockExact(1) != null && !as.getTargetBlockExact(1).isPassable()) {
                                if (!as.isDead()) {
                                    as.remove();
                                    if (player.getInventory().firstEmpty() != -1) {
                                        player.getInventory().addItem(Weapon);
                                    } else {
                                        player.getWorld().dropItemNaturally(player.getLocation(), Weapon);
                                    }
                                    cancel();
                                }

                            }

                            for (Entity entity : as.getLocation().getChunk().getEntities()) {
                                if (!as.isDead()) {
                                    if (as.getLocation().distanceSquared(entity.getLocation()) <= 1) {
                                        if (entity != player && entity != as) {
                                            if (!(entity instanceof LivingEntity)) return;
                                            LivingEntity livingEntity = (LivingEntity) entity;
                                            livingEntity.damage(10);
                                            as.getWorld().strikeLightningEffect(as.getLocation());
                                            as.getWorld().playSound(as.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1, 1);
                                            player.teleport(as.getLocation());
                                            as.remove();
                                            if (player.getInventory().firstEmpty() != -1) {
                                                player.getInventory().addItem(Weapon);
                                            } else {
                                                player.getWorld().dropItemNaturally(player.getLocation(), Weapon);
                                            }
                                            cancel();
                                        }
                                    }
                                }
                            }

                            if (i > distance) {

                                if (!as.isDead()) {
                                    as.remove();
                                    if (player.getInventory().firstEmpty() != -1) {
                                        player.getInventory().addItem(Weapon);
                                    } else {
                                        player.getWorld().dropItemNaturally(player.getLocation(), Weapon);
                                    }
                                    cancel();
                                }

                            }
                            i++;
                        }
                    }.runTaskTimer(plugin, 0, 1L);

                    e.setCancelled(true);
                }
            }
        }

}
